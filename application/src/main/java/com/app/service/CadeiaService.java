package com.app.service;

import com.app.helpers.Params;
import com.app.helpers.ServiceProxy;
import com.library.helpers.BaseResponse;
import com.library.models.Cadeia;

public class CadeiaService {
    private static final ServiceProxy oServiceProxy = new ServiceProxy();

    public static Cadeia findOne(String id){

        BaseResponse oBaseResponse = oServiceProxy
                .buildParams("api/cadeias/details", new Params().Add(new Params("id", id)).Get())
                .getTarget()
                .get(BaseResponse.class);
        oServiceProxy.close();

        Cadeia oCadeia = (Cadeia) BaseResponse.convertToModel(oBaseResponse, new Cadeia());

        return oCadeia;
    }
}
