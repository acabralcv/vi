package com.library.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.models.Profile;
import jdk.nashorn.internal.parser.JSONParser;
//import org.json.simple.JSONObject;
import org.json.simple.JSONObject;
import org.springframework.ui.Model;
import org.springframework.util.ReflectionUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Field;

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

    public static Object convertToModel(BaseResponse objResponse, Object object){

        try{

            ObjectMapper mapper = new ObjectMapper();
            String jsonResult = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(objResponse.getData());

            ObjectMapper m = new ObjectMapper();
            Object oObject = m.readValue( jsonResult, object.getClass());
            return oObject;

        } catch (Exception e) {
            e.printStackTrace();
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
