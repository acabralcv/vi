package com.app.service;

import com.app.helpers.Params;
import com.app.helpers.ServiceProxy;
import com.library.helpers.BaseResponse;
import com.library.models.Eventslog;
import com.library.models.User;

/**
 * Service for app all logs
 */
public class EventslogService {

    private static final ServiceProxy oServiceProxy = new ServiceProxy();

    /**
     * Service to get a single event log
     * @param id
     * @return
     */
    public static Eventslog findOne(String id){

        BaseResponse oBaseResponse = oServiceProxy
                .buildParams("api/eventslog/details", new Params().Add(new Params("id", id)).Get())
                .getTarget()
                .get(BaseResponse.class);
        oServiceProxy.close();

        Eventslog oEventslog = (Eventslog) BaseResponse.convertToModel(oBaseResponse, new Eventslog());

        return oEventslog;
    }
}
