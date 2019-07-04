package com.app.controllers;


import com.app.exceptions.ResourceNotFoundException;
import com.app.helpers.Params;
import com.app.helpers.ServiceProxy;
import com.app.service.CadeiaService;
import com.app.service.ComplexoService;
import com.library.helpers.BaseResponse;
import com.library.helpers.HelperPaging;
import com.library.models.Cadeia;
import com.library.models.Complexo;
import com.library.models.Profile;
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
public class ComplexoController {



    @Autowired
    private Environment env;

    /**
     *
     * @param model
     * @param pageable
     * @return
     */
    @RequestMapping(value = "admin/complexos", method = RequestMethod.GET)
    public String actionIndex(ModelMap model, @PageableDefault(sort = { "name"}, value = 10, page = 0) Pageable pageable) {


        ServiceProxy oServiceProxy = new ServiceProxy(env);
        //get info
        BaseResponse objResponse = oServiceProxy
                .getJsonData("api/complexos", oServiceProxy.encodePageableParams(pageable));
        oServiceProxy.close();

        //Pageable result objt
        JSONObject dataResponse = (JSONObject) objResponse.getData();
        if(dataResponse != null) {
        //check result
        ArrayList<Complexo> Listacomplexos = (ArrayList<Complexo>) dataResponse.get("content");

            model.addAttribute("objPaging", (new HelperPaging().getResponsePaging(pageable, dataResponse)));
            model.addAttribute("complexos", Listacomplexos);
            }
        return  "/views/complexo/index";
    }

    /**
     *
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = {"admin/complexos/view/{id}"}, method = {RequestMethod.GET})
    public String actionView(ModelMap model, @PathVariable UUID id) {

        Complexo oComplexo = new ComplexoService(env).findOne(id.toString());

        if(oComplexo == null)
            throw new ResourceNotFoundException("Não possivel encontrar informaçao do 'Complexo' solicitado");

        //ArrayList<Setor> sectorList = new SectorService(env).findAllByComplexo(id);

        model.addAttribute("oComplexo", oComplexo);
        return  "/views/complexo/view";
    }

    /**
     *
     * @param objComplexo
     * @param result
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = {"admin/complexos/create"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String actionCreate(@Valid @ModelAttribute Complexo objComplexo, BindingResult result,
                               ModelMap model, HttpServletRequest request) {

        if (request.getMethod().equals("POST")) {

            if (!result.hasErrors()){

                ServiceProxy oServiceProxy = new ServiceProxy(env);
                BaseResponse oBaseResponse = oServiceProxy.postJsonData("api/complexos/create", objComplexo, new ArrayList<>() );
                oServiceProxy.close();

                Complexo createdCadeia = (Complexo) BaseResponse.convertToModel(oBaseResponse, new Complexo());

                if(createdCadeia != null)
                    return "redirect:/admin/complexos/view/" + createdCadeia.getId();
                else
                    throw new InternalError(oBaseResponse.getMessage());
            }
        }

        model.addAttribute("objComplexo", objComplexo);
        model.addAttribute("cadeiaList", new CadeiaService(env).findAll(30));

        return "views/complexo/create";
    }


    /**
     *
     * @param objComplexo
     * @param result
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = {"admin/complexos/update/{id}"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String actionUpdate(@ModelAttribute Complexo objComplexo, @PathVariable UUID id, BindingResult result,
                               ModelMap model, HttpServletRequest request) {

        if (request.getMethod().equals("POST")) {

            if (!result.hasErrors()){

                ServiceProxy oServiceProxy = new ServiceProxy(env);
                BaseResponse oBaseResponse = oServiceProxy.postJsonData("api/complexos/update", objComplexo, new ArrayList<>() );
                oServiceProxy.close();

                Complexo updatedCadeia = (Complexo) BaseResponse.convertToModel(oBaseResponse, new Complexo());

                if(updatedCadeia != null)
                    return "redirect:/admin/complexos/view/" + updatedCadeia.getId();
                else
                    throw new InternalError(oBaseResponse.getMessage());
            }
        }

        objComplexo = new ComplexoService(env).findOne(id.toString());

        if(objComplexo == null)
            throw new ResourceNotFoundException("Não possivel encontrar informaçao do 'Complexo' solicitado");

        model.addAttribute("objComplexo", objComplexo);
        model.addAttribute("cadeiaList", new CadeiaService(env).findAll(30));

        return "views/complexo/update";


    }

}
