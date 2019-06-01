package com.app.controllers;

import com.app.helpers.Params;
import com.app.helpers.ServiceProxy;
import com.library.helpers.BaseResponse;
import com.library.helpers.HelperPaging;
import com.library.models.Profile;
import com.library.repository.ProfileRepository;
import com.library.service.EventsLogService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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
import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.UUID;

@Controller
public class ProfileController {


    @Autowired
    private ProfileRepository profileRepository;

    /**
     * get profiles
     * @param model
     * @param pageable
     * @return
     */
    @RequestMapping(value = "admin/profiles", method = RequestMethod.GET)
    public String actionIndex(ModelMap model, @PageableDefault(page = 0, size = 10) Pageable pageable) {

        try {

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

        } catch (Exception e) {
            new EventsLogService().AddEventologs(null, "Excption in " + this.getClass().getName(), e.getMessage(), null);
            throw new NotFoundException("Pagina não encontrada.");
        }
    }


    /**
     * get profile details
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = {"admin/profiles/view/{id}"}, method = {RequestMethod.GET})
    public String actionView(ModelMap model, @PathVariable UUID id) {

        try{

            ServiceProxy oServiceProxy = new ServiceProxy();
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

        } catch (Exception e) {
            new EventsLogService().AddEventologs(null, "Excption in " + this.getClass().getName(), e.getMessage(), null);
            throw new NotFoundException("Pagina não encontrada.");
        }
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

            try {

                if (!result.hasErrors()) {

                    ArrayList<Params> p = new ArrayList<>();
                    BaseResponse objResponse = (new ServiceProxy()).postJsonData("api/profiles/create", objProfile, new ArrayList<>() );

                    if(objResponse.getStatusAction() == 1)
                        return "redirect:/admin/profiles";
                    else
                        throw new Exception(objResponse.getMessage());
                }else
                    System.out.println(result.getAllErrors());

            } catch (Exception e) {
                new EventsLogService().AddEventologs(null, "Excption in " + this.getClass().getName(), e.getMessage(), null);
                new EventsLogService().AddEventologs(null, "Excption in " + this.getClass().getName(), e.getMessage(), null);
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

        try{

            if (request.getMethod().equals("POST")) {
                if (!result.hasErrors()) {

                    ArrayList<Params> p = new ArrayList<>();
                    BaseResponse objResponse = (new ServiceProxy()).postJsonData("api/profiles/update", objProfile, new ArrayList<>() );

                    if(objResponse.getStatusAction() == 1)
                        return "redirect:/admin/profiles/view/" + id.toString();
                    else
                        throw new Exception(objResponse.getMessage());
                }
            }

            ServiceProxy oServiceProxy = new ServiceProxy();
            BaseResponse oBaseResponse = oServiceProxy
                    .buildParams("api/profiles/details", new Params().Add(new Params("id", id.toString())).Get())
                    .getTarget()
                    .get(BaseResponse.class);
            oServiceProxy.close();

            objProfile = (Profile) BaseResponse.convertToModel(oBaseResponse, new Profile());
            model.addAttribute("objProfile", objProfile);
            return "views/profile/update";

        } catch (Exception e) {
            new EventsLogService().AddEventologs(null, "Excption in " + this.getClass().getName(), e.getMessage(), null);
            throw new NotFoundException("Pagina não encontrada");
        }
    }

    /**
     *
     * @param id
     * @return
     */
    @RequestMapping(value = {"admin/profiles/delete/{id}"}, method = {RequestMethod.GET})
    public String actionDelete(@PathVariable(required = true) UUID id) {
        try{

            Profile objProfile = new Profile();
            objProfile.setId(id);

            ArrayList<Params> p = new ArrayList<>();
            BaseResponse objResponse = (new ServiceProxy()).postJsonData("api/profiles/delete", objProfile, new ArrayList<>() );

            if(objResponse.getStatusAction() == 1)
                return "redirect:/admin/profiles";
            else
                throw new Exception(objResponse.getMessage());

        } catch (Exception e) {
            new EventsLogService().AddEventologs(null, "Excption in " + this.getClass().getName(), e.getMessage(), null);
            throw new NotFoundException("Pagina não encontrada.");
        }
    }
}
