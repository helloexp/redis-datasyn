/*
 * Copyright Hand China Co.,Ltd.
 */

package com.hand.redis;

import com.hand.util.redis.Field.FieldDescriptor;
import com.hand.util.redis.Field.FieldType;
import com.hand.util.redis.dao.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * @author yijie.liu@hand-china.com
 *         店铺推荐
 */
@Repository("productRecommendStagedDao")
public class ProductRecommendStagedDao extends BaseDao {
    public ProductRecommendStagedDao() {
        super();
        this.catelog = "staged";
        this.clazz = "Product:recommend";
        this.hashTag = "product";
        this.idDescriptor = new FieldDescriptor("uid");
        addFieldDescriptor(createFieldDescriptor("creationTime", FieldType.TYPE_RANGE));
    }
}
