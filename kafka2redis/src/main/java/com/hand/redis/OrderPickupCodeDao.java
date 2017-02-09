package com.hand.redis;

import com.hand.util.redis.Field.FieldType;
import com.hand.util.redis.dao.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * Created by Hand on 2016/11/29.
 */
@Repository
public class OrderPickupCodeDao extends BaseDao {
    public OrderPickupCodeDao(){
        this.clazz = "Pickup:code";
        this.hashTag = "order";
        this.idDescriptor = createFieldDescriptor("uid", FieldType.TYPE_EQUAL);
        addFieldDescriptor(createFieldDescriptor("pickupId",FieldType.TYPE_EQUAL));
        addFieldDescriptor(createFieldDescriptor("status",FieldType.TYPE_EQUAL));
        addFieldDescriptor(createFieldDescriptor("orderId",FieldType.TYPE_EQUAL));
        addFieldDescriptor(createFieldDescriptor("storeId",FieldType.TYPE_EQUAL));
        addFieldDescriptor(createFieldDescriptor("active",FieldType.TYPE_EQUAL));
    }
}
