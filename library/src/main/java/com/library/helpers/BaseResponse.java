package com.library.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.models.Profile;
import jdk.nashorn.internal.parser.JSONParser;
//import org.json.simple.JSONObject;
import org.json.simple.JSONObject;
import org.springframework.ui.Model;

import java.io.IOException;

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

    public BaseResponse  getObjResponse(int statusAction, String message, Object data){
        return new BaseResponse(statusAction,message, data);
    }

    public static Object convertToModel(BaseResponse objResponse){

        try{

            //Pageable result objt
            JSONObject dataResponse = (JSONObject) objResponse.getData();

            ObjectMapper m = new ObjectMapper();
            Object oObject = m.readValue(dataResponse.toString(), Object.class);
            return oObject;

        }catch (IOException e){
            return  null;
        }
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
