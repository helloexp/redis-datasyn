package com.hand.util.producer;

/**
 * Created by wuhanyuan on 2016/11/29.
 */

import com.hand.util.redis.dao.DeadLetterDao;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.UUID;

@Component
public class KafkaProducer {

    @Autowired
    DeadLetterDao deadLetterDao;

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

    public void kp(String message,String topicName) {
        Producer<String, String> producer = new org.apache.kafka.clients.producer.KafkaProducer<String, String>(props);
        producer.send(new ProducerRecord<String, String>(topicName, message, message), new Callback() {
            public void onCompletion(RecordMetadata metadata, Exception e) {
                if (e != null){
                    e.printStackTrace();
                    HashMap<String, Object> map = new HashMap<>();
                    String DLID = UUID.randomUUID().toString();
                    map.put("DLID", DLID);
                    map.put("message", message);
                    long diff = new Date().getTime();
                    map.put("dietime", diff);
                    deadLetterDao.add(map);
                }
            }
        });
        producer.close();

    }

}
