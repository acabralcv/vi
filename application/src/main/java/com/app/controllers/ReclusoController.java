package com.app.controllers;

import com.app.helpers.Params;
import com.app.helpers.ServiceProxy;
import com.app.service.IlhaService;
import com.app.service.PaisService;
import com.app.service.ReclusoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.helpers.BaseResponse;
import com.library.helpers.HelperPaging;
import com.library.models.*;
import com.library.repository.PaisRepository;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.UUID;

@Controller
public class ReclusoController {

    @Autowired
    private ReclusoRepository reclusoRepository;

    @Autowired
    private PaisRepository paisRepository;

    final ObjectMapper objectMapper = new ObjectMapper();


    @RequestMapping(value = {"reclusos/view/{id}"}, method = {RequestMethod.GET})
    public String actionView(ModelMap model, @PathVariable UUID id) {

        try{
            Recluso oRecluso = ReclusoService.findOne(id.toString());

            if(oRecluso == null)
                throw new ResourceNotFoundException("Não possivel encontrar o recluso solicitado");

            model.addAttribute("oRecluso", oRecluso);

            return  "/views/recluso/view";

        } catch (Exception e) {
            new EventsLogService().AddEventologs(null, "Excption in " + this.getClass().getName(), e.getMessage(), null);
            throw new NotFoundException(e.getMessage());
        }
    }

    @RequestMapping(value = "reclusos", method = RequestMethod.GET)
    public String actionIndex(ModelMap model, @PageableDefault(page = 0, size = 10) Pageable pageable) {

        try{
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

        } catch (Exception e) {
            new EventsLogService().AddEventologs(null, "Excption in " + this.getClass().getName(), e.getMessage(), null);
            throw new NotFoundException(e.getMessage());
        }
    }

    @RequestMapping(value = {"reclusos/create"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String actionCreate(@Valid @ModelAttribute Recluso objRecluso, BindingResult result,
                               ModelMap model, HttpServletRequest request) {

        try {

            if (request.getMethod().equals("POST")) {

                if (result.hasErrors())
                    throw new Exception(result.getAllErrors().get(0).toString());

                BaseResponse oBaseResponse = (new ServiceProxy()).postJsonData("api/reclusos/create", objRecluso, new ArrayList<>() );
                Recluso createdUser = (Recluso) BaseResponse.convertToModel(oBaseResponse, new Recluso());

                if(oBaseResponse.getStatusAction() == 1)
                    return "redirect:/reclusos/view/" + createdUser.getId();
                else
                    throw new Exception(oBaseResponse.getMessage());
            }

            model.addAttribute("paisList", new PaisService().findAll());
            model.addAttribute("ilhaList", new IlhaService().findAll());
            model.addAttribute("objRecluso", objRecluso);
            return "views/recluso/create";

        } catch (Exception e) {
            new EventsLogService().AddEventologs(null, "Excption in " + this.getClass().getName(), e.getMessage(), null);
            throw new NotFoundException(e.getMessage());
        }
    }


    @RequestMapping(value = "reclusos/update/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public String actionUpdate(@Valid @ModelAttribute Recluso objRecluso, BindingResult result,
                               ModelMap model, HttpServletRequest request, @PathVariable UUID id) {

        try {

            if (request.getMethod().equals("POST")) {

                if (!result.hasErrors()) {

                    ArrayList<Params> p = new ArrayList<>();
                    BaseResponse oBaseResponse = (new ServiceProxy()).postJsonData("api/reclusos/update", objRecluso, new ArrayList<>() );

                    if(oBaseResponse.getStatusAction() != 1 || oBaseResponse.getData() == null || oBaseResponse.getData() == "null")
                        throw new Exception(oBaseResponse.getMessage());

                    Recluso updatedRecluso = objectMapper.convertValue(oBaseResponse.getData(), Recluso.class);

                    return "redirect:/reclusos/view/" + updatedRecluso.getId().toString();
                }
            }

            objRecluso = ReclusoService.findOne(id.toString());

            if(objRecluso == null)
                throw new ResourceNotFoundException("Não possivel encontrar o recluso solicitado");

            model.addAttribute("objRecluso", objRecluso);
            return  "/views/recluso/update";

        } catch (Exception e) {
            new EventsLogService().AddEventologs(null, "Excption in " + this.getClass().getName(), e.getMessage(), null);
            throw new NotFoundException(e.getMessage());
        }
    }

}
