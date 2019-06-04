package com.app.controllers;

import com.app.exceptions.ResourceNotFoundException;
import com.app.helpers.Params;
import com.app.helpers.ServiceProxy;
import com.library.helpers.BaseResponse;
import com.app.helpers.FlowConfig;
import com.library.helpers.HelperPaging;
import com.library.models.Profile;
import com.library.models.States;
import com.library.models.Workflow;
import com.library.repository.ProfileRepository;
import com.library.repository.StatesRepository;
import com.library.repository.WorkflowRepository;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class WorkflowController {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private StatesRepository statesRepository;

    @Autowired
    private WorkflowRepository workflowRepository;


    @RequestMapping(value = "admin/workflows/view/{id}", method = RequestMethod.GET)
    public String actionDetails(ModelMap model, @PathVariable UUID id) {

        ServiceProxy oServiceProxy = new ServiceProxy();
        BaseResponse oBaseResponse = oServiceProxy
                .buildParams("api/worflows/details", new Params().Add(new Params("id", id.toString())).Get())
                .getTarget()
                .get(BaseResponse.class);
        oServiceProxy.close();

        Workflow oWorkflow = (Workflow) BaseResponse.convertToModel(oBaseResponse, new Workflow());

        if(oWorkflow == null)
            throw new ResourceNotFoundException("Não possivel encontrar o 'Workflow' solicitado");

        System.out.println(statesRepository.findByWorkflow(oWorkflow));

        model.addAttribute("wfStates", statesRepository.findByWorkflow(oWorkflow));
        model.addAttribute("oWorkflow", oWorkflow);

        return  "/views/workflow/view";
    }


    @RequestMapping(value = "admin/workflows", method = RequestMethod.GET)
    public String actionIndex(ModelMap model, @PageableDefault(page = 0, size = 10) Pageable pageable) {

        //get info
        BaseResponse objResponse = (new ServiceProxy())
                .getJsonData("api/worflows", (new ServiceProxy()).encodePageableParams(pageable));

        //Pageable result objt
        JSONObject dataResponse = (JSONObject) objResponse.getData();

        //check result
        ArrayList<Workflow> workflows = (ArrayList<Workflow>) dataResponse.get("content");

        model.addAttribute("objPaging", (new HelperPaging().getResponsePaging(pageable, dataResponse)));
        model.addAttribute("workflows", workflows);

        return  "/views/workflow/index";
    }

    @RequestMapping(value = "admin/workflows/teste_01", method = RequestMethod.GET)
    public String actionTest_01(ModelMap model) {

        FlowConfig config = new FlowConfig();
        config.setProcessCode("PROCESS_USER_REGISTRATION");
        config.setObjEntity(new Profile());
        config.setStep(1);

        BaseResponse oBaseResponse = new FlowConfig(statesRepository, workflowRepository)
                .transitionFlow(config);
        System.out.println(oBaseResponse.getMessage());

        return "redirect:/admin/workflows/teste_02/";
        //return  "/views/workflow/index";
    }

    @RequestMapping(value = "admin/workflows/teste_02", method = RequestMethod.GET)
    public String actionTest_02(ModelMap model) {

        FlowConfig config = new FlowConfig();
        config.setProcessCode("PROCESS_USER_REGISTRATION");
        config.setObjEntity(new Profile());
        config.setStep(2);

        BaseResponse oBaseResponse = new FlowConfig(statesRepository, workflowRepository)
                .transitionFlow(config);
        System.out.println(oBaseResponse.getMessage());

        return "redirect:/admin/workflows/teste_03/";
    }

    @RequestMapping(value = "admin/workflows/teste_03", method = RequestMethod.GET)
    public String actionTest_03(ModelMap model) {

        FlowConfig config = new FlowConfig();
        config.setProcessCode("PROCESS_USER_REGISTRATION");
        config.setObjEntity(new Profile());
        config.setStep(3);

        BaseResponse oBaseResponse = new FlowConfig(statesRepository, workflowRepository)
                .transitionFlow(config);
        System.out.println(oBaseResponse.getMessage());

        return  "/views/workflow/index";
    }

}
