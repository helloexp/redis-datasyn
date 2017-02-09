package com.hand.redis;

import org.springframework.stereotype.Repository;

import com.hand.util.redis.Field.FieldType;
import com.hand.util.redis.dao.BaseDao;

@Repository("weiboDao")
public class WeiboDao extends BaseDao {
	public WeiboDao() {
		super();
		// clazz对应key名中的Staff
		this.clazz = "thirdParty:weibo";
		// hashTag对应key名中的｛staff｝
		this.hashTag = "thirdParty";
		// 设置uid为hash_key
		this.idDescriptor = createFieldDescriptor("weiboOpenId", FieldType.TYPE_EQUAL);
		// 设置需要搜索的字段，priority为字段名，通过FieldType设置搜索模式，EQUAL对应相等匹配查询，MATCH对应模糊匹配查询，RANGE对应范围匹配查询
		addFieldDescriptor(createFieldDescriptor("weiboOpenId", FieldType.TYPE_EQUAL));
		addFieldDescriptor(createFieldDescriptor("accessToken", FieldType.TYPE_EQUAL));
		addFieldDescriptor(createFieldDescriptor("createTime", FieldType.TYPE_RANGE));
	}
}
