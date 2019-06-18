package com.app.controllers;


import com.app.helpers.ServiceProxy;
import com.library.helpers.BaseResponse;
import com.library.helpers.HelperPaging;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;

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
    public String actionIndex(ModelMap model, @PageableDefault(sort = { "name"}, value = 10, page = 0) Pageable pageable) {


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



    @RequestMapping(value = {"admin/setor/create"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String actionCreate(@Valid @ModelAttribute Setor objSetor, BindingResult result,
                               ModelMap model, HttpServletRequest request) {

        if (request.getMethod().equals("POST")) {

            if (!result.hasErrors()){

                ServiceProxy oServiceProxy = new ServiceProxy(env);
                BaseResponse oBaseResponse = oServiceProxy.postJsonData("api/setores/create", objSetor, new ArrayList<>() );
                oServiceProxy.close();

                Setor createdSetor = (Setor) BaseResponse.convertToModel(oBaseResponse, new Setor());

                if(createdSetor != null)
                    return "redirect:/admin/setor"; // + createdSetor.getId();
                else
                    throw new InternalError(oBaseResponse.getMessage());
            }
        }

        model.addAttribute("objSetor", objSetor);
        //model.addAttribute("setorList", new SetorService(env).findOne());
        return "views/setores/create";
    }

}
