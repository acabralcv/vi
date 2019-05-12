package com.library.helpers;

public class BaseResponse {

    private int statusAction;
    private String message;
    private Object data;

    public  BaseResponse(){ }

    public BaseResponse(int statusAction, String message, Object data){
        this.statusAction = statusAction;
        this.message = message;
        this.data = data;
    }
    public BaseResponse  getResponse(int statusAction, String message, Object data){
        return new BaseResponse(statusAction,message,data);
    }

    public int getStatusAction() {
        return statusAction;
    }

    public void setStatusAction(int statusAction) {
        this.statusAction = statusAction;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
