package com.hand.redis;

import com.hand.redis.DetailStatus;
import com.hand.redis.OrderStatus;
import com.hand.util.redis.Field.FieldType;
import com.hand.util.redis.dao.BaseDao;
import org.apache.commons.collections.SetUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Hand on 2016/11/17.
 */

@Repository
public class OrderProductDao extends BaseDao{
    public OrderProductDao(){
        this.clazz = "Order:details";
        this.hashTag = "order";
        this.idDescriptor = createFieldDescriptor("uid", FieldType.TYPE_EQUAL);
        addFieldDescriptor(createFieldDescriptor("orderId",FieldType.TYPE_EQUAL));
        addFieldDescriptor(createFieldDescriptor("name",FieldType.TYPE_MATCH));
        addFieldDescriptor(createFieldDescriptor("sign",FieldType.TYPE_EQUAL));
        addFieldDescriptor(createFieldDescriptor("status",FieldType.TYPE_EQUAL));
    }

    public Set<String> selectUnclosedIds(String orderId){
        String orderKey = createPattern("orderId",orderId);
        String closedKey = createPattern("state", OrderStatus.TRADE_CLOSED.toString());
        Set<String> pids = redisTemplate.boundZSetOps(orderKey).rangeByScore(-Double.MAX_VALUE, Double.MAX_VALUE);
        String tempKey = createPattern(UUID.randomUUID().toString());
        //将两个集合的交集存在tempKey中
        redisTemplate.opsForZSet().intersectAndStore(orderKey, closedKey, tempKey);
        Set<String> cids = redisTemplate.boundZSetOps(tempKey).rangeByScore(-Double.MAX_VALUE,Double.MAX_VALUE);
        redisTemplate.delete(tempKey);
        //取差集
        if(cids!=null&&cids.size()>0){
            pids.removeAll(cids);
        }
        return pids;
    }

    public Set<String> selectSendedIds(String orderId){
        String orderKey =createPattern("orderId",orderId);
        String sendKey = createPattern("status", DetailStatus.WAIT_BUYER_CONFIRM_GOODS.toString());
        String tempKey = createPattern(UUID.randomUUID().toString());
        redisTemplate.opsForZSet().intersectAndStore(orderKey, sendKey, tempKey);
        Set<String> ids = redisTemplate.boundZSetOps(tempKey).rangeByScore(-Double.MAX_VALUE,Double.MAX_VALUE);
        redisTemplate.delete(tempKey);
        return ids;
    }

    public Set<String> selectTakedIds(String orderId){
        String orderKey =createPattern("orderId",orderId);
        String sendKey = createPattern("status", DetailStatus.WAIT_BUYER_TAKE_GOODS.toString());
        String tempKey = createPattern(UUID.randomUUID().toString());
        redisTemplate.opsForZSet().intersectAndStore(orderKey, sendKey, tempKey);
        Set<String> ids = redisTemplate.boundZSetOps(tempKey).rangeByScore(-Double.MAX_VALUE,Double.MAX_VALUE);
        redisTemplate.delete(tempKey);
        return ids;
    }
}
