package com.hand.aspect;

import com.hand.annotation.RedisID;
import com.hand.annotation.RedisMap;
import com.hand.annotation.TableDao;
import com.hand.util.redis.dao.BaseDao;
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
import java.util.Map;

/**
 * Created by Hand on 2017/2/14.
 */
@Aspect
@Component
public class KafkaLockAspect {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Pointcut("@annotation(com.hand.annotation.KafkaTransaction)")
    public void pointcut(){}

    @Around("pointcut()")
    public Object doAroud(ProceedingJoinPoint point){
        //获取注解@RedisMap的数据Map和获取注解@TableDao的Dao
        MethodSignature signature = (MethodSignature) point.getSignature();
        Parameter[] parameters = signature.getMethod().getParameters();
        Object[] args = point.getArgs();
        Map<String,Object> data = null;
        BaseDao dao = null;
        for(int count=0; count<parameters.length; count++){
            if(parameters[count].isAnnotationPresent(RedisMap.class)){
               data = (Map<String, Object>) args[count];
            }else if(parameters[count].isAnnotationPresent(TableDao.class)){
                dao = (BaseDao) args[count];
            }
        }
        Object result = null;
        if(data!=null&&dao!=null){
            String id  = dao.getIdValue(data);
            String clazz = dao.getClazz();
            BaseRedisLock redisLock = new UnexpireRedisLock(clazz,id,redisTemplate);
            //获取锁
            while (!redisLock.lock()){
                continue;
            }
            //执行方法
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
