package com.app.controllers;


import com.app.helpers.Params;
import com.app.helpers.ServiceProxy;
import com.library.helpers.BaseResponse;
import com.library.helpers.Helper;
import com.library.helpers.HelperPaging;
import com.library.helpers.UtilsDate;
import com.library.models.Profile;
import com.library.models.User;
import com.library.models.UserProfiles;
import com.library.service.EventsLogService;
import org.json.simple.JSONObject;
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
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

@Controller
public class UserController {

        /**
         *
         * @param model
         * @param pageable
         * @return
         */
        @RequestMapping(value = "users", method = RequestMethod.GET)
        public String actionIndex(ModelMap model, @PageableDefault(sort = { "name"}, value = 5, page = 0) Pageable pageable) {

            //get info
            BaseResponse objResponse = (new ServiceProxy())
                    .getJsonData("api/users", (new ServiceProxy()).encodePageableParams(pageable));

            //Pageable result objt
            JSONObject dataResponse = (JSONObject) objResponse.getData();

            //check result
            ArrayList<User> users = (ArrayList<User>) dataResponse.get("content");

            model.addAttribute("objPaging", (new HelperPaging().getResponsePaging(pageable, dataResponse)));
            model.addAttribute("users", users);

            return  "/views/user/index";
        }


    /**
     *
     * @param model
     * @param id
     * @return
     */
        @RequestMapping(value = {"users/view/{id}"}, method = {RequestMethod.GET})
        public String actionView(ModelMap model, @PathVariable UUID id) {

            ArrayList<Params> params1 = new ArrayList<>();
            params1.add(new Params("id", id.toString()));

            ServiceProxy oServiceProxy = new ServiceProxy();

            //let's get the user details
            User oUser = oServiceProxy
                    .buildParams("api/users/details", params1)
                    .getTarget()
                    .get(User.class);
            oServiceProxy.close();
            System.out.println(oUser);

            //get info
            BaseResponse objResProfiles = (new ServiceProxy())
                    .getJsonData("api/profiles", (new ServiceProxy()).encodePageableParams(PageRequest.of(0,50)));

            //Pageable result objt
            JSONObject dataResponse = (JSONObject) objResProfiles.getData();

            //check result
            ArrayList<Profile> profileList = (ArrayList<Profile>) dataResponse.get("content");

            //setting the variables
            model.addAttribute("oUser", oUser);
            model.addAttribute("oProfile", new Profile());
            model.addAttribute("profileList", profileList);
            model.addAttribute("userProfileList", oUser.getUserProfiles());
            model.addAttribute("objPaging", null);

            return  "/views/user/view";
        }

        /**
         *
         * @param objUser
         * @param result
         * @param model
         * @param request
         * @param id
         * @return
         */
        @RequestMapping(value = {"users/create"}, method = {RequestMethod.GET, RequestMethod.POST})
        public String actionCreate(@Valid @ModelAttribute User objUser,
                                   BindingResult result,
                                   ModelMap model, HttpServletRequest request,
                                   @PathVariable(required = false)
                                           UUID id) {

            if (request.getMethod().equals("POST")) {

                try {

                    if (!result.hasErrors()) {

                        System.out.println(objUser);

                        BaseResponse objResponse = (new ServiceProxy()).postJsonData("api/users/create", objUser, new ArrayList<>() );

                        if(objResponse.getStatusAction() == 1)
                            return "redirect:/users/view/" + ( (LinkedHashMap) objResponse.getData()).get("id");
                        else
                            new Exception(objResponse.getMessage());
                    }else
                        System.out.println(result.getAllErrors());

                } catch (Exception e) {
                    new EventsLogService().AddEventologs(null, "Excption in " + this.getClass().getName(), e.getMessage(), null);
                }
            }

            model.addAttribute("objUser", objUser);
            return "views/user/create";
        }
    }
