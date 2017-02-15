package com.hand.util.redis.lock;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Created by Hand on 2017/2/9.
 */
public interface BaseRedisLock {

    public abstract boolean lock();

    public abstract void unlock();

    public boolean isLocked();
}
