package com.hand.redis;

import com.hand.util.json.JsonMapper;
import com.hand.util.redis.dao.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * Created by WuHuazhen on 2016/11/21.
 */
@Repository
public class SliderCaptchaDao extends BaseDao {

    public SliderCaptchaDao() {
        super();
        this.clazz = "SliderCaptcha";
        this.hashTag = "verification";
    }

    /**
     * 获取json格式的信息并单独取出一个属性的值
     */
    public String getkey(String name, String id) {
        return this.createPattern(id, name);
    }

    /**
     * 将token保存到Redis
     *
     * @param exprie
     */
    public void addToken(String key, String token, int exprie) {
        redisTemplate.opsForValue().set(key, token, exprie, TimeUnit.SECONDS);
    }

    public String getToken(String key) {
        return redisTemplate.opsForValue().get(key);
    }
    
    /**
     * 将secretKey保存到Redis
     *
     * @param exprie
     */
    public void addSecretKey(String key, String secretKey, int exprie) {
        redisTemplate.opsForValue().set(key, secretKey, exprie, TimeUnit.SECONDS);
    }

    public String getSecretKey(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 将valid保存到Redis
     *
     * @param exprie
     */
    public void addValid(String key, String valid, int exprie) {
        redisTemplate.opsForValue().set(key, valid, exprie, TimeUnit.SECONDS);
    }

    public String getValid(String key) {
        return redisTemplate.opsForValue().get(key);
    }
        
    
    public void remove(String key) {
        redisTemplate.execute((RedisCallback<Object>) connection -> {
            connection.del(key.getBytes());
            return null;
        });
    }

    public Map<String, ?> parseJSON(String json) {
        return this.jsonMapper.convertToMap(json);
    }
}
