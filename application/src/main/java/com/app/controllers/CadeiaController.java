package com.app.controllers;


import com.app.service.ComplexoService;
import com.app.helpers.Params;
import com.app.service.ReclusoService;
import com.library.models.Cadeia;
import com.library.models.Complexo;
import com.library.models.Recluso;
import com.library.service.EventsLogService;
import org.json.simple.JSONObject;
import com.app.service.IlhaService;
import com.app.helpers.ServiceProxy;
import com.app.service.CadeiaService;
import com.library.helpers.BaseResponse;
import com.library.helpers.HelperPaging;
import com.app.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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



    @Autowired
    private Environment env;

    /**
     *
     * @param model
     * @param pageable
     * @return
     */
    @RequestMapping(value = "admin/cadeias", method = RequestMethod.GET)
    public String actionIndex(ModelMap model, @PageableDefault(sort = { "nome"}, value = 10, page = 0) Pageable pageable) {


        ServiceProxy oServiceProxy = new ServiceProxy(env);
        //get info
        BaseResponse objResponse = oServiceProxy
                .getJsonData("api/cadeias", oServiceProxy.encodePageableParams(pageable));
        oServiceProxy.close();

        //Pageable result objt
        JSONObject dataResponse = (JSONObject) objResponse.getData();

        //check result
        ArrayList<Cadeia> cadeias = (ArrayList<Cadeia>) dataResponse.get("content");

        model.addAttribute("objPaging", (new HelperPaging().getResponsePaging(pageable, dataResponse)));
        model.addAttribute("cadeias", cadeias);

        return  "/views/cadeia/index";
    }


    /**
     *
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = {"admin/cadeias/view/{id}"}, method = {RequestMethod.GET})
    public String actionView(ModelMap model, @PathVariable UUID id) {

        Cadeia oCadeia = new CadeiaService(env).findOne(id.toString());

        if(oCadeia == null)
            throw new ResourceNotFoundException("Não possivel encontrar informaçao da 'Cadeia' solicitado");

        ArrayList<Complexo> complexosList = new ComplexoService(env).findAllByCadeia(id);

        model.addAttribute("oCadeia", oCadeia);
        model.addAttribute("complexosList", complexosList);

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
    @RequestMapping(value = {"admin/cadeias/create"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String actionCreate(@Valid @ModelAttribute Cadeia objCadeia, BindingResult result,
                               ModelMap model, HttpServletRequest request) {

        if (request.getMethod().equals("POST")) {

            if (!result.hasErrors()){

                ServiceProxy oServiceProxy = new ServiceProxy(env);
                BaseResponse oBaseResponse = oServiceProxy.postJsonData("api/cadeias/create", objCadeia, new ArrayList<>() );
                oServiceProxy.close();

                Cadeia createdCadeia = (Cadeia) BaseResponse.convertToModel(oBaseResponse, new Cadeia());

                if(createdCadeia != null)
                    return "redirect:/admin/cadeias"; // + createdCadeia.getId();
                else
                    throw new InternalError(oBaseResponse.getMessage());
            }
        }

        model.addAttribute("objCadeia", objCadeia);
        model.addAttribute("ilhaList", new IlhaService(env).findAll());
        return "views/cadeia/create";
    }

    @RequestMapping(value = {"admin/cadeias/update/{id}"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String actionUpdate(@PathVariable UUID id, @Valid @ModelAttribute Cadeia objCadeia, BindingResult result,
                               ModelMap model, HttpServletRequest request) {

        if (request.getMethod().equals("POST")) {

            if (!result.hasErrors()){

                ServiceProxy oServiceProxy = new ServiceProxy(env);
                BaseResponse oBaseResponse = oServiceProxy.postJsonData("api/cadeias/update", objCadeia, new ArrayList<>() );
                oServiceProxy.close();

                Cadeia updatedCadeia = (Cadeia) BaseResponse.convertToModel(oBaseResponse, new Cadeia());

                if(updatedCadeia != null)
                    return "redirect:/admin/cadeias/view/" + updatedCadeia.getId();
                else
                    throw new InternalError(oBaseResponse.getMessage());
            }
        }

        objCadeia = new CadeiaService(env).findOne(id.toString());

        if(objCadeia == null)
            throw new ResourceNotFoundException("Não possivel encontrar informaçao da 'Cadeia' solicitado");

        model.addAttribute("objCadeia", objCadeia);
        model.addAttribute("ilhaList", new IlhaService(env).findAll());
        return "views/cadeia/update";
    }

    /**
     *
     * @param id
     * @return
     */
    @RequestMapping(value = {"admin/cadeias/delete/{id}"}, method = {RequestMethod.GET})
    public String actionDelete(@PathVariable(required = true) UUID id) {

        Cadeia objCadeia = new Cadeia();
        objCadeia.setId(id);

        ArrayList<Params> p = new ArrayList<>();
        ServiceProxy oServiceProxy = new ServiceProxy(env);
        BaseResponse oBaseResponse = oServiceProxy.postJsonData("api/cadeias/delete", objCadeia, new ArrayList<>() );
        oServiceProxy.close();

        if(oBaseResponse.getStatusAction() == 1)
            return "redirect:/admin/cadeias";
        else
            throw new InternalError(oBaseResponse.getMessage());
    }
}