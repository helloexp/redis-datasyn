/*
 * Copyright Hand China Co.,Ltd.
 */

package com.hand.redis;

import org.springframework.stereotype.Repository;

import com.hand.util.redis.Field.FieldDescriptor;
import com.hand.util.redis.Field.FieldType;
import com.hand.util.redis.dao.BaseDao;

/**
 * 
 * @author lzh
 *
 */
@Repository("pointOfServiceDao")
public class PointOfServiceDao extends BaseDao {
	public PointOfServiceDao() {
		super();
		this.clazz = "Base:pointOfService";
		this.hashTag = "base";
		this.idDescriptor = new FieldDescriptor("code");
		addFieldDescriptor(createFieldDescriptor("state", FieldType.TYPE_EQUAL));
		addFieldDescriptor(createFieldDescriptor("city", FieldType.TYPE_EQUAL));
		addFieldDescriptor(createFieldDescriptor("district", FieldType.TYPE_EQUAL));
	}
}
