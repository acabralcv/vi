package com.app.controllers;

import com.app.helpers.Params;
import com.app.helpers.ServiceProxy;
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

    /**
     *
     * @param model
     * @param pageable
     * @return
     */
    @RequestMapping(value = "admin/profiles", method = RequestMethod.GET)
    public String actionIndex(ModelMap model, Pageable pageable) {

        System.out.println(pageable);

        //buid params
        ArrayList<Params> p = new ArrayList<>();
        p.add(new Params("page", "" + pageable.getPageNumber()));
        p.add(new Params("size", "" + pageable.getPageSize()));
        p.add(new Params("sort", "" + pageable.getSort()));
        p.add(new Params("number", "" + pageable.getPageNumber()));

        //get info
        JSONObject objResponse = (new ServiceProxy()).getJsonData("api/profiles", p);
        //check result
        System.out.println(objResponse);
        ArrayList<Profile> profiles = (objResponse != null) ? (ArrayList<Profile>) objResponse.get("content") : null;

        List<Integer> pageNumbers = null;

        if (pageable.getPageNumber() > 0) {
            pageNumbers = IntStream.rangeClosed(1, pageable.getPageNumber())
                    .boxed()
                    .collect(Collectors.toList());
        }

        HelperPaging objPaging = new HelperPaging(pageable, (long) pageable.getPageNumber());
        objPaging.setNumber((Long)objResponse.get("number"));
        objPaging.setTotalPages((Long) objResponse.get(("totalPages")));

        model.addAttribute("objPaging", objPaging);
        model.addAttribute("profiles", profiles);

        return  "/views/profile/index";
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
