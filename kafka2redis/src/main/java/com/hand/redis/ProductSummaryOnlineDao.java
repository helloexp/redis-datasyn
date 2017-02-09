/*
 * Copyright Hand China Co.,Ltd.
 */

package com.hand.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.stereotype.Repository;
import com.hand.util.redis.Field.FieldDescriptor;
import com.hand.util.redis.Field.FieldType;
import com.hand.util.redis.dao.BaseDao;

/**
 * @author yijie.liu@hand-china.com
 */
@Repository("productSummaryOnlineDao")
public class ProductSummaryOnlineDao extends BaseDao{
   public ProductSummaryOnlineDao(){
	    super();
	    this.catelog = "online";
		this.clazz = "Product:summary";
		this.hashTag = "product";
		this.idDescriptor = new FieldDescriptor("productCode");
		addFieldDescriptor(createFieldDescriptor("categoryCode", FieldType.TYPE_EQUAL));
		addFieldDescriptor(createFieldDescriptor("name", FieldType.TYPE_MATCH));
		addFieldDescriptor(createFieldDescriptor("subtitle", FieldType.TYPE_MATCH));
   }
   
   public List<String> selectAllKeys() {
		BoundHashOperations<String, String, String> hashOperations = redisTemplate.boundHashOps(createPattern());
		List<String> values = hashOperations.values();
		/*
		 * Set<String> keys = hashOperations.keys(); List<String> lkeys = new
		 * ArrayList<String>(keys);
		 */
		List<String> lkeys = new ArrayList<>();
		List<Map<String, ?>> valuesList = jsonMapper.convertToList(values);
		for (Map<String, ?> map : valuesList) {
			if (map.get("approval").toString().equals("on")) {
				lkeys.add(map.get("productCode").toString());
			}
		}
		return lkeys;
	}
}
