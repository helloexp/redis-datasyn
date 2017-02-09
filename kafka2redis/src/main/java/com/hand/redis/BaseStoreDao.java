package com.hand.redis;

import org.springframework.stereotype.Repository;

import com.hand.util.redis.Field.FieldDescriptor;
import com.hand.util.redis.dao.BaseDao;

/**
 * 网站店铺信息Dao
 * @author tiantao
 * 
 */
@Repository("baseStoreDao")
public class BaseStoreDao extends BaseDao {
	public BaseStoreDao(){
		super();
		this.clazz = "Base:baseStore";
		this.hashTag = "Base";
		this.idDescriptor = new FieldDescriptor("storeId");
	}
}
