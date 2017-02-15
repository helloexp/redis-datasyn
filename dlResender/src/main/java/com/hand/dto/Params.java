package com.hand.dto;

import java.util.List;
import java.util.Map;

/**
 * Created by Hand on 2017/1/6.
 */
public class Params {
    private Map<String,List<Object>> equals;
    private Map<String,ScoreRange> ranges;
    private Map<String,Object> matches;

    public Map<String, List<Object>> getEquals() {
        return equals;
    }

    public void setEquals(Map<String, List<Object>> equals) {
        this.equals = equals;
    }

    public Map<String, ScoreRange> getRanges() {
        return ranges;
    }

    public void setRanges(Map<String, ScoreRange> ranges) {
        this.ranges = ranges;
    }

    public Map<String, Object> getMatches() {
        return matches;
    }

    public void setMatches(Map<String, Object> matches) {
        this.matches = matches;
    }
}
