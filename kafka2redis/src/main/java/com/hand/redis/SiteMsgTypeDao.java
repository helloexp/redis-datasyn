package com.hand.redis;

import org.springframework.stereotype.Repository;

import com.hand.util.redis.Field.FieldDescriptor;
import com.hand.util.redis.Field.FieldType;
import com.hand.util.redis.dao.BaseDao;

/**
 * 站内信   左侧信息项
 * @author tiantao
 *
 */
@Repository("siteMsgTypeDao")
public class SiteMsgTypeDao extends BaseDao {
	public SiteMsgTypeDao(){
		super();
		this.clazz = "siteMessage:type";
		this.hashTag = "siteMessage";
		this.idDescriptor = new FieldDescriptor("msgCode");
		addFieldDescriptor(createFieldDescriptor("msgCode", FieldType.TYPE_EQUAL));
	}
}
