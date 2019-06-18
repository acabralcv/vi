package com.app.service;

import com.app.helpers.Params;
import com.app.helpers.ServiceProxy;
import com.library.helpers.BaseResponse;
import com.library.models.Cadeia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

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


}
