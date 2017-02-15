package com.hand.dto;

import java.util.Map;

/**
 * Created by DongFan on 2016/12/15.
 */
public class PageInfo {
    private Integer page = 1;
    private Integer pageSize = 10;

    public PageInfo() {
    }

    public PageInfo(Map<String, ?> map) {
        this.page = map.get("page") != null ? (Integer) map.get("page") : 1;
        this.pageSize = map.get("pageSize") != null ? (Integer) map.get("pageSize") : 10;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
