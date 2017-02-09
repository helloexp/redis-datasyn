/*
 * Copyright Hand China Co.,Ltd.
 */

package com.hand.redis;

import org.springframework.stereotype.Repository;
import com.hand.util.redis.Field.FieldDescriptor;
import com.hand.util.redis.dao.BaseDao;

/**
 * @author yijie.liu@hand-china.com
 */
@Repository("productResourceStagedDao")
public class ProductResourceStagedDao extends BaseDao {
	public ProductResourceStagedDao() {
		super();
		this.catelog = "staged";
		this.clazz = "Product:resource";
		this.hashTag = "product";
		this.idDescriptor = new FieldDescriptor("productCode");
	}
}
