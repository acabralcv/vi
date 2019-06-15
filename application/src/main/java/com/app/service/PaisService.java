package com.app.service;

import com.app.helpers.ServiceProxy;
import com.library.helpers.BaseResponse;
import com.library.models.Pais;
import com.library.models.Profile;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;

public class PaisService {


    @Autowired
    private final Environment env;

    private final ServiceProxy oServiceProxy;

    public PaisService(Environment _env){
        this.env = _env;
        oServiceProxy = new ServiceProxy(this.env);
    }

    /**
     * Service to get all countries
     * @return
     */
    public ArrayList<Pais> findAll(){

        BaseResponse oBaseResponse = oServiceProxy
                .getJsonData("api/paises", oServiceProxy.encodePageableParams(PageRequest.of(0,200)));

        JSONObject dataResponse = (JSONObject) oBaseResponse.getData();

        return dataResponse != null ? (ArrayList<Pais>) dataResponse.get("content") : new ArrayList<>();
    }
}
