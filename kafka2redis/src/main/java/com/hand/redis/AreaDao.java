/*
 * Copyright Hand China Co.,Ltd.
 */

package com.hand.redis;

import org.springframework.stereotype.Repository;
import com.hand.util.redis.Field.FieldDescriptor;
import com.hand.util.redis.Field.FieldType;
import com.hand.util.redis.dao.BaseDao;

/**
 * @author shengli.yuan@hand-china.com
 */
@Repository("areaDao")
public class AreaDao extends BaseDao{
   public AreaDao(){
	   super();
		this.clazz = "Base:area";
		this.hashTag = "base";
		this.idDescriptor = new FieldDescriptor("areaId");
		addFieldDescriptor(createFieldDescriptor("type", FieldType.TYPE_EQUAL));
		addFieldDescriptor(createFieldDescriptor("parentId", FieldType.TYPE_EQUAL));
   }
}
