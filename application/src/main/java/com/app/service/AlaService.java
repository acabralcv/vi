package com.app.service;

import com.app.helpers.Params;
import com.app.helpers.ServiceProxy;
import com.library.helpers.BaseResponse;
import com.library.models.Ala;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.UUID;

public class AlaService {

    @Autowired
    private final Environment env;

    private final ServiceProxy oServiceProxy;

    public AlaService(Environment _env){
        this.env = _env;
        oServiceProxy = new ServiceProxy(this.env);
    }

    public Ala findOne(String id){

        BaseResponse oBaseResponse = oServiceProxy
                .buildParams("api/alas/details", new Params().Add(new Params("id", id)).Get())
                .getTarget()
                .get(BaseResponse.class);
        oServiceProxy.close();

        return (Ala) BaseResponse.convertToModel(oBaseResponse, new Ala());
    }

    public ArrayList<Ala> findAllBySetor(UUID id_setor){

        BaseResponse oBaseResponse = oServiceProxy
                .buildParams("api/alas", new Params().Add(new Params("id_setor", id_setor.toString())).Get())
                .getTarget()
                .get(BaseResponse.class);
        oServiceProxy.close();

        //Pageable result objt
        ArrayList<Ala> dataResponse = (ArrayList<Ala>) oBaseResponse.getData();

        return dataResponse;
    }
}
