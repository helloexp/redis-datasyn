package com.hand.redis;

import com.hand.util.redis.dao.BaseDao;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;


/**
 * Created by DongFan on 2016/11/15.
 */
@Repository
public class CaptchaDao extends BaseDao {

    public CaptchaDao() {
        super();
        this.clazz = "Captcha";
        this.hashTag = "verification";
    }

    /**
     * 将verification保存到Redis
     *
     * @param exprie
     */
    public void addVer(String verificationKey, String verificationCode, int exprie) {
        redisTemplate.opsForValue().set(createPattern() + ":" + verificationKey, verificationCode, exprie, TimeUnit.SECONDS);
    }

    /**
     * 获取json格式的信息并单独取出一个属性的值
     */
    public String getkey(String verificationKey) {
        return this.createPattern(verificationKey);
    }

    public String selectByKey(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void remove(String key) {
        redisTemplate.execute((RedisCallback<Object>) connection -> {
            connection.del(key.getBytes());
            return null;
        });
    }
}
