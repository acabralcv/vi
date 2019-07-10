package com.app.service;

import com.app.helpers.Params;
import com.app.helpers.ServiceProxy;
import com.library.helpers.BaseResponse;
import com.library.models.Complexo;
import com.library.models.Setor;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.UUID;

public class SetorService {

    @Autowired
    private final Environment env;

    private final ServiceProxy oServiceProxy;

    public SetorService(Environment _env){
        this.env = _env;
        oServiceProxy = new ServiceProxy(this.env);
    }

    public Setor findOne(String id){

        BaseResponse oBaseResponse = oServiceProxy
                .buildParams("api/setores/details", new Params().Add(new Params("id", id)).Get())
                .getTarget()
                .get(BaseResponse.class);
        oServiceProxy.close();

        return (Setor) BaseResponse.convertToModel(oBaseResponse, new Setor());
    }

    public ArrayList<Setor> findAllByComplexo(UUID id_complexo){

        BaseResponse oBaseResponse = oServiceProxy
                .buildParams("api/setores", new Params().Add(new Params("id_complexo", id_complexo.toString())).Get())
                .getTarget()
                .get(BaseResponse.class);
        oServiceProxy.close();

        //Pageable result objt
        ArrayList<Setor> dataResponse = (ArrayList<Setor>) oBaseResponse.getData();

        return dataResponse;
    }


    /**
     *
     * @param limit
     * @return
     */
    public ArrayList<Setor> findAll(Integer limit){

        if(limit <= 0) limit = 15;

        BaseResponse oBaseResponse = oServiceProxy
                .getJsonData("api/setores", oServiceProxy.encodePageableParams(PageRequest.of(0,limit)));

        JSONObject dataResponse = (JSONObject) oBaseResponse.getData();

        return dataResponse != null ? (ArrayList<Setor>) dataResponse.get("content") : new ArrayList<>();
    }
}
