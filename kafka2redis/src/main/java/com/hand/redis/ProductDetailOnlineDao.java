/*
 * Copyright Hand China Co.,Ltd.
 */

package com.hand.redis;

import org.springframework.stereotype.Repository;
import com.hand.util.redis.Field.FieldDescriptor;
import com.hand.util.redis.Field.FieldType;
import com.hand.util.redis.dao.BaseDao;

/**
 * @author yijie.liu@hand-china.com
 */
@Repository("productDetailOnlineDao")
public class ProductDetailOnlineDao extends BaseDao {
	public ProductDetailOnlineDao() {
		super();
		this.catelog = "online";
		this.clazz = "Product";
		this.hashTag = "product";
		this.idDescriptor = new FieldDescriptor("productId");
		addFieldDescriptor(createFieldDescriptor("productCode", FieldType.TYPE_EQUAL));
		addFieldDescriptor(createFieldDescriptor("size", FieldType.TYPE_EQUAL));
		addFieldDescriptor(createFieldDescriptor("style", FieldType.TYPE_EQUAL));
		addFieldDescriptor(createFieldDescriptor("price", FieldType.TYPE_RANGE));
	}
}
