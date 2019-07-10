package com.app.service;

import com.app.helpers.Params;
import com.app.helpers.ServiceProxy;
import com.library.helpers.BaseResponse;
import com.library.models.Cela;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.UUID;

public class CelaService {

    @Autowired
    private final Environment env;

    private final ServiceProxy oServiceProxy;

    public CelaService(Environment _env){
        this.env = _env;
        oServiceProxy = new ServiceProxy(this.env);
    }

    public Cela findOne(String id){

        BaseResponse oBaseResponse = oServiceProxy
                .buildParams("api/celas/details", new Params().Add(new Params("id", id)).Get())
                .getTarget()
                .get(BaseResponse.class);
        oServiceProxy.close();

        return (Cela) BaseResponse.convertToModel(oBaseResponse, new Cela());
    }

    public ArrayList<Cela> findAllBySetor(UUID id_ala){

        BaseResponse oBaseResponse = oServiceProxy
                .buildParams("api/celas", new Params().Add(new Params("id_ala", id_ala.toString())).Get())
                .getTarget()
                .get(BaseResponse.class);
        oServiceProxy.close();

        //Pageable result objt
        ArrayList<Cela> dataResponse = (ArrayList<Cela>) oBaseResponse.getData();

        return dataResponse;
    }
}
