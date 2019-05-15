package com.app.controllers;

import com.app.helpers.Params;
import com.app.helpers.ServiceProxy;
import com.library.helpers.BaseResponse;
import com.library.helpers.Helper;
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
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class ProfileController {


    @Autowired
    private ProfileRepository profileRepository;

    @RequestMapping(value = "admin/profiles", method = RequestMethod.GET)
    public String actionIndex(ModelMap model, Pageable pageable) {

        //buid params
        ArrayList<Params> p = new ArrayList<>();
        p.add(new Params("page", "" + pageable.getPageNumber()));
        p.add(new Params("size", "" + pageable.getPageSize()));

        //get response from service
        (new ServiceProxy()).getTeste("api/profiles", p);
        JSONObject objResponse = (new ServiceProxy()).getJsonData("api/profiles", p);

        List<Integer> pageNumbers = null;

        if (pageable.getPageNumber() > 0) {
            pageNumbers = IntStream.rangeClosed(1, pageable.getPageNumber())
                    .boxed()
                    .collect(Collectors.toList());
        }
//
//        System.out.println(objResponse);
//
        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("profiles", (ArrayList<Profile>) objResponse.get("content"));
        Pageable firstPageWithTwoElements = PageRequest.of(0, 5);

        return  "/views/profile/index";
    }

    @RequestMapping(value = {"admin/profiles/create"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String actionCreate(@Valid @ModelAttribute Profile objProfile,
                                BindingResult result,
                                ModelMap model, HttpServletRequest request,
                                @PathVariable(required = false)
                                UUID id) {

        if (request.getMethod().equals("POST")) {

            try {

                System.out.println( "IS POTS");
                if (!result.hasErrors()) {
                    System.out.println( "NO ERROR");


                    ArrayList<Params> p = new ArrayList<>();


                    BaseResponse objResponse = (new ServiceProxy()).postJsonData("api/profiles/create", objProfile, new ArrayList<>() );

                    if(true)
                        return "redirect:/admin/profiles";

                    System.out.println( ":::::::");
                    System.out.println( "PROFILES:: " + objResponse.getStatusAction());
                    System.out.println( "IS ONE:: " + ( objResponse.getStatusAction() == 1));

                    if(objResponse.getStatusAction() == 1)
                        return "redirect:/admin/profiles";
                    else
                        new Exception(objResponse.getMessage());

                }else
                    System.out.println(result.getAllErrors());




            } catch (Exception e) {
                new EventsLogService().AddEventologs(null, "Excption in " + this.getClass().getName(), e.getMessage(), null);
            }
        }else{
            System.out.println( "NO POTS");
        }

        model.addAttribute("objProfile", objProfile);
        return "views/profile/create";
    }
}
