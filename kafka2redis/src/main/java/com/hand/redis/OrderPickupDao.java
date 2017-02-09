package com.hand.redis;

import com.hand.util.redis.Field.FieldType;
import com.hand.util.redis.dao.BaseDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by Hand on 2016/11/21.
 */
@Repository
public class OrderPickupDao extends BaseDao {
    public OrderPickupDao(){
        this.clazz = "Pickup";
        this.hashTag = "order";
        this.idDescriptor = createFieldDescriptor("pickupId", FieldType.TYPE_EQUAL);
        addFieldDescriptor(createFieldDescriptor("orderId",FieldType.TYPE_EQUAL));
        addFieldDescriptor(createFieldDescriptor("userId",FieldType.TYPE_EQUAL));
    }

    public String selectPickupIdByOrderId(String orderId){
        if(StringUtils.isEmpty(orderId)){
            return null;
        }
        Set<String> ids = redisTemplate.boundZSetOps(createPattern("orderId",orderId)).rangeByScore(-Double.MAX_VALUE, Double.MAX_VALUE);
        if(ids==null||ids.size()!=1){
            return null;
        }
        Iterator<String> iterator = ids.iterator();
        return iterator.next();
    }
}
