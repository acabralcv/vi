package com.app.controllers;

import com.app.exceptions.ResourceNotFoundException;
import com.app.helpers.Params;
import com.app.helpers.ServiceProxy;
import com.app.service.IlhaService;
import com.app.service.PaisService;
import com.app.service.ReclusoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.helpers.BaseResponse;
import com.library.helpers.HelperPaging;
import com.library.models.*;
import com.library.repository.ImageRepository;
import com.library.repository.PaisRepository;
import com.library.repository.ReclusoRepository;
import com.library.service.EventsLogService;
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
import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Controller
public class ReclusoController {

    @Autowired
    private ReclusoRepository reclusoRepository;

    @Autowired
    private PaisRepository paisRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private Environment env;

    final ObjectMapper objectMapper = new ObjectMapper();


    @RequestMapping(value = {"reclusos/view/{id}"}, method = {RequestMethod.GET})
    public String actionView(ModelMap model, @PathVariable UUID id) {

        Recluso oRecluso = new ReclusoService(env).findOne(id.toString());

        if(oRecluso == null)
            throw new ResourceNotFoundException("Não possivel encontrar o recluso solicitado");

        new EventsLogService().addReclusoLog(oRecluso);

        model.addAttribute("oRecluso", oRecluso);

        return  "/views/recluso/view";
    }

    @RequestMapping(value = "reclusos", method = RequestMethod.GET)
    public String actionIndex(ModelMap model, @PageableDefault(page = 0, size = 10) Pageable pageable) {

        //get info
        ServiceProxy oServiceProxy = new ServiceProxy(env);
        BaseResponse objResponse = oServiceProxy
                .getJsonData("api/reclusos", oServiceProxy.encodePageableParams(pageable));
        oServiceProxy.close();

        //Pageable result objt
        JSONObject dataResponse = (JSONObject) objResponse.getData();

        //check result
        ArrayList<Recluso> reclusos = dataResponse != null ? (ArrayList<Recluso>) dataResponse.get("content") : null;

        model.addAttribute("objPaging", (new HelperPaging().getResponsePaging(pageable, dataResponse)));
        model.addAttribute("reclusos", reclusos);

        return  "/views/recluso/index";
    }

    @RequestMapping(value = {"reclusos/create"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String actionCreate(@Valid @ModelAttribute Recluso objRecluso, BindingResult result,
                               ModelMap model, HttpServletRequest request) {

            ServiceProxy oServiceProxy = new ServiceProxy(env);

            if (request.getMethod().equals("POST")) {

                //eviter problema
                objRecluso.setDataNascimento(null);

                if ( !result.hasErrors()) {
                    BaseResponse oBaseResponse = oServiceProxy.postJsonData("api/reclusos/create", objRecluso, new ArrayList<>());
                    oServiceProxy.close();

                    Recluso createdUser = (Recluso) BaseResponse.convertToModel(oBaseResponse, new Recluso());

                    if (createdUser == null)
                        throw new InternalError(oBaseResponse.getMessage());

                    return "redirect:/reclusos/view/" + createdUser.getId();
                }
            }

            model.addAttribute("paisList", new PaisService(env).findAll());
            model.addAttribute("ilhaList", new IlhaService(env).findAll());
            model.addAttribute("oServiceProxy", oServiceProxy);
            model.addAttribute("objRecluso", objRecluso);
            return "views/recluso/create";
    }


    @RequestMapping(value = "reclusos/update/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public String actionUpdate(@Valid @ModelAttribute Recluso objRecluso, BindingResult result,
                               ModelMap model, HttpServletRequest request, @PathVariable UUID id) {

        if (request.getMethod().equals("POST")) {

            if (!result.hasErrors()) {

                ServiceProxy oServiceProxy = new ServiceProxy(env);
                BaseResponse oBaseResponse = oServiceProxy.postJsonData("api/reclusos/update", objRecluso, new ArrayList<>() );
                oServiceProxy.close();

                if(oBaseResponse.getStatusAction() != 1 || oBaseResponse.getData() == null || oBaseResponse.getData() == "null")
                    throw new InternalError(oBaseResponse.getMessage());

                Recluso updatedRecluso = objectMapper.convertValue(oBaseResponse.getData(), Recluso.class);
                if(updatedRecluso != null)
                    return "redirect:/reclusos/view/" + updatedRecluso.getId().toString();
                else
                    throw new InternalError(oBaseResponse.getMessage());
            }
        }

        objRecluso = new ReclusoService(env).findOne(id.toString());
        if(objRecluso == null)
            throw new ResourceNotFoundException("Não possivel encontrar o recluso solicitado");

        model.addAttribute("objRecluso", objRecluso);
        return  "/views/recluso/update";
    }


    @RequestMapping(value = "reclusos/transfer/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public String actionTransfer(@Valid @ModelAttribute Recluso objRecluso, BindingResult result,
                               ModelMap model, HttpServletRequest request, @PathVariable UUID id) {

        if (request.getMethod().equals("POST")) {

            if (!result.hasErrors()) {

                ArrayList<Params> p = new ArrayList<>();
                BaseResponse oBaseResponse = (new ServiceProxy(env)).postJsonData("api/reclusos/transfer", objRecluso, new ArrayList<>() );

                if(oBaseResponse.getStatusAction() != 1 || oBaseResponse.getData() == null || oBaseResponse.getData() == "null")
                    throw new InternalError(oBaseResponse.getMessage());

                Recluso updatedRecluso = objectMapper.convertValue(oBaseResponse.getData(), Recluso.class);
                if(updatedRecluso != null)
                    return "redirect:/reclusos/view/" + updatedRecluso.getId().toString();
                else
                    throw new InternalError(oBaseResponse.getMessage());
            }
        }

        objRecluso = new ReclusoService(env).findOne(id.toString());
        if(objRecluso == null)
            throw new ResourceNotFoundException("Não possivel encontrar o recluso solicitado");

        model.addAttribute("objRecluso", objRecluso);
        return  "/views/recluso/transfer";
    }


    @RequestMapping(value = "reclusos/decisaoJudicial/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public String actiondecisaoJudicial(@Valid @ModelAttribute Recluso objRecluso, BindingResult result,
                                        ModelMap model, HttpServletRequest request, @PathVariable UUID id) {

        if (request.getMethod().equals("POST")) {

            if (!result.hasErrors()) {

                ArrayList<Params> p = new ArrayList<>();
                BaseResponse oBaseResponse = (new ServiceProxy(env)).postJsonData("api/reclusos/decisaoJudicial", objRecluso, new ArrayList<>() );

                if(oBaseResponse.getStatusAction() != 1 || oBaseResponse.getData() == null || oBaseResponse.getData() == "null")
                    throw new InternalError(oBaseResponse.getMessage());

                Recluso updatedRecluso = objectMapper.convertValue(oBaseResponse.getData(), Recluso.class);
                if(updatedRecluso != null)
                    return "redirect:/reclusos/view/" + updatedRecluso.getId().toString();
                else
                    throw new InternalError(oBaseResponse.getMessage());
            }
        }

        objRecluso = new ReclusoService(env).findOne(id.toString());
        if(objRecluso == null)
            throw new ResourceNotFoundException("Não possivel encontrar o recluso solicitado");

        model.addAttribute("objRecluso", objRecluso);
        return  "/views/recluso/decisaoJudicial";
    }
}
