package com.app.controllers;


import com.app.exceptions.ResourceNotFoundException;
import com.app.helpers.ServiceProxy;
import com.app.service.CadeiaService;
import com.app.service.IlhaService;
import com.library.helpers.BaseResponse;
import com.library.helpers.HelperPaging;
import com.library.models.Profile;
import com.library.models.Cadeia;
import org.json.simple.JSONObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import java.util.UUID;

@Controller
public class CadeiaController {

    /**
     *
     * @param model
     * @param pageable
     * @return
     */
    @RequestMapping(value = "cadeia", method = RequestMethod.GET)
    public String actionIndex(ModelMap model, @PageableDefault(sort = { "name"}, value = 10, page = 0) Pageable pageable) {

        //get info
        BaseResponse objResponse = (new ServiceProxy())
                .getJsonData("api/cadeia", (new ServiceProxy()).encodePageableParams(pageable));

        //Pageable result objt
        JSONObject dataResponse = (JSONObject) objResponse.getData();

        //check result
        ArrayList<Cadeia> cadeia;
        if (dataResponse != null) {
            cadeia = (ArrayList<Cadeia>) dataResponse.get("content");

            model.addAttribute("objPaging", (new HelperPaging().getResponsePaging(pageable, dataResponse)));
            model.addAttribute("cadeia", cadeia);
        }
        return  "/views/cadeia/index";
    }



    /**
     *
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = {"cadeia/view/{id}"}, method = {RequestMethod.GET})
    public String actionView(ModelMap model, @PathVariable UUID id) {

        Cadeia oCadeia = CadeiaService.findOne(id.toString());
        if(oCadeia == null)
            throw new ResourceNotFoundException("Não possivel encontrar o 'Utilizador' solicitado");

        //get profiles
        BaseResponse objResProfiles = (new ServiceProxy())
                .getJsonData("api/profiles", (new ServiceProxy()).encodePageableParams(PageRequest.of(0,50)));
        JSONObject dataResponse = (JSONObject) objResProfiles.getData();

        //setting the variables
       // model.addAttribute("oCadeia", oCadeia);
        //model.addAttribute("oProfile", new Profile());
        //model.addAttribute("profileList",  (ArrayList<Profile>) dataResponse.get("content"));
        //model.addAttribute("objPaging", null);

        return  "/views/cadeia/view";
    }

    /**
     *
     * @param objCadeia
     * @param result
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = {"cadeia/create"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String actionCreate(@Valid @ModelAttribute Cadeia objCadeia, BindingResult result,
                               ModelMap model, HttpServletRequest request) {

        if (request.getMethod().equals("POST")) {

            if (!result.hasErrors()){

                BaseResponse oBaseResponse = (new ServiceProxy()).postJsonData("api/cadeias/create", objCadeia, new ArrayList<>() );
                Cadeia createdCadeia = (Cadeia) BaseResponse.convertToModel(oBaseResponse, new Cadeia());

                if(createdCadeia != null)
                    return "redirect:/cadeia/view/" + createdCadeia.getId();
                else
                    throw new InternalError(oBaseResponse.getMessage());
            }
        }


        model.addAttribute("objCadeia", objCadeia);
        model.addAttribute("ilhaList", new IlhaService().findAll());
        return "views/cadeia/create";
    }

    @RequestMapping(value = {"cadeia/update/{id"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String actionUpdate(@Valid @ModelAttribute Cadeia objCadeia, BindingResult result,
                               ModelMap model, HttpServletRequest request, @PathVariable UUID id) {

            if (request.getMethod().equals("POST")) {

                if (!result.hasErrors()){

                    BaseResponse oBaseResponse = (new ServiceProxy()).postJsonData("api/cadeia/update    ", objCadeia, new ArrayList<>() );
                    Cadeia createdCadeia = (Cadeia) BaseResponse.convertToModel(oBaseResponse, new Cadeia());

                    if(createdCadeia != null)
                        return "redirect:/cadeia/view/" + createdCadeia.getId();
                    else
                        throw new InternalError(oBaseResponse.getMessage());
                }
            }

            //Cadeia oCadeia = cadeiaervice.findOne(id.toString());
            //if(oCadeia == null)
              //  throw new ResourceNotFoundException("Não possivel encontrar o 'Utilizador' solicitado");

            //model.addAttribute("objCadeia", objCadeia);
            return "views/cadeia/create";
    }

}
