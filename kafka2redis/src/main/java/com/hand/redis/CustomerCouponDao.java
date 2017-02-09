package com.hand.redis;

import com.hand.util.redis.Field.FieldType;
import com.hand.util.redis.dao.BaseDao;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerCouponDao extends BaseDao{
    {
        this.hashTag = "promotion";
        this.clazz = "coupon:customerCoupon";
        idDescriptor = createFieldDescriptor("couponId", FieldType.TYPE_EQUAL);
        addFieldDescriptor(createFieldDescriptor("userId", FieldType.TYPE_EQUAL));
        addFieldDescriptor(createFieldDescriptor("couponCode", FieldType.TYPE_EQUAL));
        addFieldDescriptor(createFieldDescriptor("status", FieldType.TYPE_EQUAL));
    }
}
