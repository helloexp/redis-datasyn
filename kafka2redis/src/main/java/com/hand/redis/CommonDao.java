package com.hand.redis;

import com.hand.config.TestConfig;
import com.hand.consumer.SpringUtils;
import com.hand.util.redis.Field.FieldDescriptor;
import com.hand.util.redis.Field.FieldType;
import com.hand.util.redis.dao.BaseDao;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;
import java.util.Map;

/**
 * Created by wuhanyuan on 2017/2/8.
 */
public class CommonDao extends BaseDao {
    public String index;

    public CommonDao (){

    }

    public CommonDao(String tableName){
        StringRedisTemplate stringRedisTemplate = (StringRedisTemplate) SpringUtils.getBean("stringRedisTemplate");
        TestConfig testConfig = (TestConfig) SpringUtils.getBean("testConfig");
        Map routes = testConfig.getRoutes();
        TestConfig.Route route = (TestConfig.Route)routes.get(tableName);
        String[] values = route.getValue().split("\\|");
        String[] types = route.getType().split("\\|");

        index = values[0];

        this.redisTemplate = stringRedisTemplate;
        this.catelog = route.getCatelog();
        this.clazz = route.getClazz();
        this.hashTag = route.getHashTag();
        this.idDescriptor = createFieldDescriptor(values[0],types[0]);
        for (int i = 1; i < values.length; i++){
            addFieldDescriptor(createFieldDescriptor(values[i], types[i]));
        };

    }

}
