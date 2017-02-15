package com.hand.dto;

/**
 * Created by Hand on 2017/1/5.
 */
public class ScoreRange {
    private double min;
    private double max;
    public ScoreRange(){
    }
    public ScoreRange(double min,double max){
        this.min = min;
        this.max = max;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public void setMax(double max) {
        this.max = max;
    }
}
