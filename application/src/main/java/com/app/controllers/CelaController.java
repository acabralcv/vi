package com.app.controllers;


import com.app.exceptions.ResourceNotFoundException;
import com.app.helpers.Params;
import com.app.helpers.ServiceProxy;
import com.app.service.AlaService;
import com.app.service.CelaService;
import com.app.service.CadeiaService;
import com.library.helpers.BaseResponse;
import com.library.helpers.HelperPaging;
import com.library.models.Ala;
import com.library.models.Cela;
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
public class CelaController {



    @Autowired
    private Environment env;

    /**
     *
     * @param model
     * @param pageable
     * @return
     */
    @RequestMapping(value = "admin/celas", method = RequestMethod.GET)
    public String actionIndex(ModelMap model, @PageableDefault(sort = { "nome"}, value = 10, page = 0) Pageable pageable) {


        ServiceProxy oServiceProxy = new ServiceProxy(env);
        //get info
        BaseResponse objResponse = oServiceProxy
                .getJsonData("api/celas", oServiceProxy.encodePageableParams(pageable));
        oServiceProxy.close();

        //Pageable result objt
        JSONObject dataResponse = (JSONObject) objResponse.getData();
        if(dataResponse != null) {
        //check result
        ArrayList<Cela> Listacelas = (ArrayList<Cela>) dataResponse.get("content");

            model.addAttribute("objPaging", (new HelperPaging().getResponsePaging(pageable, dataResponse)));
            model.addAttribute("celas", Listacelas);
            }
        return  "/views/cela/index";
    }

    /**
     *
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = {"admin/celas/view/{id}"}, method = {RequestMethod.GET})
    public String actionView(ModelMap model, @PathVariable UUID id) {

        Cela oCela = new CelaService(env).findOne(id.toString());

        if(oCela == null)
            throw new ResourceNotFoundException("Não possivel encontrar informaçao do 'Cela' solicitado");

        //ArrayList<Setor> sectorList = new SectorService(env).findAllByComplexo(id);

        model.addAttribute("oCela", oCela);
        return  "/views/cela/view";
    }

    /**
     *
     * @param objCela
     * @param result
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = {"admin/celas/create"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String actionCreate(@Valid @ModelAttribute Cela objCela, BindingResult result,
                               ModelMap model, HttpServletRequest request) {

        if (request.getMethod().equals("POST")) {

            if (!result.hasErrors()){

                ServiceProxy oServiceProxy = new ServiceProxy(env);
                BaseResponse oBaseResponse = oServiceProxy.postJsonData("api/celas/create", objCela, new ArrayList<>() );
                oServiceProxy.close();

                Cela createdAla = (Cela) BaseResponse.convertToModel(oBaseResponse, new Cela());

                if(createdAla != null)
                    return "redirect:/admin/celas/view/" + createdAla.getId();
                else
                    throw new InternalError(oBaseResponse.getMessage());
            }
        }

        model.addAttribute("objCela", objCela);
        model.addAttribute("alaList", new AlaService(env).findAll(30));

        return "views/cela/create";
    }


    /**
     *
     * @param objCela
     * @param result
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = {"admin/celas/update/{id}"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String actionUpdate(@ModelAttribute Cela objCela, @PathVariable UUID id, BindingResult result,
                               ModelMap model, HttpServletRequest request) {

        if (request.getMethod().equals("POST")) {

            if (!result.hasErrors()){

                ServiceProxy oServiceProxy = new ServiceProxy(env);
                BaseResponse oBaseResponse = oServiceProxy.postJsonData("api/celas/update", objCela, new ArrayList<>() );
                oServiceProxy.close();

                Cela updatedAla = (Cela) BaseResponse.convertToModel(oBaseResponse, new Cela());

                if(updatedAla != null)
                    return "redirect:/admin/celas/view/" + updatedAla.getId();
                else
                    throw new InternalError(oBaseResponse.getMessage());
            }
        }

        objCela = new CelaService(env).findOne(id.toString());

        if(objCela == null)
            throw new ResourceNotFoundException("Não possivel encontrar informaçao do 'Cela' solicitado");

        model.addAttribute("objCela", objCela);
        model.addAttribute("alaList", new CadeiaService(env).findAll(30));

        return "views/cela/update";
    }

    /**
     *
     * @param id
     * @return
     */
    @RequestMapping(value = {"admin/celas/delete/{id}"}, method = {RequestMethod.GET})
    public String actionDelete(@PathVariable(required = true) UUID id) {

        Cela objCela = new Cela();
        objCela.setId(id);

        ArrayList<Params> p = new ArrayList<>();
        ServiceProxy oServiceProxy = new ServiceProxy(env);
        BaseResponse oBaseResponse = oServiceProxy.postJsonData("api/celas/delete", objCela, new ArrayList<>() );
        oServiceProxy.close();

        if(oBaseResponse.getStatusAction() == 1)
            return "redirect:/admin/celas";
        else
            throw new InternalError(oBaseResponse.getMessage());
    }
}
