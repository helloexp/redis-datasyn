package com.hand.redis;

import org.springframework.stereotype.Repository;

import com.hand.util.redis.Field.FieldDescriptor;
import com.hand.util.redis.Field.FieldType;
import com.hand.util.redis.dao.BaseDao;

/**
 * 站内信息，已读的系统信息记录表
 * @author tiantao
 *
 */
@Repository("siteMsgReadDao")
public class SiteMsgReadDao extends BaseDao{
	public SiteMsgReadDao(){
		super();
		this.clazz = "siteMessage:read";
		this.hashTag = "siteMessage";
		this.idDescriptor = new FieldDescriptor("uid");
		addFieldDescriptor(createFieldDescriptor("userId", FieldType.TYPE_EQUAL));
		addFieldDescriptor(createFieldDescriptor("siteMsgId", FieldType.TYPE_EQUAL));
		addFieldDescriptor(createFieldDescriptor("state", FieldType.TYPE_EQUAL));
		addFieldDescriptor(createFieldDescriptor("msgCode", FieldType.TYPE_EQUAL));
	}
}
