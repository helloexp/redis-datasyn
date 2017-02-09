package com.hand.dto;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DongFan on 2016/12/29.
 */
public class BaseMap {
    private Long creationTime;
    private String creationBy;
    private Long updateTime;
    private String updateBy;

    public BaseMap() {
        this.setCreationTime(System.currentTimeMillis());
        this.setUpdateTime(System.currentTimeMillis());
    }

    public Long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Long creationTime) {
        this.creationTime = creationTime;
    }

    public String getCreationBy() {
        return creationBy;
    }

    public void setCreationBy(String creationBy) {
        this.creationBy = creationBy;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Map<String, Object> getBaseMap() {
        Map map = new HashMap<>();
        map.put("creationTime", this.getCreationTime());
        map.put("creationBy", this.getCreationTime());
        map.put("updateTime", this.getCreationTime());
        map.put("updateBy", this.getCreationTime());
        return map;
    }
}
