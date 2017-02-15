package com.hand.util.redis.lock;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Random;

/**
 * 测试程序在hmall-lock-test下
 * 高并发下，只要锁未超时，准确率较高，貌似效率也不高。。,比Unexpire略高（猜的，按道理是），反正感觉相差不大
 * Created by Hand on 2017/2/11.
 */
public class UnexwatchRedisLock implements BaseRedisLock {
    private static Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    private StringRedisTemplate redisTemplate;

    private boolean locked;

    private long lockedExpireTime;

    private String lockKey;

    private static final String prefix = "hmall:locked:{locks}";

    /**
     * 锁超时时间，防止线程在入锁以后，无限的执行等待
     */
    private int expireMsecs = 60 * 1000;

    /**
     * 锁等待时间，防止线程饥饿
     */
    private int timeoutMsecs = 10 * 1000;


    public UnexwatchRedisLock(String daoName, String idName, StringRedisTemplate redisTemplate) {
        this.lockKey = createPattern(daoName, idName);
        this.redisTemplate = redisTemplate;
    }

    public UnexwatchRedisLock(String daoName, String idName, StringRedisTemplate redisTemplate, int expireMsecs) {
        this(daoName, idName, redisTemplate);
        this.expireMsecs = expireMsecs;
    }

    public UnexwatchRedisLock(String daoName, String idName, StringRedisTemplate redisTemplate, int expireMsecs, int timeoutMsecs) {
        this(daoName, idName, redisTemplate, expireMsecs);
        this.timeoutMsecs = timeoutMsecs;
    }


    //尝试去获取锁，锁获取成功返回true，否则返回false
    @Override
    public boolean lock() {
        int timeout = timeoutMsecs;
        while (timeout > 0) {
            lockedExpireTime = createExpireTime();
            //试图去获取锁，并设置超时时间
            if (tryLock(objToString(lockedExpireTime))) {
                locked = true;
//                System.err.println("+++++" + Thread.currentThread().getName() + " 获取了锁，value=" + lockedExpireTime);
                return true;
            }
            //若未获取到锁，判断当前的锁是否过期
            String preLockExpire = redisTemplate.opsForValue().get(lockKey);
            if (preLockExpire != null && stringTolong(preLockExpire) < System.currentTimeMillis()) {
                //如果当前锁过期，试图去获取锁，当设置完时间，两次获取的时间一致，说明获取到锁
                //若设置完时间，两次时间不一致，说明锁被其他线程抢先获取，由于时间差较短，因此即使时间不一致，也不影响另一个线程
                lockedExpireTime = createExpireTime();
                String posLockExpire = redisTemplate.opsForValue().getAndSet(lockKey, objToString(lockedExpireTime));
                if (posLockExpire != null && posLockExpire.equals(preLockExpire)) {
                    locked = true;
//                    System.err.println("**+++" +Thread.currentThread().getName() + " 获取了锁（前次超时）value=" + lockedExpireTime);
                    return true;
                }
            }

            //让当前线程随机休息50~200毫秒
            int randomTime = nextRandomTime();
            timeout = timeout - randomTime;
            try {
                Thread.sleep(randomTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    //尝试去释放锁，当锁未超时时，释放锁
    @Override
    public void unlock() {
        //当当前的锁未超时，才去释放锁
        if (locked && lockedExpireTime > System.currentTimeMillis()) {
            Object result = redisTemplate.execute(new SessionCallback<Object>() {
                @Override
                public Object execute(RedisOperations redisOperations) throws DataAccessException {
                    redisOperations.watch(lockKey);
                    redisOperations.multi();
                    redisOperations.delete(lockKey);
                    return redisOperations.exec();
                }
            });
        }
    }

    @Override
    public boolean isLocked() {
        return locked;
    }

    private boolean tryLock(String expireTime) {
        return redisTemplate.opsForValue().setIfAbsent(lockKey, expireTime);
    }

    public String objToString(Object obj) {
        if (obj == null) {
            return "0";
        }
        return String.valueOf(obj);
    }

    ;

    public long stringTolong(String obj) {
        if (obj == null) {
            return 0;
        }
        return Long.parseLong(obj);
    }

    public long createExpireTime() {
        return System.currentTimeMillis() + expireMsecs + 1;
    }

    //生成50到200之间的随机数
    public int nextRandomTime() {
        return new Random().nextInt(150) + 50;
    }

    public String createPattern(String... keys) {
        StringBuffer sb = new StringBuffer(prefix);
        for (String key : keys) {
            sb.append(":" + key);
        }
        return sb.toString();
    }
}
