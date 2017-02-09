package com.hand.redis;

import com.hand.redis.OrderStatus;
import com.hand.redis.VisibleStatus;
import com.hand.util.redis.Field.FieldDescriptor;
import com.hand.util.redis.Field.FieldType;
import com.hand.util.redis.dao.BaseDao;
import org.apache.commons.collections.SetUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class  OrderDao extends BaseDao{

    private static final long DAY_MILLI_SECONDS = 86400000L;

    public OrderDao(){
        this.clazz = "Order";
        this.hashTag = "order";
        this.idDescriptor = createFieldDescriptor("orderId", FieldType.TYPE_EQUAL);
        addFieldDescriptor(createFieldDescriptor("userId", FieldType.TYPE_EQUAL));
        addPagingField("userId", "creationTime");
        addFieldDescriptor(createFieldDescriptor("distribution",FieldType.TYPE_EQUAL));
        addFieldDescriptor(createFieldDescriptor("creationTime", FieldType.TYPE_RANGE));
        addFieldDescriptor(createFieldDescriptor("status", FieldType.TYPE_EQUAL));
        addPagingField("status", "creationTime");
        addFieldDescriptor(createFieldDescriptor("visible", FieldType.TYPE_EQUAL));
    }


    public Map<String,Object> selectByMatchId(String orderId,Map<String,Object> conditions,int offset,int count){
        List<String> keys = new ArrayList<>();
        Set<Map.Entry<String,Object>> entrySet = conditions.entrySet();
        for(Map.Entry<String,Object> entry: entrySet){
            keys.add(createPattern(entry.getKey(),String.valueOf(entry.getValue())));
        }
        String tempKey = createPattern(UUID.randomUUID().toString());
        redisTemplate.opsForZSet().intersectAndStore(keys.get(0), keys, tempKey);
        //从交集中取ids
        RedisZSetCommands.Range range = new RedisZSetCommands.Range();
        range.gte(orderId);
        range.lte(orderId + "}");
        RedisZSetCommands.Limit limit = new RedisZSetCommands.Limit();
        limit.offset(offset);
        limit.count(count);
        //获取总数和计算分页数据
        int total = redisTemplate.boundZSetOps(tempKey).rangeByLex(range).size();
        Set<String> ids =redisTemplate.boundZSetOps(tempKey).rangeByLex(range, limit);
        redisTemplate.delete(tempKey);
        //根据id查询订单信息
        BoundHashOperations<String,String,String> hashOperations = redisTemplate.boundHashOps(createPattern());
        List<String> jsons = hashOperations.multiGet(ids);
        List<Map<String,?>> orders = jsonMapper.convertToList(jsons);
        Map<String,Object> map = new HashMap<>();
        map.put("orders",orders);
        map.put("total",total);
        return map;
    }

    public Map<String,Object> selectByMatchId(String orderId,Map<String,Object> conditions,long start,long end,int offset,int count){
        List<String> keys = new ArrayList<>();
        Set<Map.Entry<String,Object>> entrySet = conditions.entrySet();
        for(Map.Entry<String,Object> entry:entrySet){
            keys.add(createPattern(entry.getKey(), String.valueOf(entry.getValue())));
        }
        String tempKey = createPattern(UUID.randomUUID().toString());
        redisTemplate.opsForZSet().intersectAndStore(keys.get(0), keys, tempKey);
        RedisZSetCommands.Range range = new RedisZSetCommands.Range();
        range.gte(orderId);
        range.lte(orderId + "}");
        RedisZSetCommands.Limit limit = new RedisZSetCommands.Limit();
        limit.offset(offset);
        limit.count(count);
        Set<String> ids = redisTemplate.boundZSetOps(tempKey).rangeByLex(range, limit);
        Set<String> total = redisTemplate.boundZSetOps(tempKey).rangeByLex(range);
        redisTemplate.delete(tempKey);
        //根据时间进行分页
        String userId = (String)conditions.get("userId");
        String userKey = createPattern("userId", userId);
        Set<String> timeSet =  redisTemplate.boundZSetOps(userKey).rangeByScore(start, end);
        ids.retainAll(timeSet);
        total.retainAll(timeSet);
        int totalNum = total.size();
        BoundHashOperations<String,String,String> hashOperations =redisTemplate.boundHashOps(createPattern());
        List<String> jsons = hashOperations.multiGet(ids);
        List<Map<String,?>> orders = jsonMapper.convertToList(jsons);
        Map<String,Object> result = new HashMap<>();
        result.put("orders",orders);
        result.put("total",totalNum);
        return result;
    }


    //搜索需要用來关闭的订单
    public List<Map<String,?>> selectBackedOrders(){
        long cutOffTime = getPastTime();
        String zsetKey = createPattern("status", OrderStatus.WAIT_BUYER_PAY.toString());
        Set<String> ids = redisTemplate.boundZSetOps(zsetKey).rangeByScore(-Double.MAX_VALUE, cutOffTime);
        if(ids==null||ids.isEmpty()){
            return Collections.emptyList();
        }
        BoundHashOperations<String,String,String> hashOperations = redisTemplate.boundHashOps(createPattern());
        List<String> jsons = hashOperations.multiGet(ids);
        return jsonMapper.convertToList(jsons);
    }

    private long getPastTime(){
        return new Date().getTime()-DAY_MILLI_SECONDS;
    }
}