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
@Repository("productRecommendOnlineDao")
public class ProductRecommendOnlineDao extends BaseDao {
    public ProductRecommendOnlineDao() {
        super();
        this.catelog = "online";
        this.clazz = "Product:recommend";
        this.hashTag = "product";
        this.idDescriptor = new FieldDescriptor("uid");
        addFieldDescriptor(createFieldDescriptor("creationTime", FieldType.TYPE_RANGE));
    }
}
