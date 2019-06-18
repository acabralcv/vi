package com.app.service;

import com.app.helpers.Params;
import com.app.helpers.ServiceProxy;
import com.library.helpers.BaseResponse;
import com.library.models.Complexo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

public class ComplexoService {

    @Autowired
    private final Environment env;

    private final ServiceProxy oServiceProxy;

    public ComplexoService(Environment _env){
        this.env = _env;
        oServiceProxy = new ServiceProxy(this.env);
    }

    public Complexo findOne(String id){

        BaseResponse oBaseResponse = oServiceProxy
                .buildParams("api/complexos/details", new Params().Add(new Params("id", id)).Get())
                .getTarget()
                .get(BaseResponse.class);
        oServiceProxy.close();

        return (Complexo) BaseResponse.convertToModel(oBaseResponse, new Complexo());
    }
}
