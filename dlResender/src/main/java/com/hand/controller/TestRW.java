package com.hand.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hand.dto.DeadLetterDao;
import com.hand.kafka.DoKfk;
import com.hand.kafka.KafkaProducer;
import com.hand.util.json.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wuhanyuan on 2017/1/6.
 */

@RestController
public class TestRW {
    @Autowired
    private DeadLetterDao deadLetterDao;
    @Autowired
    private DoKfk doKfk;
    private ObjectMapper objectMapper = new ObjectMapper();

    {
        objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
    }

    @RequestMapping(value = "resend")
    public void get(){
        while (1>0){
            try {
                List<Map<String, ?>> list = new ArrayList<>();
                list = deadLetterDao.selectAll();
                for (int i=0;i<list.size();i++){
                    Map dlmessage = list.get(i);
                    Object message = dlmessage.get("message");
                    doKfk.onMessage(message.toString());
                    deadLetterDao.delete(dlmessage.get("DLID").toString());
                }
            } catch(Exception exception){
                exception.printStackTrace();
            }
        }
    }
}
