package com.app.controllers;

import com.app.exceptions.ResourceNotFoundException;
import com.app.helpers.Params;
import com.app.helpers.ServiceProxy;
import com.library.helpers.BaseResponse;
import com.library.helpers.HelperPaging;
import com.library.models.Profile;
import com.library.repository.ProfileRepository;
import com.library.service.EventsLogService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.UUID;

@Controller
//@PreAuthorize("hasAuthority('admin')")
public class ProfileController {


    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private Environment env;

    /**
     * get profiles
     * @param model
     * @param pageable
     * @return
     */
    @RequestMapping(value = "admin/profiles", method = RequestMethod.GET)
    public String actionIndex(ModelMap model, @PageableDefault(page = 0, size = 10) Pageable pageable) {

        ServiceProxy oServiceProxy = new ServiceProxy(env);
        //get info
        BaseResponse objResponse = oServiceProxy
                .getJsonData("api/profiles", oServiceProxy.encodePageableParams(pageable));
        oServiceProxy.close();

        //Pageable result objt
        JSONObject dataResponse = (JSONObject) objResponse.getData();

        //check result
        ArrayList<Profile> profiles = (ArrayList<Profile>) dataResponse.get("content");

        model.addAttribute("objPaging", (new HelperPaging().getResponsePaging(pageable, dataResponse)));
        model.addAttribute("profiles", profiles);

        return  "/views/profile/index";
    }


    /**
     * get profile details
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = {"admin/profiles/view/{id}"}, method = {RequestMethod.GET})
    public String actionView(ModelMap model, @PathVariable UUID id) {

        ServiceProxy oServiceProxy = new ServiceProxy(env);
        BaseResponse oBaseResponse = oServiceProxy
                    .buildParams("api/profiles/details", new Params().Add(new Params("id", id.toString())).Get())
                    .getTarget()
                    .get(BaseResponse.class);
        oServiceProxy.close();

        Profile oProfile = (Profile) BaseResponse.convertToModel(oBaseResponse, new Profile());

        if(oProfile == null)
            throw new ResourceNotFoundException("Não possivel encontrar o 'Peril' solicitado");

        model.addAttribute("oProfile", oProfile);

        return  "/views/profile/view";
    }

    /**
     * create new profile
     * @param objProfile
     * @param result
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = {"admin/profiles/create"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String actionCreate(@Valid @ModelAttribute Profile objProfile,
                                BindingResult result,
                                ModelMap model, HttpServletRequest request) {

        if (request.getMethod().equals("POST")) {

            if (!result.hasErrors()) {

                ServiceProxy oServiceProxy = new ServiceProxy(env);
                BaseResponse oBaseResponse = oServiceProxy.postJsonData("api/profiles/create", objProfile, new ArrayList<>() );
                oServiceProxy.close();
                Profile createdProfile = (Profile) BaseResponse.convertToModel(oBaseResponse, new Profile());

                if(createdProfile == null)
                    return "redirect:/admin/profiles/view/" + createdProfile.getId();
                else
                    throw new InternalError(oBaseResponse.getMessage());
            }
        }

        model.addAttribute("objProfile", objProfile);
        return "views/profile/create";
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
    @RequestMapping(value = {"admin/profiles/update/{id}"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String actionUpdate(@Valid @ModelAttribute Profile objProfile,
                               BindingResult result,
                               ModelMap model, HttpServletRequest request,
                               @PathVariable(required = true) UUID id) {

            if (request.getMethod().equals("POST")) {
                if (!result.hasErrors()) {

                    ArrayList<Params> p = new ArrayList<>();
                    ServiceProxy oServiceProxy = new ServiceProxy(env);
                    BaseResponse oBaseResponse = oServiceProxy.postJsonData("api/profiles/update", objProfile, new ArrayList<>() );
                    oServiceProxy.close();

                    if(oBaseResponse.getStatusAction() == 1)
                        return "redirect:/admin/profiles/view/" + id.toString();
                    else
                        throw new InternalError(oBaseResponse.getMessage());
                }
            }

        ServiceProxy oServiceProxy = new ServiceProxy(env);
            BaseResponse oBaseResponse = oServiceProxy
                    .buildParams("api/profiles/details", new Params().Add(new Params("id", id.toString())).Get())
                    .getTarget()
                    .get(BaseResponse.class);
            oServiceProxy.close();

            objProfile = (Profile) BaseResponse.convertToModel(oBaseResponse, new Profile());
            model.addAttribute("objProfile", objProfile);
            return "views/profile/update";
    }

    /**
     *
     * @param id
     * @return
     */
    @RequestMapping(value = {"admin/profiles/delete/{id}"}, method = {RequestMethod.GET})
    public String actionDelete(@PathVariable(required = true) UUID id) {

        Profile objProfile = new Profile();
        objProfile.setId(id);

        ArrayList<Params> p = new ArrayList<>();
        ServiceProxy oServiceProxy = new ServiceProxy(env);
        BaseResponse oBaseResponse = oServiceProxy.postJsonData("api/profiles/delete", objProfile, new ArrayList<>() );
        oServiceProxy.close();

        if(oBaseResponse.getStatusAction() == 1)
            return "redirect:/admin/profiles";
        else
            throw new InternalError(oBaseResponse.getMessage());
    }
}
