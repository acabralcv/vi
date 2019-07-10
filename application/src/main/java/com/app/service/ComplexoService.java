package com.app.service;

import com.app.helpers.Params;
import com.app.helpers.ServiceProxy;
import com.library.helpers.BaseResponse;
import com.library.models.Complexo;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.UUID;

public class ComplexoService {

    @Autowired
    private final Environment env;

    private final ServiceProxy oServiceProxy;

    public ComplexoService(Environment _env){
        this.env = _env;
        oServiceProxy = new ServiceProxy(this.env);
    }

    /**
     *
     * @param id
     * @return
     */
    public Complexo findOne(String id){

        BaseResponse oBaseResponse = oServiceProxy
                .buildParams("api/complexos/details", new Params().Add(new Params("id", id)).Get())
                .getTarget()
                .get(BaseResponse.class);
        oServiceProxy.close();

        return (Complexo) BaseResponse.convertToModel(oBaseResponse, new Complexo());
    }

    /**
     *
     * @param id_cadeia
     * @return
     */
    public ArrayList<Complexo> findAllByCadeia(UUID id_cadeia){

        BaseResponse oBaseResponse = oServiceProxy
                .buildParams("api/complexos", new Params().Add(new Params("id_cadeia", id_cadeia.toString())).Get())
                .getTarget()
                .get(BaseResponse.class);
        oServiceProxy.close();

        //Pageable result objt
        ArrayList<Complexo> dataResponse = (ArrayList<Complexo>) oBaseResponse.getData();

        return dataResponse;
    }

    /**
     *
     * @param limit
     * @return
     */
    public ArrayList<Complexo> findAll(Integer limit){

        if(limit <= 0) limit = 15;

        BaseResponse oBaseResponse = oServiceProxy
                .getJsonData("api/complexos", oServiceProxy.encodePageableParams(PageRequest.of(0,limit)));

        JSONObject dataResponse = (JSONObject) oBaseResponse.getData();

        return dataResponse != null ? (ArrayList<Complexo>) dataResponse.get("content") : new ArrayList<>();
    }
}
