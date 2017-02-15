package com.hand.util.redis.lock;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Created by DongFan on 2017/1/20.
 */
public class RedisLock {

    private String lockKey;
    private volatile boolean locked = false;
    private RedisTemplate redisTemplate;

    /**
     * 锁失效时间，防止线程在入锁以后，无限的执行等待，毫秒
     */
    private int expire = 60 * 1000;

    /**
     * 锁超时时间，防止线程饥饿，毫秒
     */
    private int timeout = 10 * 1000;

    private static Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    /**
     * 失效时间默认60000ms，超时时间默认10000ms
     *
     * @param redisTemplate redisTemplate
     * @param lockKey       加锁的key
     */
    public RedisLock(RedisTemplate redisTemplate, String lockKey) {
        this.redisTemplate = redisTemplate;
        this.lockKey = lockKey + "_lock";
    }

    /**
     * 失效时间默认60000ms
     *
     * @param timeout 超时时间
     */
    public RedisLock(RedisTemplate redisTemplate, String lockKey, int timeout) {
        this(redisTemplate, lockKey);
        this.timeout = timeout;
    }

    /**
     * @param timeout 超时时间
     * @param expire  失效时间
     */
    public RedisLock(RedisTemplate redisTemplate, String lockKey, int timeout, int expire) {
        this(redisTemplate, lockKey, timeout);
        this.expire = expire;
    }

    public String getLockKey() {
        return lockKey;
    }

    /**
     * 释放锁
     */
    public synchronized void unlock() {
        if (locked) {
            redisTemplate.delete(lockKey);
            locked = false;
        }
    }
}
