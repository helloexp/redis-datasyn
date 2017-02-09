package com.hand.redis;

import com.hand.util.redis.Field.FieldType;
import com.hand.util.redis.dao.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * Created by Hand on 2016/11/21.
 */
@Repository
public class OrderNodeDao extends BaseDao{
    public OrderNodeDao(){
        this.clazz ="Order:node";
        this.hashTag = "order";
        this.idDescriptor = createFieldDescriptor("uid", FieldType.TYPE_EQUAL);
        addFieldDescriptor(createFieldDescriptor("orderId",FieldType.TYPE_EQUAL));
        addFieldDescriptor(createFieldDescriptor("status",FieldType.TYPE_EQUAL));
    }
}
