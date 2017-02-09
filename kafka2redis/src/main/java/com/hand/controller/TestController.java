package com.hand.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hand.config.Config;
import com.hand.consumer.ConsumerHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Hand on 2016/11/14.
 */

@RestController
public class TestController {

    @Autowired
    private Config config;

    private ObjectMapper objectMapper = new ObjectMapper();

    {
        objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
    }

    @RequestMapping(value = "add")
    public String add() {

//        Properties props = new Properties();
//
//        props.put("bootstrap.servers", config.getBootstrapservers());
//        props.put("group.id", config.getKafkagroupid());
//
////        final Map<String, List<ConsumerThread>> consumersMap = new TreeMap<String, List<ConsumerThread>>();
//        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
//        for (String topic : config.getTopics()) {
//            TopicConfig topicConfig = new TopicConfig(props, topic);
////            List<ConsumerThread> consumers = new ArrayList<ConsumerThread>(topicConfig.getThreadCount());
//            consumer.subscribe(Arrays.asList(config.getKafkasendtopic()));
//            for (int i = 0; i < topicConfig.getThreadCount(); i++) {
//                try {
//                    consumers.add(new ConsumerThread(config, topicConfig));
//                } catch (Exception e) {
//
//                }
//            }
//            consumersMap.put(topic, consumers);
//        }
//        for (Map.Entry<String, List<ConsumerThread>> entry : consumersMap.entrySet()) {
//            for (ConsumerThread consumer : entry.getValue()) {
//                consumer.start();
//            }
//        }
//
//        Runtime.getRuntime().addShutdownHook(new Thread() {
//            @Override
//            public void run() {
//                for (Map.Entry<String, List<ConsumerThread>> entry : consumersMap.entrySet()) {
//                    for (ConsumerThread consumer : entry.getValue()) {
//                        consumer.setRunning(false);
//                    }
//                }
//            }
//        });
//
//        for (Map.Entry<String, List<ConsumerThread>> entry : consumersMap.entrySet()) {
//            for (ConsumerThread consumer : entry.getValue()) {
//                try {
//                    consumer.join();
//                } catch (Exception e) {
//                }
//            }
//        }

        int workerNum = Runtime.getRuntime().availableProcessors();
        ConsumerHandler consumers = new ConsumerHandler(config);
        consumers.execute(workerNum);
        try {
            Thread.sleep(1000000);
        } catch (InterruptedException ignored) {}
        consumers.shutdown();

        return "ok";
    }

}
