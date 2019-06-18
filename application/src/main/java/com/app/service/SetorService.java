package com.app.service;

import com.app.helpers.Params;
import com.app.helpers.ServiceProxy;
import com.library.helpers.BaseResponse;
import com.library.models.Setor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

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
}
