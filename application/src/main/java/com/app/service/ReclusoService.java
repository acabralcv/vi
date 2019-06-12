package com.app.service;

import com.app.helpers.Params;
import com.app.helpers.ServiceProxy;
import com.library.helpers.BaseResponse;
import com.library.models.Recluso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

public class ReclusoService {


    @Autowired
    private final Environment env;

    private final ServiceProxy oServiceProxy;

    public ReclusoService(Environment _env){
        this.env = _env;
        oServiceProxy = new ServiceProxy(this.env);
    }

    /**
     * Service to get a single recluso
     * @param id
     * @return
     */
    public Recluso findOne(String id){

        Recluso oRecluso = null;

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
