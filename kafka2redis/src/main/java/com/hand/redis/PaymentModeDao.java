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
@Repository("paymentModeDao")
public class PaymentModeDao extends BaseDao {
	public PaymentModeDao() {
		super();
		this.clazz = "Base:paymentMode";
		this.hashTag = "base";
		this.idDescriptor = new FieldDescriptor("paymentModeId");
	}
}
