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
 */
@Repository("categoryOnlineDao")
public class CategoryOnlineDao extends BaseDao {
    public CategoryOnlineDao() {
        super();
        this.catelog = "online";
        this.clazz = "Category";
        this.hashTag = "product";
        this.idDescriptor = new FieldDescriptor("uid");
        addFieldDescriptor(createFieldDescriptor("parentCode", FieldType.TYPE_EQUAL));
    }
}
