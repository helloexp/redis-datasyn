package com.hand.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by wuhanyuan on 2017/2/8.
 */
@Configurable
@ConfigurationProperties(prefix = "daoConfig",locations = "classpath:daoConfig.yml")
public class TestConfig {

    private Map<String, Route> routes = new HashMap();

    public Map<String, Route> getRoutes() {
        return routes;
    }

    public void setRoutes(Map<String, Route> routes) {
        this.routes = routes;
    }



    @PostConstruct
    public void init() {
        Iterator var1 = this.routes.entrySet().iterator();

//        CommonDao commonDao;

        while(var1.hasNext()) {
            Map.Entry entry = (Map.Entry)var1.next();
            Route value = (Route)entry.getValue();
            if(!StringUtils.hasText(value.getCatelog())) {
                value.catelog= (String)entry.getKey();
            }
            if(!StringUtils.hasText(value.getClazz())) {
                value.clazz= (String)entry.getKey();
            }
            if(!StringUtils.hasText(value.getHashTag())) {
                value.hashTag= (String)entry.getKey();
            }
            if(!StringUtils.hasText(value.getTableName())) {
                value.tableName= (String)entry.getKey();
            }
            if(!StringUtils.hasText(value.getType())) {
                value.type= (String)entry.getKey();
            }
            if(!StringUtils.hasText(value.getValue())) {
                value.value= (String)entry.getKey();
            }
        }
    }

    public static class Route {

        private String catelog = "";
        private String clazz = "";
        private String hashTag = "";
        private String tableName = "";
        private String type = "";
        private String value = "";

        public Route(){};



//        public String getNum() {
//            return num;
//        }
//
//        public void setNum(String num) {
//            this.num = num;
//        }
//
//        public String getId() {
//            return id;
//        }
//
//        public void setId(String id) {
//            this.id = id;
//        }
        /* ----------------------------getter&setter-------------------------------------------------- */
        public String getCatelog() {
            return catelog;
        }

        public void setCatelog(String catelog) {
            this.catelog = catelog;
        }

        public String getClazz() {
            return clazz;
        }

        public void setClazz(String clazz) {
            this.clazz = clazz;
        }

        public String getHashTag() {
            return hashTag;
        }

        public void setHashTag(String hashTag) {
            this.hashTag = hashTag;
        }

        public String getTableName() {
            return tableName;
        }

        public void setTableName(String tableName) {
            this.tableName = tableName;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }


    }
}
