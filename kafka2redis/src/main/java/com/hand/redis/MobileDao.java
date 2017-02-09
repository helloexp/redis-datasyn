package com.hand.redis;

import org.springframework.stereotype.Repository;

import com.hand.util.redis.Field.FieldType;
import com.hand.util.redis.dao.BaseDao;

/**
 * @author cw
 */

@Repository("mobileDao")
public class MobileDao extends BaseDao {

	public MobileDao() {
		this.hashTag = "customer";
		this.clazz = "Customer:mobile";
		idDescriptor = createFieldDescriptor("mobileNumber", FieldType.TYPE_EQUAL);
	}

}
