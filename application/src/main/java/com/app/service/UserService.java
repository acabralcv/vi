package com.app.service;

import com.app.helpers.Params;
import com.app.helpers.ServiceProxy;
import com.library.helpers.BaseResponse;
import com.library.models.User;

public class UserService {

    private static final ServiceProxy oServiceProxy = new ServiceProxy();

    public static User findOne(String id){

        BaseResponse oBaseResponse = oServiceProxy
                .buildParams("api/users/details", new Params().Add(new Params("id", id)).Get())
                .getTarget()
                .get(BaseResponse.class);
            oServiceProxy.close();

        User oUser = (User) BaseResponse.convertToModel(oBaseResponse, new User());

        return oUser;
    }
}
