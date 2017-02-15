package com.hand.dto;

/**
 * Created by Hand on 2017/2/8.
 */
public class ServiceResult {
    private boolean isSuccess;
    private String errorCode;
    private Object result;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public ServiceResult(String errorCode,Object result){
        this(false,errorCode,result);
    }

    public ServiceResult(Object result){
        this(true,null,result);
    }

    public ServiceResult(boolean isSuccess,String errorCode){
        this(isSuccess,errorCode,null);
    }

    public ServiceResult(boolean isSuccess,String errorCode,Object result){
        this.isSuccess = isSuccess;
        this.errorCode = errorCode;
        this.result = result;
    }

}
