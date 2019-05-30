package com.app.controllers;

import com.app.helpers.Params;
import com.app.helpers.ServiceProxy;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.helpers.BaseResponse;
import com.library.helpers.HelperPaging;
import com.library.models.Domain;
import com.library.repository.DomainRepository;
import com.library.service.EventsLogService;
import org.json.simple.JSONObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.UUID;

@Controller
public class DomainController {

    DomainRepository domainRepository;
    final ObjectMapper objectMapper = new ObjectMapper();
    final ServiceProxy oServiceProxy = new ServiceProxy();

    @RequestMapping(value = "admin/domains", method = RequestMethod.GET)
    public String actionIndex(ModelMap model,@PageableDefault(sort = {"name"}, value = 10, page = 0) Pageable pageable) {

        //get info
        BaseResponse objResponse = (new ServiceProxy())
                .getJsonData("api/domains", (new ServiceProxy()).encodePageableParams(pageable));

        //Pageable result objt
        JSONObject dataResponse = (JSONObject) objResponse.getData();

        model.addAttribute("objPaging", (new HelperPaging().getResponsePaging(pageable, dataResponse)));
        model.addAttribute("domains", (ArrayList<Domain>) dataResponse.get("content"));

        return  "/views/domain/index";
    }

    /**
     *
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "admin/domains/view/{id}", method = RequestMethod.GET)
    public String actionDetails(ModelMap model, @PathVariable UUID id) {

        ArrayList<Params> params = new ArrayList<>();
        params.add(new Params("id", id.toString()));

        BaseResponse oBaseResponse = oServiceProxy
                .buildParams("api/domains/details", params)
                .getTarget()
                .get(BaseResponse.class);
        oServiceProxy.close();

        if(oBaseResponse.getData() == null || oBaseResponse.getData() == "null")
            new ResourceNotFoundException("Dominio não encontrado");

        Domain oDomain = objectMapper.convertValue(oBaseResponse.getData(), Domain.class);

        model.addAttribute("oDomain", oDomain);

        return  "/views/domain/view";
    }


    /**
     *
     * @param model
     * @param domainType
     * @return
     */
    @RequestMapping(value = "admin/domains/create", method = {RequestMethod.GET, RequestMethod.POST})
    public String actionCreate(@Valid @ModelAttribute Domain oDomain,
                               BindingResult result,
                               ModelMap model, HttpServletRequest request,
                               @RequestParam(name = "domainType", required = false) String domainType) {


        if (request.getMethod().equals("POST")) {

            try {

                if (!result.hasErrors()) {

                    ArrayList<Params> p = new ArrayList<>();
                    BaseResponse oBaseResponse = (new ServiceProxy()).postJsonData("api/domains/create", oDomain, new ArrayList<>() );

                    if(oBaseResponse.getStatusAction() != 1 || oBaseResponse.getData() == null || oBaseResponse.getData() == "null")
                        new Exception(oBaseResponse.getMessage());

                    Domain createdDomain = objectMapper.convertValue(oBaseResponse.getData(), Domain.class);

                    return "redirect:/admin/domains/view/" + createdDomain.getId().toString();
                }

            } catch (Exception e) {
                new EventsLogService().AddEventologs(null, "Excption in " + this.getClass().getName(), e.getMessage(), null);
            }
        }

        model.addAttribute("oDomain", oDomain);
        return  "/views/domain/create";
    }


    @RequestMapping(value = "admin/domains/update", method = {RequestMethod.GET, RequestMethod.POST})
    public String actionUpdate(@Valid @ModelAttribute Domain oDomain,
                               BindingResult result,
                               ModelMap model, HttpServletRequest request) {

        if (request.getMethod().equals("POST")) {

            try {

                if (!result.hasErrors()) {

                    ArrayList<Params> p = new ArrayList<>();
                    BaseResponse oBaseResponse = (new ServiceProxy()).postJsonData("api/domains/update", oDomain, new ArrayList<>() );

                    if(oBaseResponse.getStatusAction() != 1 || oBaseResponse.getData() == null || oBaseResponse.getData() == "null")
                        new Exception(oBaseResponse.getMessage());

                    Domain updatedDomain = objectMapper.convertValue(oBaseResponse.getData(), Domain.class);

                    return "redirect:/admin/domains/view/" + updatedDomain.getId().toString();
                }

            } catch (Exception e) {
                new EventsLogService().AddEventologs(null, "Excption in " + this.getClass().getName(), e.getMessage(), null);
            }
        }

        model.addAttribute("oDomain", oDomain);

        return  "/views/domain/update";
    }

}