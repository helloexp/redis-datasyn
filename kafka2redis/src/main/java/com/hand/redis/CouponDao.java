package com.hand.redis;

import com.hand.util.redis.Field.FieldType;
import com.hand.util.redis.dao.BaseDao;
import org.springframework.stereotype.Repository;


@Repository
public class CouponDao extends BaseDao{
    {
        this.hashTag = "promotion";
        this.clazz = "coupon";
        idDescriptor = createFieldDescriptor("couponCode", FieldType.TYPE_EQUAL);
        addFieldDescriptor(createFieldDescriptor("name", FieldType.TYPE_MATCH));
        addFieldDescriptor(createFieldDescriptor("range", FieldType.TYPE_MATCH));
        addFieldDescriptor(createFieldDescriptor("type", FieldType.TYPE_EQUAL));
        addFieldDescriptor(createFieldDescriptor("startDate", FieldType.TYPE_EQUAL));
        addFieldDescriptor(createFieldDescriptor("endDate", FieldType.TYPE_EQUAL));
    }
}
