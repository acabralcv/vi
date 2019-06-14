package com.app.service;

import com.app.helpers.Params;
import com.app.helpers.ServiceProxy;
import com.library.helpers.BaseResponse;
import com.library.models.Eventslog;
import com.library.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

/**
 * Service for app all logs
 */
public class EventslogService {

    @Autowired
    private final Environment env;

    private final ServiceProxy oServiceProxy;

    public EventslogService(Environment _env){
        this.env = _env;
        oServiceProxy = new ServiceProxy(this.env);
    }

    /**
     * Service to get a single event log
     * @param id
     * @return
     */
    public Eventslog findOne(String id){

        BaseResponse oBaseResponse = oServiceProxy
                .buildParams("api/eventslog/details", new Params().Add(new Params("id", id)).Get())
                .getTarget()
                .get(BaseResponse.class);

        oServiceProxy.close();

        Eventslog oEventslog = (Eventslog) BaseResponse.convertToModel(oBaseResponse, new Eventslog());

        return oEventslog;
    }
}
