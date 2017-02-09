package com.hand.redis;

import org.springframework.stereotype.Repository;

import com.hand.util.redis.Field.FieldDescriptor;
import com.hand.util.redis.Field.FieldType;
import com.hand.util.redis.dao.BaseDao;

/**
 * @author yijie.liu@hand-china.com
 */
@Repository("stockStoreDao")
public class StockStoreDao extends BaseDao {

	public StockStoreDao(){
		super();
		this.clazz = "Store";
		this.hashTag = "stock";
		this.idDescriptor = new FieldDescriptor("uid");
		addFieldDescriptor(createFieldDescriptor("productCode", FieldType.TYPE_EQUAL));
		addFieldDescriptor(createFieldDescriptor("productId", FieldType.TYPE_EQUAL));
		addFieldDescriptor(createFieldDescriptor("store", FieldType.TYPE_EQUAL));
	}
}
