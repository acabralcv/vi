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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.UUID;

@Controller
//@PreAuthorize("hasAuthority('admin')")
public class UserController {



    @Autowired
    private Environment env;

    /**
     *
     * @param model
     * @param pageable
     * @return
     */
    @RequestMapping(value = "admin/users", method = RequestMethod.GET)
    public String actionIndex(ModelMap model, @PageableDefault(sort = { "name"}, value = 10, page = 0) Pageable pageable) {

        //get info
        ServiceProxy oServiceProxy = new ServiceProxy(env);
        BaseResponse objResponse = (new ServiceProxy(env))
                .getJsonData("api/users", oServiceProxy.encodePageableParams(pageable));
        oServiceProxy.close();

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
    @RequestMapping(value = {"admin/users/view/{id}"}, method = {RequestMethod.GET})
    public String actionView(ModelMap model, @PathVariable UUID id) {

        User oUser = new UserService(env).findOne(id.toString());
        if(oUser == null)
            throw new ResourceNotFoundException("Não possivel encontrar o 'Utilizador' solicitado");

        //get profiles
        ServiceProxy oServiceProxy = new ServiceProxy(env);
        BaseResponse objResProfiles = oServiceProxy
                .getJsonData("api/profiles", oServiceProxy.encodePageableParams(PageRequest.of(0,50)));
        oServiceProxy.close();

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
    @RequestMapping(value = {"admin/users/create"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String actionCreate(@Valid @ModelAttribute User objUser, BindingResult result,
                               ModelMap model, HttpServletRequest request) {

            if (request.getMethod().equals("POST")) {

                if (!result.hasErrors()){

                    ServiceProxy oServiceProxy = new ServiceProxy(env);
                    BaseResponse oBaseResponse = oServiceProxy.postJsonData("api/users/create", objUser, new ArrayList<>() );
                    oServiceProxy.close();

                    User createdUser = (User) BaseResponse.convertToModel(oBaseResponse, new User());

                    if(createdUser != null)
                        return "redirect:/admin/users/view/" + createdUser.getId();
                    else
                        throw new InternalError(oBaseResponse.getMessage());
                }
            }

            model.addAttribute("objUser", objUser);
            return "views/user/create";
    }

    @RequestMapping(value = {"admin/users/update/{id"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String actionUpdate(@Valid @ModelAttribute User objUser, BindingResult result,
                               ModelMap model, HttpServletRequest request, @PathVariable UUID id) {

            if (request.getMethod().equals("POST")) {

                if (!result.hasErrors()){

                    ServiceProxy oServiceProxy = new ServiceProxy(env);
                    BaseResponse oBaseResponse = oServiceProxy.postJsonData("api/users/update    ", objUser, new ArrayList<>() );
                    oServiceProxy.close();

                    User createdUser = (User) BaseResponse.convertToModel(oBaseResponse, new User());

                    if(createdUser != null)
                        return "redirect:/admin/users/view/" + createdUser.getId();
                    else
                        throw new InternalError(oBaseResponse.getMessage());
                }
            }

            User oUser = new UserService(env).findOne(id.toString());
            if(oUser == null)
                throw new ResourceNotFoundException("Não possivel encontrar o 'Utilizador' solicitado");

            model.addAttribute("objUser", objUser);
            return "views/user/create";
    }

    /**
     *
     * @param id
     * @return
     */
    @RequestMapping(value = {"admin/users/delete/{id}"}, method = {RequestMethod.GET})
    public String actionDelete(@PathVariable(required = true) UUID id) {

        User objUser = new User();
        objUser.setId(id);

        ArrayList<Params> p = new ArrayList<>();
        ServiceProxy oServiceProxy = new ServiceProxy(env);
        BaseResponse oBaseResponse = oServiceProxy.postJsonData("api/users/delete", objUser, new ArrayList<>() );
        oServiceProxy.close();

        if(oBaseResponse.getStatusAction() == 1)
            return "redirect:/admin/users";
        else
            throw new InternalError(oBaseResponse.getMessage());
    }
}
