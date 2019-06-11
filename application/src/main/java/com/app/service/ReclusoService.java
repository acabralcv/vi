package com.app.service;

import com.app.helpers.Params;
import com.app.helpers.ServiceProxy;
import com.library.helpers.BaseResponse;
import com.library.models.Recluso;

public class ReclusoService {

    /**
     * Service to get a single recluso
     * @param id
     * @return
     */
    public static Recluso findOne(String id){

        Recluso oRecluso = null;

        ServiceProxy oServiceProxy = new ServiceProxy();
        BaseResponse oBaseResponse = oServiceProxy
                .buildParams("api/reclusos/details", new Params().Add(new Params("id", id)).Get())
                .getTarget()
                .get(BaseResponse.class);
        oServiceProxy.close();

        if(oBaseResponse.getStatusAction() == 1 && oBaseResponse.getData() != null)
            oRecluso = (Recluso) BaseResponse.convertToModel(oBaseResponse, new Recluso());

        return oRecluso;
    }
}
