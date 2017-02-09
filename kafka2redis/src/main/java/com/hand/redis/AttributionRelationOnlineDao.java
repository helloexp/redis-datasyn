package com.hand.redis;

import org.springframework.stereotype.Repository;

import com.hand.util.redis.Field.FieldDescriptor;
import com.hand.util.redis.Field.FieldType;
import com.hand.util.redis.dao.BaseDao;

/**
 * @author yijie.liu@hand-china.com
 */
@Repository("attributionRelationOnlineDao")
public class AttributionRelationOnlineDao extends BaseDao{
	public AttributionRelationOnlineDao(){
		super();
		this.catelog = "online";
		this.clazz = "Attr:product";
		this.hashTag = "product";
		this.idDescriptor = new FieldDescriptor("uid");
		addFieldDescriptor(createFieldDescriptor("productCode", FieldType.TYPE_EQUAL));
	}
}
