package com.app.helpers;

import com.library.helpers.BaseResponse;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ServiceProxy {

    public String baseUrl = "http://localhost:8085/";
    public String serviceAccessToken = "access_token=Igh6KZaqq99UBUZwpY1nD-7ZpwAPpROx-ejeBMm9CMoLz4hs1WwKKgSQWgMocYNWaOQCz44kMq38uXKVr90BP7kPjyTw5QwOQ-yN96Mqg-rjH4OiBnAA_M8F3di4xZvE";

    public String getServiceUrl(String resource, ArrayList<Params> params)  {

        String _params = "params=1";

            try {
                for(Params objParam: params) {
                _params = _params + "&" + objParam.name + "=" + URLEncoder.encode(objParam.value, "UTF-8").toString(); //[key];
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        return baseUrl + resource + "?" + _params + "&access_token=" + serviceAccessToken;
    }

    public JSONObject getJsonData(String resourse, ArrayList<Params> params) {

        try{

            String url = this.getServiceUrl(resourse, params);

            //https://www.logicbig.com/how-to/code-snippets/jcode-jax-rs-client-and-clientbuilder.html
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(url);

            String s = target.request().get(String.class);


            client.close();

            JSONParser parser = new JSONParser();
            Object obj = parser.parse(s);
            JSONObject objResponse = (JSONObject)obj;

            return objResponse;

        }catch(ParseException pe) {
            return null;
        }
    }


    public BaseResponse postJsonData(String resourse, Object objModel, ArrayList<Params> params) {

        try{


            String url = this.getServiceUrl(resourse, params);

            //https://www.logicbig.com/how-to/code-snippets/jcode-jax-rs-client-and-clientbuilder.html
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(url);

            //post data
            BaseResponse response = target.request().post(Entity.json(objModel), BaseResponse.class);
            System.out.println("\n\nPOST RETURNED:: " + response.toString() + "\n\n");
            client.close();

            if( response.getStatusAction() != 1)
                new Exception(response.getMessage());

            return response;

        }catch(Exception pe) {
            return new BaseResponse().getResponse(0,pe.getMessage(), null);
        }
    }



}
