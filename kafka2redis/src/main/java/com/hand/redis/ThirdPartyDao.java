package com.hand.redis;

import org.springframework.stereotype.Repository;

import com.hand.util.redis.Field.FieldType;
import com.hand.util.redis.dao.BaseDao;

@Repository("thirdPartyDao")
public class ThirdPartyDao extends BaseDao {
	public ThirdPartyDao() {
		super();
		// clazz对应key名中的Staff
		this.clazz = "thirdParty";
		// hashTag对应key名中的｛staff｝
		this.hashTag = "thirdParty";
		// 设置uid为hash_key
		this.idDescriptor = createFieldDescriptor("uid", FieldType.TYPE_EQUAL);
		// 设置需要搜索的字段，priority为字段名，通过FieldType设置搜索模式，EQUAL对应相等匹配查询，MATCH对应模糊匹配查询，RANGE对应范围匹配查询
		addFieldDescriptor(createFieldDescriptor("userId", FieldType.TYPE_EQUAL));
		addFieldDescriptor(createFieldDescriptor("openId", FieldType.TYPE_EQUAL));
		addFieldDescriptor(createFieldDescriptor("uid", FieldType.TYPE_EQUAL));
		addFieldDescriptor(createFieldDescriptor("type", FieldType.TYPE_EQUAL));
		addFieldDescriptor(createFieldDescriptor("state", FieldType.TYPE_EQUAL));
	}
}
