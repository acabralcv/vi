package com.app.helpers;

import com.library.helpers.BaseResponse;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.domain.Pageable;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import javax.ws.rs.client.*;

@Configuration
@ComponentScan(basePackages = { "com.app.*" })
@PropertySource("classpath:application.properties")
public class ServiceProxy {

    @Value("$api.service.baseUrl}")
    private String baseUrl; // = "http://localhost:8081/";

    public String accessToken = "access_token=Igh6KZaqq99UBUZwpY1nD-7ZpwAPpROx-ejeBMm9CMoLz4hs1WwKKgSQWgMocYNWaOQCz44kMq38uXKVr90BP7kPjyTw5QwOQ-yN96Mqg-rjH4OiBnAA_M8F3di4xZvE";

    private Client client;

    //To resolve ${} in @Value
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
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
        this.accessToken = this.getServiceUrl(resource, params);
        return this;
    }

    public Invocation.Builder getTarget(){

        this.client = ClientBuilder.newClient();
        WebTarget target = client.target(this.accessToken);

        return target.request();
    }

    public void close(){
        this.client.close();
    }

    public BaseResponse getJsonData(String resourse, ArrayList<Params> params) {

        try{

            String url = this.getServiceUrl(resourse, params);

            //https://www.logicbig.com/how-to/code-snippets/jcode-jax-rs-client-and-clientbuilder.html
            Client client = ClientBuilder.newClient();
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

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

}
