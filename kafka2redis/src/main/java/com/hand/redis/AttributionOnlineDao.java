package com.hand.redis;

import org.springframework.stereotype.Repository;

import com.hand.util.redis.Field.FieldDescriptor;
import com.hand.util.redis.dao.BaseDao;

/**
 * @author yijie.liu@hand-china.com
 */
@Repository("attributionOnlineDao")
public class AttributionOnlineDao extends BaseDao{
	public AttributionOnlineDao(){
		super();
		this.catelog = "online";
		this.clazz = "Attr";
		this.hashTag = "product";
		this.idDescriptor = new FieldDescriptor("attrId");
	}
}
