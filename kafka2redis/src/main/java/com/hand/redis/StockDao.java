package com.hand.redis;

import com.hand.util.redis.Field.FieldDescriptor;
import com.hand.util.redis.Field.FieldType;
import com.hand.util.redis.dao.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * @author yijie.liu@hand-china.com
 */
@Repository("stockDao")
public class StockDao extends BaseDao {
	public StockDao() {
		super();
		this.clazz = "stock";
		this.hashTag = "stock";
		this.idDescriptor = new FieldDescriptor("stockId");
		addFieldDescriptor(createFieldDescriptor("productCode", FieldType.TYPE_EQUAL));
		addFieldDescriptor(createFieldDescriptor("productId", FieldType.TYPE_EQUAL));
		addFieldDescriptor(createFieldDescriptor("storeId", FieldType.TYPE_EQUAL));
		addFieldDescriptor(createFieldDescriptor("repositoryId", FieldType.TYPE_EQUAL));
		addFieldDescriptor(createFieldDescriptor("isSure", FieldType.TYPE_EQUAL));
		addFieldDescriptor(createFieldDescriptor("isExpress", FieldType.TYPE_EQUAL));
		addFieldDescriptor(createFieldDescriptor("isPickup", FieldType.TYPE_EQUAL));
		addFieldDescriptor(createFieldDescriptor("isVirtualRepository", FieldType.TYPE_EQUAL));
		addFieldDescriptor(createFieldDescriptor("isTotalRepository", FieldType.TYPE_EQUAL));
		addFieldDescriptor(createFieldDescriptor("isStore", FieldType.TYPE_EQUAL));
		addFieldDescriptor(createFieldDescriptor("isRepository", FieldType.TYPE_EQUAL));
	}
}
