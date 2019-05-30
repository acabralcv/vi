package com.app.controllers;

import com.app.helpers.ServiceProxy;
import com.library.helpers.BaseResponse;
import com.library.helpers.HelperPaging;
import com.library.models.Profile;
import com.library.models.Recluso;
import com.library.models.User;
import com.library.repository.ReclusoRepository;
import com.library.service.EventsLogService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ReclusoController {

    @Autowired
    private ReclusoRepository reclusoRepository;

    @RequestMapping(value = "reclusos", method = RequestMethod.GET)
    public String actionIndex(ModelMap model, @PageableDefault(page = 0, size = 10) Pageable pageable) {

        //get info
        BaseResponse objResponse = (new ServiceProxy())
                .getJsonData("api/reclusos", (new ServiceProxy()).encodePageableParams(pageable));

        //Pageable result objt
        JSONObject dataResponse = (JSONObject) objResponse.getData();

        //check result
        ArrayList<Recluso> reclusos = (ArrayList<Recluso>) dataResponse.get("content");

        model.addAttribute("objPaging", (new HelperPaging().getResponsePaging(pageable, dataResponse)));
        model.addAttribute("reclusos", reclusos);

        return  "/views/recluso/index";
    }

    @RequestMapping(value = {"reclusos/create"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String actionCreate(@Valid @ModelAttribute Recluso objRecluso, BindingResult result,
                               ModelMap model, HttpServletRequest request) {

        if (request.getMethod().equals("POST")) {

            try {

                if (result.hasErrors())
                    new Exception(result.getAllErrors().get(0).toString());

                BaseResponse oBaseResponse = (new ServiceProxy()).postJsonData("api/reclusos/create", objRecluso, new ArrayList<>() );
                Recluso createdUser = (Recluso) BaseResponse.convertToModel(oBaseResponse, new Recluso());

                if(oBaseResponse.getStatusAction() == 1)
                    return "redirect:/reclusos/view/" + createdUser.getId();
                else
                    new Exception(oBaseResponse.getMessage());

            } catch (Exception e) {
                new EventsLogService().AddEventologs(null, "Excption in " + this.getClass().getName(), e.getMessage(), null);
            }
        }

        model.addAttribute("objRecluso", objRecluso);
        return "views/recluso/create";
    }

}
