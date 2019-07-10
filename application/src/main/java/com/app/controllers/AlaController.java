package com.app.controllers;


import com.app.exceptions.ResourceNotFoundException;
import com.app.helpers.Params;
import com.app.helpers.ServiceProxy;
import com.app.service.AlaService;
import com.app.service.CadeiaService;
import com.app.service.ComplexoService;
import com.app.service.SetorService;
import com.library.helpers.BaseResponse;
import com.library.helpers.HelperPaging;
import com.library.models.Setor;
import com.library.models.Ala;
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
    public String actionIndex(ModelMap model, @PageableDefault(sort = { "nome"}, value = 10, page = 0) Pageable pageable) {


        ServiceProxy oServiceProxy = new ServiceProxy(env);
        //get info
        BaseResponse objResponse = oServiceProxy
                .getJsonData("api/alas", oServiceProxy.encodePageableParams(pageable));
        oServiceProxy.close();

        //Pageable result objt
        JSONObject dataResponse = (JSONObject) objResponse.getData();
        if(dataResponse != null) {
        //check result
        ArrayList<Ala> Listaalas = (ArrayList<Ala>) dataResponse.get("content");

            model.addAttribute("objPaging", (new HelperPaging().getResponsePaging(pageable, dataResponse)));
            model.addAttribute("alas", Listaalas);
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

        //ArrayList<Setor> sectorList = new SectorService(env).findAllByComplexo(id);

        model.addAttribute("oAla", oAla);
        //model.addAttribute("setoresList", new SetorService(env).findAll(30));
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

                Ala createdSetor = (Ala) BaseResponse.convertToModel(oBaseResponse, new Ala());

                if(createdSetor != null)
                    return "redirect:/admin/alas/view/" + createdSetor.getId();
                else
                    throw new InternalError(oBaseResponse.getMessage());
            }
        }

        model.addAttribute("objAla", objAla);
        model.addAttribute("setoresList", new SetorService(env).findAll(30));

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
    public String actionUpdate(@ModelAttribute Ala objAla, @PathVariable UUID id, BindingResult result,
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

        objAla = new AlaService(env).findOne(id.toString());

        if(objAla == null)
            throw new ResourceNotFoundException("Não possivel encontrar informaçao do 'Ala' solicitado");

        model.addAttribute("objAla", objAla);
        model.addAttribute("setorList", new CadeiaService(env).findAll(30));

        return "views/ala/update";
    }

    /**
     *
     * @param id
     * @return
     */
    @RequestMapping(value = {"admin/alas/delete/{id}"}, method = {RequestMethod.GET})
    public String actionDelete(@PathVariable(required = true) UUID id) {

        Ala objAla = new Ala();
        objAla.setId(id);

        ArrayList<Params> p = new ArrayList<>();
        ServiceProxy oServiceProxy = new ServiceProxy(env);
        BaseResponse oBaseResponse = oServiceProxy.postJsonData("api/alas/delete", objAla, new ArrayList<>() );
        oServiceProxy.close();

        if(oBaseResponse.getStatusAction() == 1)
            return "redirect:/admin/alas";
        else
            throw new InternalError(oBaseResponse.getMessage());
    }
}
