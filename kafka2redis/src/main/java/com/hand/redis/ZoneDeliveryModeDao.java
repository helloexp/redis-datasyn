/*
 * Copyright Hand China Co.,Ltd.
 */

package com.hand.redis;

import org.springframework.stereotype.Repository;

import com.hand.util.redis.Field.FieldDescriptor;
import com.hand.util.redis.dao.BaseDao;

/**
 * 
 * @author lzh
 *
 */
@Repository("zoneDeliveryModeDao")
public class ZoneDeliveryModeDao extends BaseDao {
	public ZoneDeliveryModeDao() {
		super();
		this.clazz = "Base:zoneDeliveryMode";
		this.hashTag = "base";
		this.idDescriptor = new FieldDescriptor("code");
	}
}
