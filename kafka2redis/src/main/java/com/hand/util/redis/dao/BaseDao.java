package com.hand.util.redis.dao;

import com.hand.dto.PagedValues;
import com.hand.dto.ScoreRange;
import com.hand.util.MapUtil;
import com.hand.util.json.JsonMapper;
import com.hand.util.redis.Field.FieldDescriptor;
import com.hand.util.redis.Field.FieldType;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.SetUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.*;
import redis.clients.jedis.Tuple;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by DongFan on 2016/11/15.
 */
public abstract class BaseDao {
    private static final int ZERO = 0;
    @Resource(name = "stringRedisTemplate")
    protected StringRedisTemplate redisTemplate;
    protected JsonMapper jsonMapper = new JsonMapper();
    protected String hashTag = "";
    protected String clazz = "";
    protected String catelog = "cache";
    protected String recycleTag = "recycle";
    protected FieldDescriptor idDescriptor;
    Map<String, FieldDescriptor> fieldDescriptorMap = new HashMap<>();
    private static Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
    //    private static Logger logger2 = LogManager.getLogger("test.time");
    //用于Equal字段分页的字段
    private Map<String, String> pagingFieldMap = new HashMap<>();

    //計算Equal字段的score值
    private double countEqualScore(String name, Map<String, ?> map) {
        double score;
        String scoreField = pagingFieldMap.get(name);
        if (scoreField == null || scoreField.length() == 0) {
            score = ZERO;
        } else {
            score = toDouble(String.valueOf(map.get(scoreField) != null ? map.get(scoreField) : ""), ZERO);
        }
        return score;
    }

    public void addPagingField(String fieldName, String pagingFieldName) {
        pagingFieldMap.put(fieldName, pagingFieldName);
    }

    public void addFieldDescriptor(FieldDescriptor fieldDescriptor) {
        fieldDescriptorMap.put(fieldDescriptor.getName(), fieldDescriptor);
    }

    public static FieldDescriptor createFieldDescriptor(String fieldName, String type) {
        return new FieldDescriptor(fieldName, type);
    }

    /**
     * 返回总数
     *
     * @return
     */
    public int count() {
        String key = createPattern();
        //return jedis.hlen(key).intValue();
        int num = redisTemplate.boundHashOps(key).size().intValue();
        logger.debug("select from " + createPattern() + " records numbers is：" + num);
        return num;
    }

    /**
     * 添加一条记录
     *
     * @param map
     */
    public void add(Map<String, ?> map) {
//        long start = System.currentTimeMillis();
        Map<String, Object> mapNew = (Map<String, Object>) map;
        mapNew.put("version", 0);
        String id = getIdValue(map);
        String pattern = createPattern();
        String json = jsonMapper.convertToJson(mapNew);
        //将新增的数据插入到redis Hash中
        redisTemplate.opsForHash().put(pattern, id, json);
        logger.debug("insert into " + pattern + " 1 record");
        //遍历对象需要进行搜索的字段
        fieldDescriptorMap.forEach((name, field) -> {
            String key;
            double score;
            Object fieldValue = getFieldValue(map, field.getName());
            String value = null;
            if (fieldValue != null) {
                value = String.valueOf(fieldValue);
            }
            if (value != null && fieldValue != null) {
                switch (field.getType()) {
                    case FieldType.TYPE_EQUAL:
                        key = createPattern(name, value);
                        score = countEqualScore(name, map);
                        redisTemplate.boundZSetOps(key).add(id, score);
                        break;
                    case FieldType.TYPE_MATCH:
                        key = createPattern(name);
                        redisTemplate.boundZSetOps(key).add(value + ":" + id, 0);
                        break;
                    //当字段为范围搜索时，需要将id插入到字段对应的ZSET中，以id为value,以字段值为score
                    case FieldType.TYPE_RANGE:
                        key = createPattern(name);
                        score = toDouble(value, 0);
                        redisTemplate.boundZSetOps(key).add(id, score);
                        break;
                    default:
                        break;
                }
            }
        });
//        long end = System.currentTimeMillis();
//        logger2.info("zadd操作耗时："+(end-start));
    }

    public void addRecycle(Map<String, ?> recycle) {
        if (MapUtils.isEmpty(recycle)) {
            return;
        }
        String rid = getIdValue(recycle);
        String json = jsonMapper.convertToJson(recycle);
        redisTemplate.boundHashOps(createRecycle()).put(rid, json);
    }

    public Map<String, ?> selectRecycle(String rid) {
        String json = (String) redisTemplate.boundHashOps(createRecycle()).get(rid);
        if(StringUtils.isEmpty(json)){
            return null;
        }
        return jsonMapper.convertToMap(json);
    }

    /**
     * 根据id搜索对应的对象
     *
     * @param id
     * @return
     */
    public Map<String, ?> select(String id) {
//        long start = System.currentTimeMillis();
        String pattern = createPattern();
        String json = (String) redisTemplate.boundHashOps(pattern).get(id);
        if (json == null || json.length() == 0 || StringUtils.isEmpty(json)) {
            logger.debug("select from " + pattern + " by id:" + id + "no record");
            return null;
        }
        logger.debug("select from " + pattern + " by id:" + id + "1 record");
//        long end = System.currentTimeMillis();
//        logger2.info("z_select操作耗时：" + (end - start));
        return jsonMapper.convertToMap(json);
    }

    /**
     * 删除一条记录
     *
     * @param id
     */
    public Long delete(String id) {
        //返回此操作影响的行数，>0执行成功
//        long start = System.currentTimeMillis();
        Long affectRows = 0l;
        Map<String, ?> old = select(id);
        String idHashKey = createPattern();
        if (old != null) {
            //根据id删除对应redis Hash中的对象
            affectRows = redisTemplate.boundHashOps(idHashKey).delete(id);
            logger.debug("delete from " + idHashKey + " by id:" + id + ",delete 1 record");
            //遍历对象所需分类查询的字段，将其对应的id删除
            fieldDescriptorMap.forEach((name, field) -> {
                String key;
                Object fieldValue = getFieldValue(old, field.getName());
                String value = null;
                if (fieldValue != null) {
                    value = String.valueOf(fieldValue);
                }
                if (fieldValue != null && value != null) {
                    switch (field.getType()) {
                        case FieldType.TYPE_EQUAL:
                            key = createPattern(name, value);
                            redisTemplate.boundZSetOps(key).remove(id);
                            break;
                        case FieldType.TYPE_MATCH:
                            key = createPattern(name);
                            redisTemplate.boundZSetOps(key).remove(value + ":" + id);
                            break;
                        case FieldType.TYPE_RANGE:
                            key = createPattern(name);
                            redisTemplate.boundZSetOps(key).remove(id);
                            break;
                        default:
                            break;
                    }
                }
            });
            String recycleKey = createRecycle();
            String json = jsonMapper.convertToJson(old);
            redisTemplate.boundHashOps(recycleKey).put(id, json);
        } else {
            logger.debug("delete from " + idHashKey + " by id:" + id + " with none record,so not do delete");
        }
//        long end = System.currentTimeMillis();
//        logger2.info("z_delete操作耗时："+(end-start));
        //将内容存在回收表中
        return affectRows;
    }

    /**
     * 更新一条记录
     *
     * @param map
     */
    public void update(Map<String, ?> map) {
        //返回此操作影响的行数，>0执行成功
//        long start = System.currentTimeMillis();
        Map<String, Object> mapNew = (Map<String, Object>) map;
        mapNew.put("version", objToInt(map.get("version")) + 1);
        String id = getIdValue(map);
        Map<String, ?> old = select(id);
        String pattern = createPattern();
        String json = jsonMapper.convertToJson(mapNew);
        //根据id更新redis Hash对象
        redisTemplate.boundHashOps(pattern).put(id, json);
        logger.debug("update from " + pattern + " by id:" + id + " ,update 1 record");
        //当EQUAL字段的SCORE更改时
        if (!pagingFieldMap.isEmpty()) {
            Set<Map.Entry<String, String>> entrySet = pagingFieldMap.entrySet();
            Iterator<Map.Entry<String, String>> iterator = entrySet.iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                String zsetField = entry.getKey();
                String scoreField = entry.getValue();
                String newZsetValue = objToString(map.get(zsetField));
                String oldZsetValue = objToString(old.get(zsetField));
                double newScoreValue = objToDouble(map.get(scoreField));
                double oldScoreValue = objToDouble(old.get(scoreField));
                if (StringUtils.isNotEmpty(newZsetValue) && StringUtils.isNotEmpty(oldZsetValue) && newScoreValue >= 0 && oldScoreValue >= 0) {
                    if (StringUtils.equals(newZsetValue, oldZsetValue) && newScoreValue != oldScoreValue) {
                        String key = createPattern(zsetField, newZsetValue);
                        redisTemplate.boundZSetOps(key).add(id, newScoreValue);
                    }
                }
            }
        }
        //当操作确实为更新操作时，删除原来旧字段所在的索引Set对应的id的值，将更新后的字段与id插入新的索引Set中
        if (old != null) {
            try {
                Set<String> keys = new HashSet<>();
                keys.addAll(map.keySet());
                keys.addAll(old.keySet());
                for (String k : keys) {
                    FieldDescriptor fd = fieldDescriptorMap.get(k);
                    if (fd == null) {
                        continue;
                    }
                    Object nv = map.get(k);
                    Object ov = old.get(k);
                    //当新数据和旧数据都为空时，直接跳过
                    if (nv == null && ov == null) {
                        continue;
                    }
                    //当新数据和旧数据都不为空，且相等时跳过
                    if (nv != null && ov != null && StringUtils.equals(nv.toString(), ov.toString())) {
                        continue;
                    }
                    if (FieldType.TYPE_EQUAL.equals(fd.getType())) {
                        if (ov != null) {
                            String okey = createPattern(k, String.valueOf(ov));
                            redisTemplate.boundZSetOps(okey).remove(id);
                        }
                        if (nv != null) {
                            String nkey = createPattern(k, String.valueOf(nv));
                            double score = countEqualScore(k, map);
                            redisTemplate.boundZSetOps(nkey).add(id, score);
                        }
                    } else if (FieldType.TYPE_MATCH.equals(fd.getType())) {
                        String key = createPattern(k);
                        if (ov != null) {
                            redisTemplate.boundZSetOps(key).remove(ov + ":" + id);
                        }
                        if (nv != null) {
                            redisTemplate.boundZSetOps(key).add(nv + ":" + id, 0);
                        }
                    } else if (FieldType.TYPE_RANGE.equals(fd.getType())) {
                        String key = createPattern(k);
                        //若nv不为空更新数据
                        if (nv != null) {
                            double score = toDouble(String.valueOf(nv), 0);
                            redisTemplate.boundZSetOps(key).add(id, score);
                        }
                        //若ov为不为空nv为空删除记录
                        else if (ov != null && nv == null) {
                            redisTemplate.boundZSetOps(key).remove(id);
                        }
                    }
                }
            } catch (Exception e) {
                jsonMapper.asRuntime(e);
            }
        }
//        long end = System.currentTimeMillis();
//        logger2.info("z_update操作耗时："+(end-start));
    }

    /**
     * @param field
     * @param value
     * @return at least empty List (never return null)
     * 根据索引字段，进行相等匹配搜索
     */
    public List<Map<String, ?>> selectByEqField(String field, String... value) {
//        long start = System.currentTimeMillis();
        if (idDescriptor.getName().equals(field)) {
            BoundHashOperations<String, String, String> boundHashOperations = redisTemplate.boundHashOps(createPattern());
            List<String> jsons = boundHashOperations.multiGet(Arrays.asList(value));
            logger.debug("select from " + createPattern() + " by " + value + " with equal search,select records numbser is " + jsons.size());
            return jsonMapper.convertToList(jsons);
        }
        FieldDescriptor fd = fieldDescriptorMap.get(field);
        if (fd == null || !FieldType.TYPE_EQUAL.equals(fd.getType())) {
            logger.debug("select from " + createPattern() + " by " + value + " with equal search，due to" + field + "not a equal field，with no record");
            return Collections.emptyList();
        }
        String[] keys = new String[value.length];
        for (int i = 0; i < value.length; i++) {
            keys[i] = createPattern(field, value[i]);
        }
        String tempZsetKey = createPattern(UUID.randomUUID().toString());
        redisTemplate.opsForZSet().unionAndStore(keys[0], Arrays.asList(keys), tempZsetKey);
        Set<String> sets = redisTemplate.boundZSetOps(tempZsetKey).rangeByScore(-Double.MAX_VALUE, Double.MAX_VALUE);
        redisTemplate.delete(tempZsetKey);
        if (sets == null || sets.isEmpty()) {
            logger.debug("select from " + createPattern() + " by " + value + " with equal search,select records numbser is " + 0);
            return Collections.emptyList();
        }
        BoundHashOperations<String, String, String> boundHashOperations = redisTemplate.boundHashOps(createPattern());
        List<String> jsonList = boundHashOperations.multiGet(sets);
        logger.debug("select from " + createPattern() + " by " + value + " with equal search,select records numbser is " + jsonList.size());
//        long end = System.currentTimeMillis();
//        logger2.info("z_selectEq操作耗时：" + (end - start));
        return jsonMapper.convertToList(jsonList);
    }

    /**
     * 查找用于分页的Equal字段对应值
     *
     * @param field
     * @param value
     * @param offset
     * @param count
     * @param isReverse
     * @return
     */
    public List<Map<String, ?>> selectByPageField(String field, String value, int offset, int count, boolean isReverse) {
        FieldDescriptor fieldDesc = fieldDescriptorMap.get(field);
        if (fieldDesc == null || !FieldType.TYPE_EQUAL.equals(fieldDesc.getType())) {
            logger.debug("select from " + createPattern() + " by " + value + " with equal search,select records numbser is " + 0);
            return Collections.emptyList();
        }
        String key = createPattern(field, value);
        ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();
        //获取所对应的ids
        Set<String> ids = null;
        if (isReverse) {
            ids = zSetOperations.reverseRangeByScore(key, -Double.MAX_EXPONENT, Double.MAX_VALUE, offset, count);
        } else {
            ids = zSetOperations.rangeByScore(key, -Double.MAX_VALUE, Double.MAX_VALUE, offset, count);
        }
        if (ids == null || ids.size() == 0) {
            logger.debug("select from " + createPattern() + " by " + value + " with equal search,select records numbser is " + 0);
            return Collections.emptyList();
        }
        BoundHashOperations<String, String, String> hashOperations = redisTemplate.boundHashOps(createPattern());
        List<String> jsons = hashOperations.multiGet(ids);
        logger.debug("select from " + createPattern() + " by " + value + " with equal search,select records numbser is " + jsons.size());
        return jsonMapper.convertToList(jsons);
    }

    /**
     * 根据paging_feild范围进行分页
     *
     * @param field
     * @param value
     * @param min
     * @param max
     * @param offset
     * @param count
     * @param isReverse
     * @return
     */
    public List<Map<String, ?>> selectByPageField(String field, String value, double min, double max, int offset, int count, boolean isReverse) {
        FieldDescriptor fieldDesc = fieldDescriptorMap.get(field);
        if (fieldDesc == null || !FieldType.TYPE_EQUAL.equals(fieldDesc.getType())) {
            logger.debug("select from " + createPattern() + " by " + value + " with equal search,select records numbser is " + 0);
            return Collections.emptyList();
        }
        String key = createPattern(field, value);
        ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();
        //获取所对应的ids
        Set<String> ids = null;
        if (isReverse) {
            ids = zSetOperations.reverseRangeByScore(key, min, max, offset, count);
        } else {
            ids = zSetOperations.rangeByScore(key, min, max, offset, count);
        }
        if (ids == null || ids.size() == 0) {
            logger.debug("select from " + createPattern() + " by " + value + " with equal search,select records numbser is " + 0);
            return Collections.emptyList();
        }
        BoundHashOperations<String, String, String> hashOperations = redisTemplate.boundHashOps(createPattern());
        List<String> jsons = hashOperations.multiGet(ids);
        logger.debug("select from " + createPattern() + " by " + value + " with equal search,select records numbser is " + jsons.size());
        return jsonMapper.convertToList(jsons);
    }

    //根据多个Eq属性取交集
    public List<Map<String, ?>> selectByMutilEqField(Map<String, Object> fieldsMap) {
//        long start = System.currentTimeMillis();
        String hashKey = createPattern();
        if (fieldsMap == null || fieldsMap.size() <= 0) {
            logger.debug("select from " + hashKey + " by " + fieldsMap + " with intersect equal search,with no result");
            return Collections.emptyList();
        }
        List<String> keys = new ArrayList<>();
        Set<String> mapKeys = fieldsMap.keySet();
        for (String mapKey : mapKeys) {
            String mapValue = String.valueOf(fieldsMap.get(mapKey));
            String key = createPattern(mapKey, mapValue);
            keys.add(key);
        }
        String tempZsetKey = createPattern(UUID.randomUUID().toString());
        redisTemplate.opsForZSet().intersectAndStore(keys.get(0), keys, tempZsetKey);
        Set<String> keysSet = redisTemplate.boundZSetOps(tempZsetKey).rangeByScore(-Double.MAX_VALUE, Double.MAX_VALUE);
        redisTemplate.delete(tempZsetKey);
        if (keysSet == null || keysSet.size() <= 0) {
            logger.debug("select from " + hashKey + " by " + fieldsMap + " with intersect equal search,with no result");
            return Collections.emptyList();
        }
        BoundHashOperations<String, String, String> hashOperations = redisTemplate.boundHashOps(hashKey);
        List<String> jsons = hashOperations.multiGet(keysSet);
        logger.debug("select from " + hashKey + " by " + fieldsMap + " with intersect equal search,select records number is " + jsons.size());
//        long end = System.currentTimeMillis();
//        logger2.info("z操作耗时：" + (end - start));
        return jsonMapper.convertToList(jsons);
    }

    /**
     * @param field
     * @param prefix 匹配模式
     * @return at least empty List (never return null)
     * 根据索引字段，进行模糊匹配搜索
     * @Param offset 搜索结果偏移量
     * @Param count  搜索结果取值长度
     */
    public List<Map<String, ?>> selectByMatchField(String field, String prefix, int offset, int count) {
        FieldDescriptor fd = fieldDescriptorMap.get(field);
        String key = createPattern(field);
        if (fd == null || !FieldType.TYPE_MATCH.equals(fd.getType())) {
            logger.debug("select from " + createPattern() + " by " + prefix + " with equal search，due to" + field + "not a match field，with no record");
            return Collections.emptyList();
        }
        RedisZSetCommands.Range range = new RedisZSetCommands.Range();
        range.gte(prefix);
        RedisZSetCommands.Limit limit = new RedisZSetCommands.Limit();
        limit.offset(offset);
        limit.count(count);
        Set<String> strList = redisTemplate.boundZSetOps(key).rangeByLex(range, limit);
        if (strList.isEmpty()) {
            logger.debug("select from " + key + " by " + prefix + " with match search,select records numbser is " + 0);
            return Collections.emptyList();
        }
        String[] ids = new String[strList.size()];
        int i = 0;
        for (String str : strList) {
            ids[i++] = StringUtils.substringAfterLast(str, ":");
        }
        BoundHashOperations<String, String, String> boundHashOperations = redisTemplate.boundHashOps(createPattern());
        List<String> jsons = boundHashOperations.multiGet(Arrays.asList(ids));
        logger.debug("select from " + key + " by " + prefix + " with match search,select records numbser is " + jsons.size());
        return jsonMapper.convertToList(jsons);
    }

    public List<Map<String, ?>> selectByMultiPageField(Map<String, ?> fields, int offset, int count, boolean isReverse) {
        List<String> keys = new ArrayList<>(fields.size());
        if (MapUtils.isEmpty(fields)) {
            return Collections.emptyList();
        }
        Set<String> keySet = fields.keySet();
        for (String mapKey : keySet) {
            String mapValue = String.valueOf(fields.get(mapKey));
            keys.add(createPattern(mapKey, mapValue));
        }
        String tempZsetKey = createPattern(UUID.randomUUID().toString());
        redisTemplate.opsForZSet().intersectAndStore(keys.get(0), keys, tempZsetKey);
        Set<String> ids = null;
        if (isReverse) {
            ids = redisTemplate.opsForZSet().reverseRangeByScore(tempZsetKey, -Double.MAX_VALUE, Double.MAX_VALUE, offset, count);
        } else {
            ids = redisTemplate.opsForZSet().rangeByScore(tempZsetKey, -Double.MAX_VALUE, Double.MAX_VALUE, offset, count);
        }
        redisTemplate.delete(tempZsetKey);
        if (ids == null || ids.size() == 0) {
            return Collections.emptyList();
        }
        BoundHashOperations<String, String, String> hashOperations = redisTemplate.boundHashOps(createPattern());
        List<String> jsons = hashOperations.multiGet(ids);
        return jsonMapper.convertToList(jsons);
    }

    public List<Map<String, ?>> selectByMultiPageField(Map<String, ?> fields, double min, double max, int offset, int count, boolean isReverse) {
        List<String> keys = new ArrayList<>(fields.size());
        if (MapUtils.isEmpty(fields)) {
            return Collections.emptyList();
        }
        Set<String> keySet = fields.keySet();
        for (String mapKey : keySet) {
            String mapValue = String.valueOf(fields.get(mapKey));
            keys.add(createPattern(mapKey, mapValue));
        }
        String tempZsetKey = createPattern(UUID.randomUUID().toString());
        redisTemplate.opsForZSet().intersectAndStore(keys.get(0), keys, tempZsetKey);
        Set<String> ids = null;
        if (isReverse) {
            ids = redisTemplate.opsForZSet().reverseRangeByScore(tempZsetKey, min, max, offset, count);
        } else {
            ids = redisTemplate.opsForZSet().rangeByScore(tempZsetKey, min, max, offset, count);
        }
        redisTemplate.delete(tempZsetKey);
        if (ids == null || ids.size() == 0) {
            return Collections.emptyList();
        }
        BoundHashOperations<String, String, String> hashOperations = redisTemplate.boundHashOps(createPattern());
        List<String> jsons = hashOperations.multiGet(ids);
        return jsonMapper.convertToList(jsons);
    }

    public Map<String, Object> selectByMultiMap(Map<String, ?> fields, int offset, int count, boolean isReverse) {
        List<String> keys = new ArrayList<>(fields.size());
        if (MapUtils.isEmpty(fields)) {
            return Collections.emptyMap();
        }
        Set<String> keySet = fields.keySet();
        for (String mapKey : keySet) {
            String mapValue = (String) fields.get(mapKey);
            keys.add(createPattern(mapKey, mapValue));
        }
        if (keys.isEmpty()) {
            return Collections.emptyMap();
        }
        String tempZsetKey = createPattern(UUID.randomUUID().toString());
        String firstKey = keys.get(0);
        keys.remove(0);
        redisTemplate.opsForZSet().intersectAndStore(firstKey, keys, tempZsetKey);
        //redisTemplate.opsForZSet().intersectAndStore(keys.get(0), keys, tempZsetKey);
        Set<String> ids = null;
        if (isReverse) {
            ids = redisTemplate.opsForZSet().reverseRangeByScore(tempZsetKey, -Double.MAX_VALUE, Double.MAX_VALUE, offset, count);
        } else {
            ids = redisTemplate.opsForZSet().rangeByScore(tempZsetKey, -Double.MAX_VALUE, Double.MAX_VALUE, offset, count);
        }
        long totalNum = redisTemplate.boundZSetOps(tempZsetKey).size();
        int total = (int) totalNum;
        redisTemplate.delete(tempZsetKey);
        if (ids == null || ids.size() == 0) {
            return Collections.emptyMap();
        }
        BoundHashOperations<String, String, String> hashOperations = redisTemplate.boundHashOps(createPattern());
        List<String> jsons = hashOperations.multiGet(ids);
        List<Map<String, ?>> list = jsonMapper.convertToList(jsons);
        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("total", total);
        return result;
    }

    public Map<String, Object> selectByMultiMap(Map<String, ?> fields, double min, double max, int offset, int count, boolean isReverse) {
        List<String> keys = new ArrayList<>(fields.size());
        if (MapUtils.isEmpty(fields)) {
            return Collections.emptyMap();
        }
        Set<String> keySet = fields.keySet();
        for (String mapKey : keySet) {
            String mapValue = (String) fields.get(mapKey);
            keys.add(createPattern(mapKey, mapValue));
        }
        String tempZsetKey = createPattern(UUID.randomUUID().toString());
        String firstKey = keys.get(0);
        keys.remove(0);
        redisTemplate.opsForZSet().intersectAndStore(firstKey, keys, tempZsetKey);
//        redisTemplate.opsForZSet().intersectAndStore(keys.get(0), keys, tempZsetKey);
        Set<String> ids = null;
        if (isReverse) {
            ids = redisTemplate.opsForZSet().reverseRangeByScore(tempZsetKey, min, max, offset, count);
        } else {
            ids = redisTemplate.opsForZSet().rangeByScore(tempZsetKey, min, max, offset, count);
        }
        long totalNum = redisTemplate.boundZSetOps(tempZsetKey).count(min, max);
        int total = (int) totalNum;
        redisTemplate.delete(tempZsetKey);
        if (ids == null || ids.size() == 0) {
            return Collections.emptyMap();
        }
        BoundHashOperations<String, String, String> hashOperations = redisTemplate.boundHashOps(createPattern());
        List<String> jsons = hashOperations.multiGet(ids);
        List<Map<String, ?>> list = jsonMapper.convertToList(jsons);
        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("total", total);
        return result;
    }

    /**
     * @param field
     * @param prefix 匹配模式
     * @return at least empty List (never return null)
     * 根据索引字段，进行模糊匹配搜索
     * @Param offset 搜索结果偏移量
     * @Param count  搜索结果取值长度
     */
    public List<Map<String, ?>> selectBySingleMatchField(String field, String prefix) {
        FieldDescriptor fd = fieldDescriptorMap.get(field);
        String key = createPattern(field);
        if (fd == null || !FieldType.TYPE_MATCH.equals(fd.getType())) {
            logger.debug("select from " + createPattern() + " by " + prefix + " with equal search，due to" + field + "not a match field，with no record");
            return Collections.emptyList();
        }
        RedisZSetCommands.Range range = new RedisZSetCommands.Range();
        range.gte(prefix);
        range.lte(prefix + ":" + "}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}");
        Set<String> strList = redisTemplate.boundZSetOps(key).rangeByLex(range);
        if (strList.isEmpty()) {
            logger.debug("select from " + key + " by " + prefix + " with match search,select records numbser is " + 0);
            return Collections.emptyList();
        }
        String[] ids = new String[strList.size()];
        int i = 0;
        for (String str : strList) {
            ids[i++] = StringUtils.substringAfterLast(str, ":");
        }
        BoundHashOperations<String, String, String> boundHashOperations = redisTemplate.boundHashOps(createPattern());
        List<String> jsons = boundHashOperations.multiGet(Arrays.asList(ids));
        logger.debug("select from " + key + " by " + prefix + " with match search,select records numbser is " + jsons.size());
        return jsonMapper.convertToList(jsons);
    }

    /**
     * @param field
     * @return at least empty List (never return null)
     * 根据索引字段，进行范围匹配搜索
     * @Param min 范围最小值
     * @Param max  范围最大值
     * @Param offset 搜索结果偏移量
     * @Param count  搜索结果取值长度
     */
    public List<Map<String, ?>> selectByRangeField(String field, double min, double max, int offset, int count) {
        FieldDescriptor fd = fieldDescriptorMap.get(field);
        if (fd == null || !FieldType.TYPE_RANGE.equals(fd.getType())) {
            logger.debug("select from " + createPattern() + " by " + min + " to " + max + " with equal search，due to" + field + "not a range field，with no record");
            return Collections.emptyList();
        }
        String key = createPattern(field);
        Set<String> sets = redisTemplate.opsForZSet().rangeByScore(key, min, max, offset, count);
        if (sets.isEmpty()) {
            logger.debug("select from " + key + " by " + min + " to " + max + " with range search,select records numbser is " + 0);
            return Collections.emptyList();
        }
        BoundHashOperations<String, String, String> boundHashOperations = redisTemplate.boundHashOps(createPattern());
        List<String> jsonList = boundHashOperations.multiGet(sets);
        logger.debug("select from " + key + " by " + min + " to " + max + " with range search,select records numbser is " + jsonList.size());
        return jsonMapper.convertToList(jsonList);
    }

    //倒序获取ByRange
    public List<Map<String, ?>> selectByRevRange(String field, double min, double max, int offset, int count) {
        FieldDescriptor fieldDescriptor = fieldDescriptorMap.get(field);
        if (fieldDescriptor == null || !fieldDescriptor.getType().equals(FieldType.TYPE_RANGE)) {
            logger.debug("select from " + createPattern() + " by " + min + " to " + max + " with equal search，due to" + field + "not a range field，with no record");
            return Collections.emptyList();
        }
        String key = createPattern(field);
        Set<String> ids = redisTemplate.opsForZSet().reverseRangeByScore(key, min, max, offset, count);
        if (ids == null || ids.size() <= 0) {
            logger.debug("select from " + key + " by " + min + " to " + max + " with range search,select records numbser is " + 0);
            return Collections.emptyList();
        }
        BoundHashOperations<String, String, String> hashOperations = redisTemplate.boundHashOps(createPattern());
        List<String> jsons = hashOperations.multiGet(ids);
        logger.debug("select from " + key + " by " + min + " to " + max + " with range search,select records numbser is " + jsons.size());
        return jsonMapper.convertToList(jsons);
    }

    public List<Map<String, ?>> selectAllByRangeField(String field, double min, double max) {
        FieldDescriptor fd = fieldDescriptorMap.get(field);
        if (fd == null || !FieldType.TYPE_RANGE.equals(fd.getType())) {
            logger.debug("select from " + createPattern() + " by " + min + " to " + max + " with equal search，due to" + field + "not a range field，with no record");
            return Collections.emptyList();
        }
        String key = createPattern(field);
        Set<String> sets = redisTemplate.boundZSetOps(key).rangeByScore(min, max);
        if (sets.isEmpty()) {
            logger.debug("select from " + key + " by " + min + " to " + max + " with range search,select records numbser is " + 0);
            return Collections.emptyList();
        }
        BoundHashOperations<String, String, String> boundHashOperations = redisTemplate.boundHashOps(createPattern());
        List<String> jsonList = boundHashOperations.multiGet(sets);
        logger.debug("select from " + key + " by " + min + " to " + max + " with range search,select records numbser is " + jsonList.size());
        return jsonMapper.convertToList(jsonList);
    }

    public List<Map<String, ?>> selectAllByRevRangeField(String field, double min, double max) {
        FieldDescriptor fd = fieldDescriptorMap.get(field);
        if (fd == null || !FieldType.TYPE_RANGE.equals(fd.getType())) {
            logger.debug("select from " + createPattern() + " by " + min + " to " + max + " with equal search，due to" + field + "not a range field，with no record");
            return Collections.emptyList();
        }
        String key = createPattern(field);
        Set<String> sets = redisTemplate.boundZSetOps(key).reverseRangeByScore(min, max);
        if (sets.isEmpty()) {
            logger.debug("select from " + key + " by " + min + " to " + max + " with range search,select records numbser is " + 0);
            return Collections.emptyList();
        }
        BoundHashOperations<String, String, String> boundHashOperations = redisTemplate.boundHashOps(createPattern());
        List<String> jsonList = boundHashOperations.multiGet(sets);
        logger.debug("select from " + key + " by " + min + " to " + max + " with range search,select records numbser is " + jsonList.size());
        return jsonMapper.convertToList(jsonList);
    }

    protected String getIdValue(Map<String, ?> map) {
        return String.valueOf(map.get(idDescriptor.getName()));
    }

    protected Object getFieldValue(Map<String, ?> map, String f) {
        if (map.containsKey(f)) {
            return map.get(f);
        } else {
            return null;
        }
    }

    //获取所有的值
    public List<Map<String, ?>> selectAll() {
        BoundHashOperations<String, String, String> hashOperations = redisTemplate.boundHashOps(createPattern());
        redisTemplate.boundHashOps("").keys();
        List<String> jsons = hashOperations.values();
        return jsonMapper.convertToList(jsons);
    }

    //获取所有的key
    public Set<String> selectKeys() {
        BoundHashOperations<String, String, String> hashOperations = redisTemplate.boundHashOps(createPattern());
        Set<String> keys = hashOperations.keys();
        return keys;
    }

    //查找PagingEqual字段的总数页数
    public long countPagesByPagingField(String fieldName, String fieldValue, int pageSize) {
        FieldDescriptor fieldDescriptor = fieldDescriptorMap.get(fieldName);
        if (!fieldDescriptor.getType().equals(FieldType.TYPE_EQUAL)) {
            return -1L;
        }
        String key = createPattern(fieldName, fieldValue);
        long setSize = redisTemplate.opsForZSet().size(key);
        long pages = setSize / pageSize;
        long remain = setSize % pageSize;
        if (remain > 0) {
            pages++;
        } else if (pages == 0 && remain == 0) {
            pages++;
        }
        return pages;
    }

    //查询EQUAL字段下的所有信息
    public long countEqualField(String fieldName, String fieldValue) {
        FieldDescriptor fieldDescriptor = fieldDescriptorMap.get(fieldName);
        if (!fieldDescriptor.getType().equals(FieldType.TYPE_EQUAL)) {
            return -1L;
        }
        String key = createPattern(fieldName, fieldValue);
        return redisTemplate.boundZSetOps(key).size();
    }

    public long countEqualFields(Map<String, Object> fields) {
        //计算需要取交集的ids
        List<String> keys = new ArrayList<>();
        Set<Map.Entry<String, Object>> entrySet = fields.entrySet();
        Iterator<Map.Entry<String, Object>> iterator = entrySet.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            keys.add(createPattern(entry.getKey(), String.valueOf(entry.getValue())));
        }
        //临时表的表名
        String tempKey = createPattern(UUID.randomUUID().toString());
        long result = redisTemplate.opsForZSet().intersectAndStore(keys.get(0), keys, tempKey);
        redisTemplate.delete(tempKey);
        return result;
    }

    public long countEqualFields(Map<String, Object> fields, double start, double end) {
        //计算需要取交集的ids
        List<String> keys = new ArrayList<>();
        Set<Map.Entry<String, Object>> entrySet = fields.entrySet();
        Iterator<Map.Entry<String, Object>> iterator = entrySet.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            keys.add(createPattern(entry.getKey(), String.valueOf(entry.getValue())));
        }
        //临时表的表名
        String tempKey = createPattern(UUID.randomUUID().toString());
        redisTemplate.opsForZSet().intersectAndStore(keys.get(0), keys, tempKey);
        long result = redisTemplate.boundZSetOps(tempKey).count(start, end);
        redisTemplate.delete(tempKey);
        return result;
    }

    public String selectIdsSetByEqual(Map<String, List<Object>> conditions) {
        if (MapUtils.isEmpty(conditions)) {
            return null;
        }
        List<String> tempKeys = new ArrayList<>();
        Set<Map.Entry<String, List<Object>>> entrySet = conditions.entrySet();
        for (Map.Entry<String, List<Object>> entry : entrySet) {
            String tempKey = createPattern(UUID.randomUUID().toString());
            String entryKey = entry.getKey();
            List<String> keys = new ArrayList<>();
            for (Object value : entry.getValue()) {
                keys.add(createPattern(entryKey, objToString(value)));
            }
            redisTemplate.opsForZSet().unionAndStore(keys.get(0), keys, tempKey);
            tempKeys.add(tempKey);
        }
        String resultKey = createPattern(UUID.randomUUID().toString());
        redisTemplate.opsForZSet().intersectAndStore(tempKeys.get(0), tempKeys, resultKey);
        redisTemplate.delete(tempKeys);
        return resultKey;
    }

    public String selectIdsSetByRange(Map<String, ScoreRange> ranges) {
        if (MapUtils.isEmpty(ranges)) {
            return null;
        }
        List<String> tempKeys = new ArrayList<>();
        Set<Map.Entry<String, ScoreRange>> entrySet = ranges.entrySet();
        for (Map.Entry<String, ScoreRange> entry : entrySet) {
            String tempKey = createPattern(UUID.randomUUID().toString());
            ScoreRange range = entry.getValue();
            Set<ZSetOperations.TypedTuple<String>> ids = redisTemplate.boundZSetOps(createPattern(entry.getKey())).rangeByScoreWithScores(range.getMin(), range.getMax());
            if (ids == null || ids.isEmpty()) {
                return null;
            }
            redisTemplate.boundZSetOps(tempKey).add(ids);
            tempKeys.add(tempKey);
        }
        String resultKey = createPattern(UUID.randomUUID().toString());
        redisTemplate.opsForZSet().intersectAndStore(tempKeys.get(0), tempKeys, resultKey);
        redisTemplate.delete(tempKeys);
        return resultKey;
    }

    public String selectIdsSetByMatch(Map<String, Object> matches) {
        if (MapUtils.isEmpty(matches)) {
            return null;
        }
        List<String> tempKeys = new ArrayList<>();
        Set<Map.Entry<String, Object>> entrySet = matches.entrySet();
        for (Map.Entry<String, Object> entry : entrySet) {
            String tempKey = createPattern(UUID.randomUUID().toString());
            RedisZSetCommands.Range range = new RedisZSetCommands.Range();
            range.gte(objToString(entry.getValue()));
            range.lte(objToString(entry.getValue()) + "}");
            Set<String> infos = redisTemplate.boundZSetOps(createPattern(entry.getKey())).rangeByLex(range);
            Set<ZSetOperations.TypedTuple<String>> tupleSet = convertInfoToIds(infos);
            if (tupleSet == null || tupleSet.isEmpty()) {
                return null;
            }
            redisTemplate.boundZSetOps(tempKey).add(tupleSet);
            tempKeys.add(tempKey);
        }
        String resultKey = createPattern(UUID.randomUUID().toString());
        redisTemplate.opsForZSet().intersectAndStore(tempKeys.get(0), tempKeys, resultKey);
        redisTemplate.delete(tempKeys);
        return resultKey;
    }

    public PagedValues selectValuesByKeys(List<String> keys, int offset, int count, boolean isRerverse) {
        String tempKey = createPattern(UUID.randomUUID().toString());
        redisTemplate.opsForZSet().intersectAndStore(keys.get(0), keys, tempKey);
        redisTemplate.delete(keys);
        long total = redisTemplate.boundZSetOps(tempKey).size();
        Set<String> ids;
        if (isRerverse) {
            ids = redisTemplate.opsForZSet().reverseRangeByScore(tempKey, -Double.MAX_VALUE, Double.MAX_VALUE, offset, count);
        } else {
            ids = redisTemplate.opsForZSet().rangeByScore(tempKey, -Double.MAX_VALUE, Double.MAX_VALUE, offset, count);
        }
        redisTemplate.delete(tempKey);
        BoundHashOperations<String, String, String> hashOperations = redisTemplate.boundHashOps(createPattern());
        List<String> jsons = hashOperations.multiGet(ids);
        List<Map<String, ?>> values = jsonMapper.convertToList(jsons);
        return new PagedValues(total, values);
    }

    public List<Map<String, ?>> selectValuesByKeys(List<String> keys) {
        if (keys == null || keys.isEmpty()) {
            return null;
        }
        String tempKey = createPattern(UUID.randomUUID().toString());
        redisTemplate.opsForZSet().intersectAndStore(keys.get(0), keys, tempKey);
        redisTemplate.delete(keys);
        Set<String> ids = redisTemplate.boundZSetOps(tempKey).rangeByScore(-Double.MAX_VALUE, Double.MAX_VALUE);
        redisTemplate.delete(tempKey);
        BoundHashOperations<String, String, String> hashOperations = redisTemplate.boundHashOps(createPattern());
        List<String> jsons = hashOperations.multiGet(ids);
        return jsonMapper.convertToList(jsons);
    }

    protected Set<ZSetOperations.TypedTuple<String>> convertInfoToIds(Set<String> infos) {
        Set<ZSetOperations.TypedTuple<String>> ids = new HashSet<>();
        for (String info : infos) {
            String[] infoArray = info.split(":");
            ZSetOperations.TypedTuple<String> tuple = new DefaultTypedTuple<>(infoArray[infoArray.length - 1], 0.0);
            ids.add(tuple);
        }
        return ids;
    }

    protected String createPattern(String... args) {
        StringBuilder sb = new StringBuilder();
        sb.append("hmall:");
        sb.append(catelog + ":");
        sb.append('{').append(hashTag).append("}");
        sb.append(clazz);
        for (String s : args) {
            sb.append(':').append(s);
        }
        return sb.toString();
    }

    protected String createRecycle(String... args) {
        StringBuilder sb = new StringBuilder();
        sb.append("hmall:");
        sb.append(recycleTag + ":");
        sb.append('{').append(hashTag).append("}");
        sb.append(clazz);
        for (String s : args) {
            sb.append(':').append(s);
        }
        return sb.toString();
    }

    protected double toDouble(String str, double def) {
        if (StringUtils.isEmpty(str))
            return def;
        return Double.parseDouble(str);
    }

    protected double objToDouble(Object obj) {
        return Double.parseDouble(String.valueOf(obj != null ? obj : -1));
    }

    protected String objToString(Object obj) {
        return String.valueOf(obj != null ? obj : "");
    }

    protected int objToInt(Object obj) {
        return Integer.parseInt(String.valueOf(obj != null ? obj : -1));
    }
}