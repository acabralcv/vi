package com.app.service;

import com.app.helpers.Params;
import com.app.helpers.ServiceProxy;
import com.library.helpers.BaseResponse;
import com.library.models.Cadeia;
import com.library.models.Complexo;
import com.library.models.Ilha;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.UUID;

public class CadeiaService {

    @Autowired
    private final Environment env;

    private final ServiceProxy oServiceProxy;

    public CadeiaService(Environment _env){
        this.env = _env;
        oServiceProxy = new ServiceProxy(this.env);
    }

    public Cadeia findOne(String id){

        BaseResponse oBaseResponse = oServiceProxy
                .buildParams("api/cadeias/details", new Params().Add(new Params("id", id)).Get())
                .getTarget()
                .get(BaseResponse.class);
        oServiceProxy.close();

        return (Cadeia) BaseResponse.convertToModel(oBaseResponse, new Cadeia());
    }

    /**
     * Return all cadeias
     * @return
     */
    public ArrayList<Complexo> findAll(Integer limit){

        if(limit <= 0) limit = 15;

        BaseResponse oBaseResponse = oServiceProxy
                .getJsonData("api/cadeias", oServiceProxy.encodePageableParams(PageRequest.of(0,limit)));

        JSONObject dataResponse = (JSONObject) oBaseResponse.getData();

        return dataResponse != null ? (ArrayList<Complexo>) dataResponse.get("content") : new ArrayList<>();
    }


}
