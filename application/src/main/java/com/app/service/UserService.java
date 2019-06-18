package com.app.service;

import com.app.helpers.Params;
import com.app.helpers.ServiceProxy;
import com.library.helpers.BaseResponse;
import com.library.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

public class UserService {


    @Autowired
    private final Environment env;

    private final ServiceProxy oServiceProxy;

    public UserService(Environment _env){
        this.env = _env;
        oServiceProxy = new ServiceProxy(this.env);
    }

    /**
     *
     * @param id
     * @return
     */
    public User findOne(String id){

        BaseResponse oBaseResponse = oServiceProxy
                .buildParams("api/users/details", new Params().Add(new Params("id", id)).Get())
                .getTarget()
                .get(BaseResponse.class);

            oServiceProxy.close();

        User oUser = (User) BaseResponse.convertToModel(oBaseResponse, new User());

        return oUser;
    }
}
