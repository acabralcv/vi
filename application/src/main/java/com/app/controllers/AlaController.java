package com.app.controllers;


import com.app.exceptions.ResourceNotFoundException;
import com.app.helpers.ServiceProxy;
import com.app.service.CadeiaService;
import com.library.helpers.BaseResponse;
import com.library.helpers.HelperPaging;
import com.library.models.Ala;
import com.library.models.Cela;
import com.app.service.AlaService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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
public class AlaController {



    @Autowired
    private Environment env;

    /**
     *
     * @param model
     * @param pageable
     * @return
     */
    @RequestMapping(value = "admin/alas", method = RequestMethod.GET)
    public String actionIndex(ModelMap model, @PageableDefault(sort = { "name"}, value = 10, page = 0) Pageable pageable) {


        ServiceProxy oServiceProxy = new ServiceProxy(env);
        //get info
        BaseResponse objResponse = oServiceProxy
                .getJsonData("api/alas", oServiceProxy.encodePageableParams(pageable));
        oServiceProxy.close();

        //Pageable result objt
        JSONObject dataResponse = (JSONObject) objResponse.getData();
        if(dataResponse != null) {
        //check result
        ArrayList<Cela> Listacelas = (ArrayList<Cela>) dataResponse.get("content");

            model.addAttribute("objPaging", (new HelperPaging().getResponsePaging(pageable, dataResponse)));
            model.addAttribute("alas", Listacelas);
            }
        return  "/views/ala/index";
    }

    /**
     *
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = {"admin/alas/view/{id}"}, method = {RequestMethod.GET})
    public String actionView(ModelMap model, @PathVariable UUID id) {

        Ala oAla = new AlaService(env).findOne(id.toString());

        if(oAla == null)
            throw new ResourceNotFoundException("Não possivel encontrar informaçao do 'Ala' solicitado");

        //ArrayList<Cela> CelaList = new CelaService(env).findAllByAla(id);

        model.addAttribute("oAla", oAla);
        return  "/views/ala/view";
    }

    /**
     *
     * @param objAla
     * @param result
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = {"admin/alas/create"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String actionCreate(@Valid @ModelAttribute Ala objAla, BindingResult result,
                               ModelMap model, HttpServletRequest request) {

        if (request.getMethod().equals("POST")) {

            if (!result.hasErrors()){

                ServiceProxy oServiceProxy = new ServiceProxy(env);
                BaseResponse oBaseResponse = oServiceProxy.postJsonData("api/alas/create", objAla, new ArrayList<>() );
                oServiceProxy.close();

                Ala createdAla = (Ala) BaseResponse.convertToModel(oBaseResponse, new Ala());

                if(createdAla != null)
                    return "redirect:/admin/alas/view/" + createdAla.getId();
                else
                    throw new InternalError(oBaseResponse.getMessage());
            }
        }

        model.addAttribute("objAla", objAla);
        model.addAttribute("cadeiaList", new CadeiaService(env).findAll(30));

        return "views/ala/create";
    }


    /**
     *
     * @param objAla
     * @param result
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = {"admin/alas/update/{id}"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String actionUpdate(@ModelAttribute Cela objAla, @PathVariable UUID id, BindingResult result,
                               ModelMap model, HttpServletRequest request) {

        if (request.getMethod().equals("POST")) {

            if (!result.hasErrors()){

                ServiceProxy oServiceProxy = new ServiceProxy(env);
                BaseResponse oBaseResponse = oServiceProxy.postJsonData("api/alas/update", objAla, new ArrayList<>() );
                oServiceProxy.close();

                Ala updatedSetor = (Ala) BaseResponse.convertToModel(oBaseResponse, new Ala());

                if(updatedSetor != null)
                    return "redirect:/admin/alas/view/" + updatedSetor.getId();
                else
                    throw new InternalError(oBaseResponse.getMessage());
            }
        }
        /**objSetor = new SetorService(env).findOne(id.toString());*/

        if(objAla == null)
            throw new ResourceNotFoundException("Não possivel encontrar informaçao do 'Ala' solicitado");

        model.addAttribute("objAla", objAla);
        model.addAttribute("cadeiaList", new CadeiaService(env).findAll(30));

        return "views/Ala/update";

    }

}
