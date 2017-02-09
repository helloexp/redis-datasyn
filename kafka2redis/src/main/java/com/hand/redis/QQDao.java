package com.hand.redis;

import org.springframework.stereotype.Repository;

import com.hand.util.redis.Field.FieldType;
import com.hand.util.redis.dao.BaseDao;

@Repository("qqDao")
public class QQDao extends BaseDao {
	public QQDao() {
		super();
		this.clazz = "thirdParty:qq";
		this.hashTag = "thirdParty";
		// 设置uid为hash_key
		this.idDescriptor = createFieldDescriptor("qqOpenId", FieldType.TYPE_EQUAL);
		// 设置需要搜索的字段，priority为字段名，通过FieldType设置搜索模式，EQUAL对应相等匹配查询，MATCH对应模糊匹配查询，RANGE对应范围匹配查询
		addFieldDescriptor(createFieldDescriptor("qqOpenId", FieldType.TYPE_EQUAL));
		addFieldDescriptor(createFieldDescriptor("accessToken", FieldType.TYPE_EQUAL));
		addFieldDescriptor(createFieldDescriptor("createTime", FieldType.TYPE_RANGE));
	}
}
