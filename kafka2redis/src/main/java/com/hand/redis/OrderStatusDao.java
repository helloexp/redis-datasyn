package com.hand.redis;

import com.hand.util.redis.Field.FieldType;
import com.hand.util.redis.dao.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * Created by Hand on 2016/11/17.
 */
@Repository
public class OrderStatusDao extends BaseDao{
    public OrderStatusDao(){
        this.clazz = "Order:status";
        this.hashTag = "order";
        this.idDescriptor = createFieldDescriptor("orderId", FieldType.TYPE_EQUAL);
        addFieldDescriptor(createFieldDescriptor("status",FieldType.TYPE_EQUAL));
        addFieldDescriptor(createFieldDescriptor("distribution",FieldType.TYPE_EQUAL));
    }
}
