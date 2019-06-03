package com.app.controllers;


import com.app.exceptions.ResourceNotFoundException;
import com.app.helpers.Params;
import com.app.helpers.ServiceProxy;
import com.app.service.UserService;
import com.library.helpers.BaseResponse;
import com.library.helpers.HelperPaging;
import com.library.models.Profile;
import com.library.models.User;
import com.library.service.EventsLogService;
import org.json.simple.JSONObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
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
public class UserController {

    /**
     *
     * @param model
     * @param pageable
     * @return
     */
    @RequestMapping(value = "users", method = RequestMethod.GET)
    public String actionIndex(ModelMap model, @PageableDefault(sort = { "name"}, value = 10, page = 0) Pageable pageable) {

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

        User oUser = UserService.findOne(id.toString());
        if(oUser == null)
            throw new ResourceNotFoundException("Não possivel encontrar o 'Utilizador' solicitado");

        //get profiles
        BaseResponse objResProfiles = (new ServiceProxy())
                .getJsonData("api/profiles", (new ServiceProxy()).encodePageableParams(PageRequest.of(0,50)));
        JSONObject dataResponse = (JSONObject) objResProfiles.getData();

        //setting the variables
        model.addAttribute("oUser", oUser);
        model.addAttribute("oProfile", new Profile());
        model.addAttribute("profileList",  (ArrayList<Profile>) dataResponse.get("content"));
        model.addAttribute("objPaging", null);

        return  "/views/user/view";
    }

    /**
     *
     * @param objUser
     * @param result
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = {"users/create"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String actionCreate(@Valid @ModelAttribute User objUser, BindingResult result,
                               ModelMap model, HttpServletRequest request) {

            if (request.getMethod().equals("POST")) {

                if (!result.hasErrors()){

                    BaseResponse oBaseResponse = (new ServiceProxy()).postJsonData("api/users/create", objUser, new ArrayList<>() );
                    User createdUser = (User) BaseResponse.convertToModel(oBaseResponse, new User());

                    if(createdUser != null)
                        return "redirect:/users/view/" + createdUser.getId();
                    else
                        throw new InternalError(oBaseResponse.getMessage());
                }
            }

            model.addAttribute("objUser", objUser);
            return "views/user/create";
    }

    @RequestMapping(value = {"users/update/{id"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String actionUpdate(@Valid @ModelAttribute User objUser, BindingResult result,
                               ModelMap model, HttpServletRequest request, @PathVariable UUID id) {

            if (request.getMethod().equals("POST")) {

                if (!result.hasErrors()){

                    BaseResponse oBaseResponse = (new ServiceProxy()).postJsonData("api/users/update    ", objUser, new ArrayList<>() );
                    User createdUser = (User) BaseResponse.convertToModel(oBaseResponse, new User());

                    if(createdUser != null)
                        return "redirect:/users/view/" + createdUser.getId();
                    else
                        throw new InternalError(oBaseResponse.getMessage());
                }
            }

            User oUser = UserService.findOne(id.toString());
            if(oUser == null)
                throw new ResourceNotFoundException("Não possivel encontrar o 'Utilizador' solicitado");

            model.addAttribute("objUser", objUser);
            return "views/user/create";
    }

}
