package com.hand.kafka;

/**
 * Created by wuhanyuan on 2016/11/29.
 */

import java.util.HashMap;
import java.util.Properties;

import org.apache.kafka.clients.producer.*;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {

    SpringUtils springUtils  = new SpringUtils();
    Properties props = new Properties();

    public KafkaProducer(){

    }

    public KafkaProducer(String bootstrapservers,
                         String requesttimeoutms,
                         String maxblockms,
                         String acks,
                         String retries,
                         String batchsize,
                         String lingerms,
                         String buffermemory,
                         String keyserializer,
                         String valueserializer) {
        props.put("bootstrap.servers",bootstrapservers);
        props.put("request.timeout.ms", requesttimeoutms);
        props.put("max.block.ms", maxblockms);
        props.put("acks", acks);
        props.put("retries", retries);
        props.put("batch.size", batchsize);
        props.put("linger.ms", lingerms);
        props.put("buffer.memory", buffermemory);
        props.put("key.serializer", keyserializer);
        props.put("value.serializer", valueserializer);
    }
    public void kp(String message,String topicName){

        Producer<String, String> producer = new org.apache.kafka.clients.producer.KafkaProducer<String, String>(props);
        producer.send(new ProducerRecord<String, String>(topicName, message, message));
        producer.close();
//        producer.send(new ProducerRecord<String, String>(topicName, message, message), new Callback() {
//            @Override
//            public void onCompletion(RecordMetadata metadata, Exception exception) {
//
//            }
//        });

    }

}
