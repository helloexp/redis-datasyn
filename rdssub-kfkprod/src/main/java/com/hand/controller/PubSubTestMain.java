package com.hand.controller;

import com.hand.App;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
public class PubSubTestMain {

    @Resource(name = "stringRedisTemplate")
    protected StringRedisTemplate redisTemplate;


    @Autowired
    DoKfk doKfk;

    private static final Logger LOG = LoggerFactory.getLogger(App.class);

    @RequestMapping(value = "/ok", method = RequestMethod.GET)
    @HystrixCommand(fallbackMethod = "okFallback", threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "30"),
            @HystrixProperty(name = "maxQueueSize", value = "100"),
            @HystrixProperty(name = "queueSizeRejectionThreshold", value = "20")}, commandProperties = {
            @HystrixProperty(name = "execution.timeout.enabled", value = "false"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "50")

    })
    public void ok() throws Exception {

        while(1>0){
            if (redisTemplate.opsForList().size("test")>0){
                for (int i=0;i<redisTemplate.opsForList().size("test");i++){
                    String message = redisTemplate.opsForList().leftPop("test");
                    System.out.println(redisTemplate.opsForList().size("test")+"message");
                    doKfk.onMessage("test",message);
                }
            }
        }
    }

    public String okFallback(Throwable e) {
        return "fallback";
    }


}
