package com.hand.aspect;

import com.hand.annotation.RedisID;
import com.hand.annotation.RedisTransaction;
import com.hand.util.TypeUtils;
import com.hand.util.redis.lock.BaseRedisLock;
import com.hand.util.redis.lock.UnexpireRedisLock;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Parameter;

/**
 * Created by Hand on 2017/2/13.
 */
@Aspect
@Component
public class RedisLockAspect {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Pointcut("@annotation(com.hand.annotation.RedisTransaction)")
    public void RedisPointcut(){}

    @Around("RedisPointcut()")
    public Object doAround(ProceedingJoinPoint point){
        //获取方法签名,获取@RedisTransaction注解上的daoName
        MethodSignature signature = (MethodSignature) point.getSignature();
        RedisTransaction redisTransaction = signature.getMethod().getAnnotation(RedisTransaction.class);
        String daoName = redisTransaction.clazz();
        //获取参数注解RedisID
        Parameter[] parameters = signature.getMethod().getParameters();
        Object[] args = point.getArgs();
        String id = null;
        for(int count=0;count<parameters.length;count++){
            if(parameters[count].isAnnotationPresent(RedisID.class)){
                id = TypeUtils.objToString(args[count]);
                break;
            }
        }
        Object result = null;
        //当id不为空时，执行获取锁的操作
        if(id!=null){
            BaseRedisLock redisLock = new UnexpireRedisLock(daoName,id,redisTemplate);
            //获取锁
            while (!redisLock.lock()){
                continue;
            }
            //执行更新操作
            try {
                result = point.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            //释放锁
            redisLock.unlock();
        }else{
            try {
                result = point.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
        return result;
    }

}
