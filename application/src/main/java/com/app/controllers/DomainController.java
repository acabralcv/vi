package com.app.controllers;

import com.app.exceptions.ResourceNotFoundException;
import com.app.helpers.Params;
import com.app.helpers.ServiceProxy;
import com.app.service.DomainService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.helpers.BaseResponse;
import com.library.helpers.HelperPaging;
import com.library.models.Domain;
import com.library.repository.DomainRepository;
import com.library.service.EventsLogService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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

    @Autowired
    DomainRepository domainRepository;

    final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private Environment env;

    @RequestMapping(value = "admin/domains", method = RequestMethod.GET)
    public String actionIndex(ModelMap model, @RequestParam(name = "domainType", required = false) String domainType, @PageableDefault(sort = {"name"}, value = 10, page = 0) Pageable pageable) {

        ArrayList<Domain> domains = new ArrayList<>();
        HelperPaging objPaging = null;
        ServiceProxy oServiceProxy = new ServiceProxy(env);

        if(domainType != null) {
            //em caso do cliente especificar o Tipo de Dominio
            domains = DomainService.getDomains(oServiceProxy, domainType);
        }else{
            //caso contrario, vamos pegar os resultado paginados
            BaseResponse objResponse = oServiceProxy.getJsonData("api/domains", oServiceProxy.encodePageableParams(pageable));

            //Pageable result objt
            JSONObject dataResponse = (JSONObject) objResponse.getData();
            objPaging = (new HelperPaging().getResponsePaging(pageable, dataResponse));
            domains = (ArrayList<Domain>) dataResponse.get("content");

            oServiceProxy.close();
        }

        model.addAttribute("objPaging", objPaging);
        model.addAttribute("domains", domains);

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
        ServiceProxy oServiceProxy = new ServiceProxy(env);

        BaseResponse oBaseResponse = oServiceProxy
                .buildParams("api/domains/details", params)
                .getTarget()
                .get(BaseResponse.class);

        oServiceProxy.close();

        Domain oDomain = objectMapper.convertValue(oBaseResponse.getData(), Domain.class);

        if(oDomain == null)
            new ResourceNotFoundException("Não possivel encontrar o 'Dominio' solicitado");

        model.addAttribute("oDomain", oDomain);
        model.addAttribute("domains", DomainService.getDomains(oServiceProxy, oDomain.getDomainType()));

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

        ServiceProxy oServiceProxy = new ServiceProxy(env);

        if (request.getMethod().equals("POST")) {

            if (!result.hasErrors()) {

                ArrayList<Params> p = new ArrayList<>();
                BaseResponse oBaseResponse = oServiceProxy.postJsonData("api/domains/create", oDomain, new ArrayList<>() );

                oServiceProxy.close();

                if(oBaseResponse.getStatusAction() != 1 || oBaseResponse.getData() == null || oBaseResponse.getData() == "null")
                    throw new InternalError(oBaseResponse.getMessage());

                Domain createdDomain = objectMapper.convertValue(oBaseResponse.getData(), Domain.class);

                if(createdDomain != null)
                    return "redirect:/admin/domains/view/" + createdDomain.getId().toString();
                else
                    throw new InternalError(oBaseResponse.getMessage());
            }
        }

        model.addAttribute("oDomain", oDomain);
        model.addAttribute("domains", DomainService.getDomains(oServiceProxy, domainType));
        return  "/views/domain/create";
    }


    @RequestMapping(value = "admin/domains/update", method = {RequestMethod.GET, RequestMethod.POST})
    public String actionUpdate(@Valid @ModelAttribute Domain oDomain,
                               BindingResult result,
                               ModelMap model, HttpServletRequest request) {

        ServiceProxy oServiceProxy = new ServiceProxy(env);

        if (request.getMethod().equals("POST")) {

            if (!result.hasErrors()) {

                ArrayList<Params> p = new ArrayList<>();
                BaseResponse oBaseResponse = oServiceProxy.postJsonData("api/domains/update", oDomain, new ArrayList<>() );
                oServiceProxy.close();

                if(oBaseResponse.getStatusAction() != 1 || oBaseResponse.getData() == null || oBaseResponse.getData() == "null")
                    throw new InternalError(oBaseResponse.getMessage());

                Domain updatedDomain = objectMapper.convertValue(oBaseResponse.getData(), Domain.class);

                if(updatedDomain != null)
                    return "redirect:/admin/domains/view/" + updatedDomain.getId().toString();
                else
                    throw new InternalError(oBaseResponse.getMessage());
            }
        }

        model.addAttribute("oDomain", oDomain);
        return  "/views/domain/update";
    }

}