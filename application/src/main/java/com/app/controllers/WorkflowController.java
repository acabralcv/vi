package com.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class WorkflowController {

//    @Autowired
//    private WfProcessRepository wfProcessRepository;

    /**
    *
    * @param model
    * @return
    */
    @RequestMapping(value = "workflows", method = RequestMethod.GET)
    public String actionIndex(ModelMap model) {

        //model.addAttribute("processes", wfProcessRepository.findAll());
        return "views/workflow/process/index";
    }
}