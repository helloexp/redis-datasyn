package com.hand.redis;

import org.springframework.stereotype.Repository;

import com.hand.util.redis.Field.FieldDescriptor;
import com.hand.util.redis.Field.FieldType;
import com.hand.util.redis.dao.BaseDao;

/**
 * @author yijie.liu@hand-china.com
 */
@Repository("categoryRelationOnlineDao")
public class CategoryRelationOnlineDao extends BaseDao{
	public CategoryRelationOnlineDao(){
		super();
		this.catelog = "online";
        this.clazz = "Category:product";
        this.hashTag = "product";
        this.idDescriptor = new FieldDescriptor("uid");
        addFieldDescriptor(createFieldDescriptor("productCode", FieldType.TYPE_EQUAL));
        addFieldDescriptor(createFieldDescriptor("categoryCode", FieldType.TYPE_EQUAL));
	}
}
