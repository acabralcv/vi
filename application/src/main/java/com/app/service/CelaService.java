package com.app.service;

import com.app.helpers.Params;
import com.app.helpers.ServiceProxy;
import com.library.helpers.BaseResponse;
import com.library.models.Cela;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

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
}
