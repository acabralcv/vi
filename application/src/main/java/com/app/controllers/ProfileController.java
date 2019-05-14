package com.app.controllers;

import com.app.helpers.Params;
import com.app.helpers.ServiceProxy;
import com.library.helpers.BaseResponse;
import com.library.helpers.Helper;
import com.library.models.Profile;
import com.library.models.WfProcess;
import com.library.service.EventsLogService;
import org.json.simple.JSONObject;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.UUID;

@Controller
public class ProfileController {

    @RequestMapping(value = "admin/profiles", method = RequestMethod.GET)
    public String actionIndex(ModelMap model, Pageable pageable) {

        ArrayList<Params> p = new ArrayList<>();
        p.add(new Params("page", "0"));
        p.add(new Params("size", "10"));

        JSONObject objResponse = (new ServiceProxy()).getJsonData("api/profiles", p);

        System.out.println(objResponse.get("content"));

        return  "/views/profile/index";
    }
    @RequestMapping(value = {"admin/profile/create"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String actionCreate(@Valid @ModelAttribute Profile objProfile,
                                BindingResult result,
                                ModelMap model, HttpServletRequest request,
                                @PathVariable(required = false)
                                UUID id) {

        try {

            if (request.getMethod().equals("POST")) {
                if (!result.hasErrors()) {

                    ArrayList<Params> p = new ArrayList<>();

                    BaseResponse objResponse = (new ServiceProxy()).postJsonData("api/profiles/create", objProfile, new ArrayList<>() );

                    System.out.println( "PROFILES:: " + objResponse.getStatusAction());

                    if(objResponse.getStatusAction() == 1)
                        return "redirect:/admin/profile";
                    else
                        new Exception(objResponse.getMessage());

                }

            }

            model.addAttribute("objProfile", objProfile);

        } catch (Exception e) {
            new EventsLogService().AddEventologs(null, "Excption in " + this.getClass().getName(), e.getMessage(), null);
        }
        return "views/profile/create";
    }
}
