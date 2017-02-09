package com.hand.controller;

import com.hand.kafka.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by wuhanyuan on 2017/1/17.
 */


public class KfkThread implements Runnable {

    private Config config;
    private String message;

    public KfkThread(Config config,String message){
        this.config = config;
        this.message = message;
    }

    @Override
    public void run() {
        System.out.println("进入kafka线程");
        System.out.println(config.getBootstrapservers());
        KafkaProducer kafkaProducer = new KafkaProducer(
                config.getBootstrapservers(),
                config.getRequesttimeoutms(),
                config.getMaxblockms(),
                config.getAcks(),
                config.getRetries(),
                config.getBatchsize(),
                config.getLingerms(),
                config.getBuffermemory(),
                config.getKeyserializer(),
                config.getValueserializer());

        kafkaProducer.kp(message,config.getKafkatopic());
    }
}
