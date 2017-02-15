package com.hand.util.redis.dao;

import com.hand.annotation.KafkaTransaction;
import com.hand.annotation.RedisMap;
import com.hand.annotation.TableDao;
import com.hand.redis.CommonDao;

import java.util.Map;

/**
 * Created by vivianus on 17-2-14.
 */

public class UpdateService {

    @KafkaTransaction
    public void update2(@RedisMap Map<String,Object> map,@TableDao CommonDao dao){
        dao.update(map);
    }
}
