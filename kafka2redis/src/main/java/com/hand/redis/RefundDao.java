package com.hand.redis;

import org.springframework.stereotype.Repository;

import com.hand.util.redis.Field.FieldType;
import com.hand.util.redis.dao.BaseDao;

import java.util.*;

@Repository
public class RefundDao extends BaseDao {
	
	public RefundDao(){
		super();
		//clazz对应key名的Staff
		this.clazz = "Refund"; 
		//hashTag对应key名中的{ staff }
		this.hashTag = "refund";
		//设置refundId为hash_key
		this.idDescriptor = createFieldDescriptor("refundId",FieldType.TYPE_EQUAL);
		//设置需要搜索的字段,refundAmount为字段名，通过FieldType设置搜索模式，EQUAL对应相等匹配查询，MATCH对应模糊匹配查询，RANGE对应范围匹配查询
		addFieldDescriptor(createFieldDescriptor("orderId",FieldType.TYPE_EQUAL));    
		addFieldDescriptor(createFieldDescriptor("productId",FieldType.TYPE_EQUAL));
		addFieldDescriptor(createFieldDescriptor("userId",FieldType.TYPE_EQUAL));
		addFieldDescriptor(createFieldDescriptor("refundType",FieldType.TYPE_EQUAL));
	}

	public Set<String> selectIdsByMultiEqFields(Map<String,Object> fields){
		List<String> zsetKeys = new ArrayList<>();
		Set<Map.Entry<String,Object>> entrySet = fields.entrySet();
		for(Map.Entry<String,Object> entry:entrySet){
			String key = createPattern(entry.getKey(),objToString(entry.getValue()));
			zsetKeys.add(key);
		}
		String tempKey = createPattern(UUID.randomUUID().toString());
		redisTemplate.opsForZSet().intersectAndStore(zsetKeys.get(0),zsetKeys,tempKey);
		Set<String> ids = redisTemplate.boundZSetOps(tempKey).rangeByScore(-Double.MAX_VALUE,Double.MAX_VALUE);
		redisTemplate.delete(tempKey);
		return ids;
	}
}
