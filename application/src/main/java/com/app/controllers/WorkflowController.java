package com.app.controllers;

import com.app.helpers.ServiceProxy;
import com.library.helpers.BaseResponse;
import com.app.helpers.FlowConfig;
import com.library.helpers.HelperPaging;
import com.library.models.Profile;
import com.library.models.Workflow;
import com.library.repository.ProfileRepository;
import com.library.repository.StatesRepository;
import com.library.repository.WorkflowRepository;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;

@Controller
public class WorkflowController {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private StatesRepository statesRepository;

    @Autowired
    private WorkflowRepository workflowRepository;


    @RequestMapping(value = "workflows", method = RequestMethod.GET)
    public String actionIndex(ModelMap model, @PageableDefault(page = 0, size = 10) Pageable pageable) {

        //get info
        BaseResponse objResponse = (new ServiceProxy())
                .getJsonData("api/workflows", (new ServiceProxy()).encodePageableParams(pageable));

        //Pageable result objt
        JSONObject dataResponse = (JSONObject) objResponse.getData();

        //check result
        ArrayList<Workflow> workflows = (ArrayList<Workflow>) dataResponse.get("content");

        model.addAttribute("objPaging", (new HelperPaging().getResponsePaging(pageable, dataResponse)));
        model.addAttribute("workflows", workflows);

        return  "/views/workflow/index";
    }

    @RequestMapping(value = "workflows/teste_01", method = RequestMethod.GET)
    public String actionTest_01(ModelMap model) {

        FlowConfig config = new FlowConfig();
        config.setProcessCode("PROCESS_USER_REGISTRATION");
        config.setResourse("api/workflows/teste_01");
        config.setoEntity(new Profile());
        config.setStep(1);

        BaseResponse oBaseResponse = new FlowConfig(statesRepository, workflowRepository)
                .transitionFlow(config);
        System.out.println(oBaseResponse.getMessage());

        return  "/views/workflow/index";
    }

}
