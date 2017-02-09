package com.hand.redis;

import com.hand.util.redis.Field.FieldDescriptor;
import com.hand.util.redis.Field.FieldType;
import com.hand.util.redis.dao.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * 站内信 系统项Dao
 *
 * @author tiantao
 */
@Repository("siteMsgDao")
public class SiteMsgDao extends BaseDao {
	public SiteMsgDao() {
		super();
		this.clazz = "siteMessage";
		this.hashTag = "siteMessage";
		this.idDescriptor = new FieldDescriptor("uid");
		addFieldDescriptor(createFieldDescriptor("userId", FieldType.TYPE_EQUAL));
		addFieldDescriptor(createFieldDescriptor("msgCode", FieldType.TYPE_EQUAL));
	}
}
