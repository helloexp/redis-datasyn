package com.hand.util.redis.lock;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 测试程序在hmall-lock-test下
 * 高并发下效率稍高，但是定时依赖redis
 * Created by Hand on 2017/2/10.
 */
public class ExpireRedisLock implements BaseRedisLock{

    private StringRedisTemplate redisTemplate;

    /**
     * 锁超时时间，防止线程在入锁以后，无限的执行等待
     */
    private int expireMsecs = 60*1000;

    /**
     * 锁等待时间，防止线程饥饿
     */
    private int timeoutMsecs = 10 * 1000;

    private String lockKey;

    private static final String prefix = "hmall:locked:{locks}";

    private volatile boolean locked = false;

    private long lockedExpireTime;

    private static final String keyValue ="1";

    public ExpireRedisLock(String daoName,String idName,StringRedisTemplate redisTemplate){
        this.lockKey = createPattern(daoName,idName);
        this.redisTemplate = redisTemplate;
    }

    public ExpireRedisLock(String daoName,String idName,StringRedisTemplate redisTemplate,int expireMsecs){
        this(daoName,idName,redisTemplate);
        this.expireMsecs = expireMsecs;
    }

    public ExpireRedisLock(String daoName,String idName,StringRedisTemplate redisTemplate,int expireMsecs,int timeoutMsecs){
        this(daoName,idName,redisTemplate,expireMsecs);
        this.timeoutMsecs = timeoutMsecs;
    }


    @Override
    public synchronized boolean lock() {
        int timeout = timeoutMsecs;
        while (timeout>=0){
            //尝试去获取锁，若成功获取锁，设置锁有效期，未成功检查是否死锁
            if(tryLock()){
                lockedExpireTime = currentExpireTime();
                locked = true;
//                System.err.println("++++++"+Thread.currentThread().getName()+" 获得－锁－");
                return true;
            }else{
                //防止设置锁后，设置锁失效时间时线程奔溃，造成死锁
                releaseDeadLock();
            }
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
        redisTemplate.execute(new SessionCallback<Object>() {
            @Override
            public Object execute(RedisOperations redisOperations) throws DataAccessException {
                redisOperations.watch(lockKey);
                if (locked && lockedExpireTime > System.currentTimeMillis()) {
                    redisOperations.multi();
                    redisOperations.delete(lockKey);
//                    System.err.println(Thread.currentThread().getName()+" 释放获取的锁");
                    return redisOperations.exec();
                }
//                else{
//                    System.err.println(Thread.currentThread().getName()+" 获得的锁×过期×");
//                }
                return null;
            }
        });
    }

    @Override
    public boolean isLocked() {
        return locked;
    }

    private boolean tryLock(){
        boolean result = redisTemplate.opsForValue().setIfAbsent(lockKey,keyValue);
        if(result){
            redisTemplate.expire(lockKey,expireMsecs+1, TimeUnit.MILLISECONDS);
        }
        return result;
    }

    private void releaseDeadLock(){
        long preExpireTime = redisTemplate.getExpire(lockKey,TimeUnit.MILLISECONDS);
        //如果未设置过期时间，即设置过期时间时，前一个线程奔溃，补上过期时间
        if(preExpireTime==0){
            redisTemplate.expire(lockKey,expireMsecs,TimeUnit.MILLISECONDS);
        }
    }



    private long currentExpireTime(){
        return System.currentTimeMillis()+expireMsecs;
    }

    public String createPattern(String... keys){
        StringBuffer sb = new StringBuffer(prefix);
        for(String key:keys){
            sb.append(":"+key);
        }
        return sb.toString();
    }

    public String objToString(Object obj){
        if(obj==null){
            return "0";
        }
        return String.valueOf(obj);
    }

    public long stringToLong(String obj){
        if(obj==null){
            return 0L;
        }
        return Long.parseLong(obj);
    }

    //生成50到200之间的随机数
    public int nextRandomTime(){
//        return (int) (50+Math.random()*(120-50+1));
        return new Random().nextInt(150)+50;
    }
}
