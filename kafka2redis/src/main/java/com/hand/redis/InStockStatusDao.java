package com.hand.redis;

import org.springframework.stereotype.Repository;

import com.hand.util.redis.Field.FieldDescriptor;
import com.hand.util.redis.dao.BaseDao;

/**
 * 库存状态设置Dao
 * @author tiantao
 *
 */
@Repository("inStockStatusDao")
public class InStockStatusDao extends BaseDao {
	public InStockStatusDao(){
		super();
		this.clazz = "Base:inStockStatus";
		this.hashTag = "Base";
		this.idDescriptor = new FieldDescriptor("inStockCode");
	}
}
