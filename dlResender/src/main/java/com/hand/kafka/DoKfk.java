package com.hand.kafka;

import com.hand.config.Config;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Component
public class DoKfk {

    int cpuNums = Runtime.getRuntime().availableProcessors();
    ExecutorService pool = Executors.newFixedThreadPool(cpuNums * 20);

    @Autowired
    private Config config;


    public void onMessage(final String message) {

        String time = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");

        if (message != null) {
            pool.execute(new KfkThread(config, message));
        }

    }

}
