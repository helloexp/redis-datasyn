package com.hand;

import com.hand.kafka.producer.KafkaProducer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

/**
 * Created by wuhanyuan on 2016/11/29.
 */

@SpringBootApplication
public class App {
    public static void main( String[] args )
    {
        new SpringApplicationBuilder(App.class).web(true).run(args);
    }

}
