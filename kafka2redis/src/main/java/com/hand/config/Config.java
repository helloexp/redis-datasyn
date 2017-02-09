package com.hand.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class Config {
    @Value("${bootstrap.servers}")
    private String bootstrapservers;

    @Value("${request.timeout.ms}")
    private String requesttimeoutms;

    @Value("${max.block.ms}")
    private String maxblockms;

    @Value("${acks}")
    private String acks;

    @Value("${retries}")
    private String retries;

    @Value("${batch.size}")
    private String batchsize;

    @Value("${linger.ms}")
    private String lingerms;

    @Value("${buffer.memory}")
    private String buffermemory;

    @Value("${key.serializer}")
    private String keyserializer;

    @Value("${value.serializer}")
    private String valueserializer;

    @Value("${key.deserializer}")
    private String keydeserializer;

    @Value("${value.deserializer}")
    private String valuedeserializer;

    @Value("${kafka.topic}")
    private String kafkatopic;

    @Value("${kafka.groupid}")
    private String kafkagroupid;

    @Value("${kafka.send.topic}")
    private String kafkasendtopic;

    public String getBootstrapservers() {
        return bootstrapservers;
    }

    public void setBootstrapservers(String bootstrapservers) {
        this.bootstrapservers = bootstrapservers;
    }

    public String getRequesttimeoutms() {
        return requesttimeoutms;
    }

    public void setRequesttimeoutms(String requesttimeoutms) {
        this.requesttimeoutms = requesttimeoutms;
    }

    public String getMaxblockms() {
        return maxblockms;
    }

    public void setMaxblockms(String maxblockms) {
        this.maxblockms = maxblockms;
    }

    public String getAcks() {
        return acks;
    }

    public void setAcks(String acks) {
        this.acks = acks;
    }

    public String getRetries() {
        return retries;
    }

    public void setRetries(String retries) {
        this.retries = retries;
    }

    public String getBatchsize() {
        return batchsize;
    }

    public void setBatchsize(String batchsize) {
        this.batchsize = batchsize;
    }

    public String getLingerms() {
        return lingerms;
    }

    public void setLingerms(String lingerms) {
        this.lingerms = lingerms;
    }

    public String getBuffermemory() {
        return buffermemory;
    }

    public void setBuffermemory(String buffermemory) {
        this.buffermemory = buffermemory;
    }

    public String getKeyserializer() {
        return keyserializer;
    }

    public void setKeyserializer(String keyserializer) {
        this.keyserializer = keyserializer;
    }

    public String getKeydeserializer() {
        return keydeserializer;
    }

    public void setKeydeserializer(String keydeserializer) {
        this.keydeserializer = keydeserializer;
    }

    public String getValueserializer() {
        return valueserializer;
    }

    public void setValueserializer(String valueserializer) {
        this.valueserializer = valueserializer;
    }

    public String getValuedeserializer() {
        return valuedeserializer;
    }

    public void setValuedeserializer(String valuedeserializer) {
        this.valuedeserializer = valuedeserializer;
    }

    public String getKafkatopic() {
        return kafkatopic;
    }

    public void setKafkatopic(String kafkatopic) {
        this.kafkatopic = kafkatopic;
    }

    public String getKafkagroupid() {
        return kafkagroupid;
    }

    public void setKafkagroupid(String kafkagroupid) {
        this.kafkagroupid = kafkagroupid;
    }

    public String getKafkasendtopic() {
        return kafkasendtopic;
    }

    public void setKafkasendtopic(String kafkasendtopic) {
        this.kafkasendtopic = kafkasendtopic;
    }
}
