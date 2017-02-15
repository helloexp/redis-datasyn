package com.hand.util.redis.lock;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;
import java.util.Random;

/**
 * 测试程序在hmall-lock-test下
 * 高并发下，只要锁未超时，准确率高，效率相对较低（由于使用了2次WATCH）
 * Created by Hand on 2017/2/9.
 */
public class UnexpireRedisLock implements BaseRedisLock {
    private static Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    private StringRedisTemplate redisTemplate;

//    private static final int DEFAULT_ACQUIRY_RESOLUTION_MILLIS = 100;

    /**
     * Lock key path.
     */
    private String lockKey;

    /**
     * 锁超时时间，防止线程在入锁以后，无限的执行等待
     */
    private int expireMsecs = 60 * 1000;

    /**
     * 锁等待时间，防止线程饥饿
     */
    private int timeoutMsecs = 10 * 1000;

    private volatile boolean locked = false;

    private static final String prefix = "hmall:locked:{locks}";

    //    //如果获取了锁,当前锁的超时时间
    private String lockedExpireTime;

    public UnexpireRedisLock(String daoName, String idName, StringRedisTemplate redisTemplate) {
        this.lockKey = createPattern(daoName, idName);
        this.redisTemplate = redisTemplate;
    }

    public UnexpireRedisLock(String daoName, String idName, StringRedisTemplate redisTemplate, int expireMsecs) {
        this(daoName, idName, redisTemplate);
        this.expireMsecs = expireMsecs;
    }

    public UnexpireRedisLock(String daoName, String idName, StringRedisTemplate redisTemplate, int expireMsecs, int timeoutMsecs) {
        this(daoName, idName, redisTemplate, expireMsecs);
        this.timeoutMsecs = timeoutMsecs;
    }

    @Override
    public synchronized boolean lock() {
        int timeout = timeoutMsecs;
        while (timeout > 0) {
            lockedExpireTime = objToString(System.currentTimeMillis() + expireMsecs + 1);
            //试图去获取锁，当返回结果为true表示成功获取到锁
            if (tryLock(lockedExpireTime)) {
                locked = true;
//                System.err.println("+++++"+Thread.currentThread().getName()+" 获取了锁，value="+lockedExpireTime);
                return true;
            }

            //判断当前锁是否过期，如果过期则开启事务试图去获得锁，若事务执行成功，表示成功获取到锁
            boolean result = redisTemplate.execute(new SessionCallback<Boolean>() {
                @Override
                public Boolean execute(RedisOperations redisOperations) throws DataAccessException {
                    redisOperations.watch(lockKey);
                    long preExpireTime = stringToLong(objToString(redisOperations.opsForValue().get(lockKey)));
                    //若前一次的锁超时，则试图去获取当前锁
                    redisOperations.multi();
                    if (preExpireTime != 0 && preExpireTime < System.currentTimeMillis()) {
                        lockedExpireTime = objToString(System.currentTimeMillis() + expireMsecs + 1);
                        redisOperations.opsForValue().set(lockKey, lockedExpireTime);
                        Object multiResult = redisOperations.exec();
                        //事务执行成功返回非null，事务执行失败返回null
                        if (multiResult != null) {
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        redisOperations.discard();
                    }
                    return false;
                }
            });

            //如果前一次锁过期，并且事务执行成功返回true，获取到当前的锁
            if (result) {
                locked = true;
//                System.err.println("**+++"+Thread.currentThread().getName()+" 获取了锁（前次超时）value="+lockedExpireTime);
                return true;
            }

            //将线程随机停止50到200毫秒
            int randomStopTime = nextRandomTime();
            timeout = timeout - randomStopTime;
            try {
                Thread.sleep(randomStopTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public synchronized void unlock() {
        if (locked) {
            try {
                redisTemplate.execute(new SessionCallback<Object>() {
                    @Override
                    public Object execute(RedisOperations redisOperations) throws DataAccessException {
                        redisOperations.watch(lockKey);
                        String reidsExpireTime = objToString(redisOperations.opsForValue().get(lockKey));
                        //当 当前线程未超时（仍占有锁，未被其他线程因锁超时解锁），释放锁
                        redisOperations.multi();
                        if (reidsExpireTime != null && lockedExpireTime.equals(reidsExpireTime)) {
                            redisOperations.delete(lockKey);
                            return redisOperations.exec();
                        } else {
                            redisOperations.discard();
                        }
                        return null;
                    }
                });
                locked = false;
            } catch (Exception e) {
                locked = false;
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean isLocked() {
        return locked;
    }

    private boolean tryLock(String time) {
        return redisTemplate.opsForValue().setIfAbsent(lockKey, time);
    }

    public String createPattern(String... keys) {
        StringBuffer sb = new StringBuffer(prefix);
        for (String key : keys) {
            sb.append(":" + key);
        }
        return sb.toString();
    }

    public String objToString(Object obj) {
        if (obj == null) {
            return "0";
        }
        return String.valueOf(obj);
    }

    public long stringToLong(String obj) {
        if (obj == null) {
            return 0L;
        }
        return Long.parseLong(obj);
    }

    //生成50到200之间的随机数
    public int nextRandomTime() {
//        return (int) (50+Math.random()*(120-50+1));
        return new Random().nextInt(150) + 50;
    }
}
