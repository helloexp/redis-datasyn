package com.hand.redis;

import com.hand.util.redis.Field.FieldType;
import com.hand.util.redis.dao.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * Created by Hand on 2016/11/21.
 */
@Repository
public class OrderPriceDao extends BaseDao {
    public OrderPriceDao(){
        this.clazz ="Order:price";
        this.hashTag = "order";
        this.idDescriptor = createFieldDescriptor("orderId", FieldType.TYPE_EQUAL);
    }
}
