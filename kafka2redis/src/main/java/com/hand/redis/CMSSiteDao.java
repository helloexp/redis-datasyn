package com.hand.redis;

import org.springframework.stereotype.Repository;

import com.hand.util.redis.Field.FieldDescriptor;
import com.hand.util.redis.dao.BaseDao;

/**
 * 网站站点信息Dao
 * @author tiantao
 *
 */
@Repository("cmsSiteDao")
public class CMSSiteDao extends BaseDao {
	public CMSSiteDao(){
		super();
		this.clazz = "Base:cMSSite";
		this.hashTag = "Base";
		this.idDescriptor = new FieldDescriptor("cmmsId");
	}
}
