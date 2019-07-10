package com.app.controllers;


import com.app.exceptions.ResourceNotFoundException;
import com.app.helpers.Params;
import com.app.helpers.ServiceProxy;
import com.app.service.CadeiaService;
import com.app.service.SetorService;
import com.app.service.ComplexoService;
import com.library.helpers.BaseResponse;
import com.library.helpers.HelperPaging;
import com.library.models.Complexo;
import com.library.models.Setor;
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
public class SetorController {



    @Autowired
    private Environment env;

    /**
     *
     * @param model
     * @param pageable
     * @return
     */
    @RequestMapping(value = "admin/setores", method = RequestMethod.GET)
    public String actionIndex(ModelMap model, @PageableDefault(sort = { "nome"}, value = 10, page = 0) Pageable pageable) {


        ServiceProxy oServiceProxy = new ServiceProxy(env);
        //get info
        BaseResponse objResponse = oServiceProxy
                .getJsonData("api/setores", oServiceProxy.encodePageableParams(pageable));
        oServiceProxy.close();

        //Pageable result objt
        JSONObject dataResponse = (JSONObject) objResponse.getData();
        if(dataResponse != null) {
        //check result
        ArrayList<Setor> Listasetores = (ArrayList<Setor>) dataResponse.get("content");

            model.addAttribute("objPaging", (new HelperPaging().getResponsePaging(pageable, dataResponse)));
            model.addAttribute("setores", Listasetores);
            }
        return  "/views/setor/index";
    }

    /**
     *
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = {"admin/setores/view/{id}"}, method = {RequestMethod.GET})
    public String actionView(ModelMap model, @PathVariable UUID id) {

        Setor oSetor = new SetorService(env).findOne(id.toString());

        if(oSetor == null)
            throw new ResourceNotFoundException("Não possivel encontrar informaçao do 'Setor' solicitado");

        //ArrayList<Setor> sectorList = new SectorService(env).findAllByComplexo(id);

        model.addAttribute("oSetor", oSetor);
        return  "/views/setor/view";
    }

    /**
     *
     * @param objSetor
     * @param result
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = {"admin/setores/create"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String actionCreate(@Valid @ModelAttribute Setor objSetor, BindingResult result,
                               ModelMap model, HttpServletRequest request) {

        if (request.getMethod().equals("POST")) {

            if (!result.hasErrors()){

                ServiceProxy oServiceProxy = new ServiceProxy(env);
                BaseResponse oBaseResponse = oServiceProxy.postJsonData("api/setores/create", objSetor, new ArrayList<>() );
                oServiceProxy.close();

                Setor createdComplexo = (Setor) BaseResponse.convertToModel(oBaseResponse, new Setor());

                if(createdComplexo != null)
                    return "redirect:/admin/setores/view/" + createdComplexo.getId();
                else
                    throw new InternalError(oBaseResponse.getMessage());
            }
        }

        model.addAttribute("objSetor", objSetor);
        model.addAttribute("complexoList", new CadeiaService(env).findAll(30));

        return "views/setor/create";
    }


    /**
     *
     * @param objSetor
     * @param result
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = {"admin/setores/update/{id}"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String actionUpdate(@ModelAttribute Setor objSetor, @PathVariable UUID id, BindingResult result,
                               ModelMap model, HttpServletRequest request) {

        if (request.getMethod().equals("POST")) {

            if (!result.hasErrors()){

                ServiceProxy oServiceProxy = new ServiceProxy(env);
                BaseResponse oBaseResponse = oServiceProxy.postJsonData("api/setores/update", objSetor, new ArrayList<>() );
                oServiceProxy.close();

                Setor updatedComplexo = (Setor) BaseResponse.convertToModel(oBaseResponse, new Setor());

                if(updatedComplexo != null)
                    return "redirect:/admin/setores/view/" + updatedComplexo.getId();
                else
                    throw new InternalError(oBaseResponse.getMessage());
            }
        }

        objSetor = new SetorService(env).findOne(id.toString());

        if(objSetor == null)
            throw new ResourceNotFoundException("Não possivel encontrar informaçao do 'Setor' solicitado");

        model.addAttribute("objSetor", objSetor);
        model.addAttribute("complexoList", new CadeiaService(env).findAll(30));

        return "views/setor/update";

    }

    /**
     *
     * @param id
     * @return
     */
    @RequestMapping(value = {"admin/setores/delete/{id}"}, method = {RequestMethod.GET})
    public String actionDelete(@PathVariable(required = true) UUID id) {

        Setor objSetor = new Setor();
        objSetor.setId(id);

        ArrayList<Params> p = new ArrayList<>();
        ServiceProxy oServiceProxy = new ServiceProxy(env);
        BaseResponse oBaseResponse = oServiceProxy.postJsonData("api/setores/delete", objSetor, new ArrayList<>() );
        oServiceProxy.close();

        if(oBaseResponse.getStatusAction() == 1)
            return "redirect:/admin/setores";
        else
            throw new InternalError(oBaseResponse.getMessage());
    }
}