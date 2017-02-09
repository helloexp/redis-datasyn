package com.hand.redis;

import org.springframework.stereotype.Repository;

import com.hand.util.redis.Field.FieldDescriptor;
import com.hand.util.redis.dao.BaseDao;

/**
 * @author yijie.liu@hand-china.com
 */
@Repository("attributionStagedDao")
public class AttributionStagedDao extends BaseDao{
	public AttributionStagedDao(){
		super();
		this.catelog = "staged";
		this.clazz = "Attr";
		this.hashTag = "product";
		this.idDescriptor = new FieldDescriptor("attrId");
	}
}
