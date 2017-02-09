package com.hand.redis;

import org.springframework.stereotype.Repository;

import com.hand.util.redis.Field.FieldDescriptor;
import com.hand.util.redis.Field.FieldType;
import com.hand.util.redis.dao.BaseDao;

/**
 * @author yijie.liu@hand-china.com
 */
@Repository("stockCenterDao")
public class StockCenterDao extends BaseDao {

	public StockCenterDao(){
		super();
		this.clazz = "Center";
		this.hashTag = "stock";
		this.idDescriptor = new FieldDescriptor("uid");
		addFieldDescriptor(createFieldDescriptor("productCode", FieldType.TYPE_EQUAL));
		addFieldDescriptor(createFieldDescriptor("productId", FieldType.TYPE_EQUAL));
	}
}
