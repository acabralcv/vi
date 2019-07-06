package com.app.controllers;

import com.app.exceptions.ResourceNotFoundException;
import com.app.helpers.Params;
import com.app.helpers.ServiceProxy;
import com.app.service.UserService;
import com.library.helpers.BaseResponse;
import com.library.models.Profile;
import com.library.models.User;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Controller
public class UserdetailsController {

    @Autowired
    private Environment env;

    @RequestMapping(value = {"userdetails"}, method = {RequestMethod.GET})
    public String actionView(ModelMap model) {

        String username = "";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken))
            username = authentication.getName();

        User oUser = new UserService(env).findByUsername(username);
        if(oUser == null)
            throw new ResourceNotFoundException("Não possivel encontrar o 'Utilizador' solicitado");
//
//
//        JSONObject dataResponse = (JSONObject) objResProfiles.getData();
//
//        //setting the variables
//        model.addAttribute("oUser", oUser);
//        model.addAttribute("oProfile", new Profile());
//        model.addAttribute("profileList",  (ArrayList<Profile>) dataResponse.get("content"));
        model.addAttribute("oUser", oUser);

        return  "/views/userdetails/index";
    }

}
