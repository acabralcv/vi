package com.app.helpers;

import com.library.helpers.BaseResponse;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.data.domain.Pageable;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import javax.ws.rs.client.*;

public class ServiceProxy {

    @Autowired
    private final Environment env;

    private String baseUrl;
    private String serviceAddress;
    private String accessToken;
    private Client client;

//    public ServiceProxy(){
//    }

    public ServiceProxy(Environment _env){
        this.env = _env;
        this.baseUrl = env.getProperty("api.service.baseUrl");
        this.accessToken = env.getProperty("api.service.accessToken");
    }

    public ArrayList<Params> encodePageableParams(Pageable pageable)  {

        System.out.println(pageable.getSort());

        ArrayList<Params> p = new ArrayList<>();
        p.add(new Params("page", "" + pageable.getPageNumber()));
        p.add(new Params("size", "" + pageable.getPageSize()));
        p.add(new Params("sort", "" + pageable.getSort()));
        return p;
    }

    public String getServiceUrl(String resource, ArrayList<Params> params)  {

        String _params = "params=1";

            try {
                for(Params objParam: params) {
                _params = _params + "&" + objParam.name + "=" + URLEncoder.encode(objParam.value, "UTF-8").toString(); //[key];
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        return baseUrl + resource + "?" + _params + "&access_token=" + accessToken;
    }


    public ServiceProxy buildParams(String resource, ArrayList<Params> params){
        this.serviceAddress = this.getServiceUrl(resource, params);
        return this;
    }

    public Invocation.Builder getTarget(){

        this.client = ClientBuilder.newClient();
        WebTarget target = client.target(this.serviceAddress);

        return target.request();
    }

    public void close(){
        if(this.client != null)
            this.client.close();
    }


    public BaseResponse getJsonData(String resourse, ArrayList<Params> params) {

        try{

            String url = this.getServiceUrl(resourse, params);

            //https://www.logicbig.com/how-to/code-snippets/jcode-jax-rs-client-and-clientbuilder.html

//            HttpAuthenticationFeature feature = HttpAuthenticationFeature.basicBuilder()
//                    .nonPreemptive()
//                    .credentials("admin", "123")
//                    .build();

            Client client = ClientBuilder.newClient()/*.register(new Authenticator("admin", "123"))*/;
//            client.register(feature);
            WebTarget target = client.target(url);

            String s = target.request().get(String.class);

            client.close();

            JSONParser parser = new JSONParser();
            Object obj = parser.parse(s);
            JSONObject res = (JSONObject)obj;

            BaseResponse oBaseResponse = new BaseResponse().getObjResponse(
                    ((Long) res.get("statusAction")).intValue(),
                    res.get("message").toString(),
                    res.get("data"));

            return oBaseResponse;

        }catch(Exception pe) {
            return new BaseResponse().getObjResponse(0,pe.getMessage(), null);
        }
    }



    public BaseResponse getModelDetails(String resourse, ArrayList<Params> params) {

        try{

            String url = this.getServiceUrl(resourse, params);

            //https://www.logicbig.com/how-to/code-snippets/jcode-jax-rs-client-and-clientbuilder.html
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(url);

            Object oBaseResponse = target.request().get(Object.class);

            client.close();

            return new BaseResponse().getObjResponse(1,"ok", oBaseResponse);

        }catch(Exception pe) {
            return new BaseResponse().getObjResponse(0,pe.getMessage(), null);
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
            return new BaseResponse().getObjResponse(0,pe.getMessage(), null);
        }
    }



}
