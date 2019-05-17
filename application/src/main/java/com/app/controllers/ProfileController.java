package com.app.controllers;

import com.app.helpers.Params;
import com.app.helpers.ServiceProxy;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.helpers.BaseResponse;
import com.library.helpers.Helper;
import com.library.helpers.HelperPaging;
import com.library.models.Profile;
import com.library.models.WfProcess;
import com.library.repository.ProfileRepository;
import com.library.service.EventsLogService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class ProfileController {


    @Autowired
    private ProfileRepository profileRepository;

    /**
     *
     * @param model
     * @param pageable
     * @return
     */
    @RequestMapping(value = "admin/profiles", method = RequestMethod.GET)
    public String actionIndex(ModelMap model, @PageableDefault(sort = { "name"}, value = 5, page = 0) Pageable pageable) {

        //get info
        BaseResponse objResponse = (new ServiceProxy())
                .getJsonData("api/profiles", (new ServiceProxy()).encodePageableParams(pageable));

        //Pageable result objt
        JSONObject dataResponse = (JSONObject) objResponse.getData();

        //check result
        ArrayList<Profile> profiles = (ArrayList<Profile>) dataResponse.get("content");

        model.addAttribute("objPaging", (new HelperPaging().getResponsePaging(pageable, dataResponse)));
        model.addAttribute("profiles", profiles);

        return  "/views/profile/index";
    }





    @RequestMapping(value = {"admin/profiles/view/{id}"}, method = {RequestMethod.GET})
    public String actionView(ModelMap model, @PathVariable UUID id) {

        ArrayList<Params> params = new ArrayList<>();
        params.add(new Params("id", id.toString()));

        ServiceProxy oServiceProxy = new ServiceProxy();

        Profile oProfile = oServiceProxy
                    .buildParams("api/profiles/details", params)
                    .getTarget()
                    .get(Profile.class);
        oServiceProxy.close();
        System.out.println(oProfile);

        model.addAttribute("oProfile", oProfile);

        return  "/views/profile/view";
    }

    /**
     *
     * @param objProfile
     * @param result
     * @param model
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(value = {"admin/profiles/create"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String actionCreate(@Valid @ModelAttribute Profile objProfile,
                                BindingResult result,
                                ModelMap model, HttpServletRequest request,
                                @PathVariable(required = false)
                                UUID id) {

        if (request.getMethod().equals("POST")) {

            try {

                if (!result.hasErrors()) {

                    ArrayList<Params> p = new ArrayList<>();
                    BaseResponse objResponse = (new ServiceProxy()).postJsonData("api/profiles/create", objProfile, new ArrayList<>() );

                    if(objResponse.getStatusAction() == 1)
                        return "redirect:/admin/profiles";
                    else
                        new Exception(objResponse.getMessage());
                }else
                    System.out.println(result.getAllErrors());

            } catch (Exception e) {
                new EventsLogService().AddEventologs(null, "Excption in " + this.getClass().getName(), e.getMessage(), null);
            }
        }

        model.addAttribute("objProfile", objProfile);
        return "views/profile/create";
    }
}
