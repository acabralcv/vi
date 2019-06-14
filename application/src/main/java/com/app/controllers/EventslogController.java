package com.app.controllers;

import com.app.exceptions.ResourceNotFoundException;
import com.app.helpers.Params;
import com.app.helpers.ServiceProxy;
import com.app.service.DomainService;
import com.app.service.EventslogService;
import com.library.helpers.BaseResponse;
import com.library.helpers.HelperPaging;
import com.library.models.Domain;
import com.library.models.Eventslog;
import com.library.models.Recluso;
import com.library.service.EventsLogService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.UUID;

@Controller
public class EventslogController {

    @Autowired
    private Environment env;

    @RequestMapping(value ="admin/eventslog", method =RequestMethod.GET)
    public String actionIndex(ModelMap model, @PageableDefault(sort = {"message"}, value = 10, page = 0) Pageable pageable) {

        ServiceProxy oServiceProxy = new ServiceProxy(env);
        //get info
        BaseResponse objResponse = oServiceProxy
                .getJsonData("api/eventslog", oServiceProxy.encodePageableParams(pageable));
        oServiceProxy.close();

        //Pageable result objt
        JSONObject dataResponse = (JSONObject) objResponse.getData();

        //check result
        ArrayList<Eventslog> eventslogs = (dataResponse != null) ? (ArrayList<Eventslog>) dataResponse.get("content") : null;

        model.addAttribute("objPaging", (new HelperPaging().getResponsePaging(pageable, dataResponse)));
        model.addAttribute("eventslogs", eventslogs);

        return  "/views/eventslog/index";
    }

    /**
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "admin/eventslog/view/{id}", method = RequestMethod.GET)
    public String actionDetails(ModelMap model, @PathVariable UUID id) {

        Eventslog oEventslog = new EventslogService(env).findOne(id.toString());

        if (oEventslog == null)
            new ResourceNotFoundException("Não possivel encontrar o recurso solicitado");

        model.addAttribute("oEventslog", oEventslog);

        return "/views/eventslog/view";
    }
}