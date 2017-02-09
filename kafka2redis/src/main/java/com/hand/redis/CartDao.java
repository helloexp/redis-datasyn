package com.hand.redis;

import com.hand.util.redis.Field.FieldDescriptor;
import com.hand.util.redis.Field.FieldType;
import com.hand.util.redis.dao.BaseDao;
import org.springframework.stereotype.Repository;

@Repository("cartDao")
public class CartDao extends BaseDao {
    public CartDao() {
        super();
        this.clazz = "Cart";
        this.hashTag = "cart";
        this.idDescriptor = new FieldDescriptor("cartId");
        addFieldDescriptor(createFieldDescriptor("userId", FieldType.TYPE_EQUAL));
        addFieldDescriptor(createFieldDescriptor("productId", FieldType.TYPE_EQUAL));
        addFieldDescriptor(createFieldDescriptor("distribution", FieldType.TYPE_EQUAL));
        addFieldDescriptor(createFieldDescriptor("distributionId", FieldType.TYPE_EQUAL));
    }
}
