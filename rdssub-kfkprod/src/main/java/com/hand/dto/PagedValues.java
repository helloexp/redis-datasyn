package com.hand.dto;

import java.util.List;
import java.util.Map;

/**
 * Created by Hand on 2017/1/5.
 */
public class PagedValues {
    private long total;
    private List<Map<String,?>> values;
    public PagedValues(){
    }
    public PagedValues(long total,List<Map<String,?>> values){
        this.total = total;
        this.values = values;
    }

    public long getTotal() {
        return total;
    }

    public List<Map<String, ?>> getValues() {
        return values;
    }
}
