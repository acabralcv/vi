package com.controllers;


import com.library.helpers.Helper;
import com.library.helpers.UtilsDate;
import com.library.models.WfActivities;
import com.library.models.WfProcess;
import com.library.repository.WfActivityRepository;
import com.library.repository.WfProcessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.UUID;

@Controller
public class ProcessController {

    @Autowired
    private WfProcessRepository wfProcessRepository;

    @Autowired
    private WfActivityRepository wfActivityRepository;

    /**
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "workflow/process", method = RequestMethod.GET)
    public String actionIndex(ModelMap model) {

        model.addAttribute("processes", wfProcessRepository.findAll());
        return "views/workflow/process/index";
    }

    /**
     * GET and POST
     * Encontra o 'processo' pelo id com as respetivas 'Activities'
     * Caso receber um 'Activity POST' vai adcioná-lo ao processo
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "workflow/process/view/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public String actionView(@Valid @ModelAttribute WfActivities wfActivity,
                             BindingResult result,
                             ModelMap model,
                             HttpServletRequest request,
                             @PathVariable UUID id) {

        WfProcess modelProcess = wfProcessRepository.findById(id)
                .map(process ->{
                    process.setName(process.getName() + "");
                    process.setActivitiesSet(process.getActivitiesSet());
                    return process;
                }).orElseThrow(() -> new ResourceNotFoundException(WfProcess.class.getName() + " not found with id " + id));

        System.out.println(modelProcess.getActivitiesSet().size());

        wfActivity.setProcess(modelProcess);
        wfActivity.setWfType(modelProcess.getProcessCode());

        if(request.getMethod().equals("POST")){
            if (!result.hasErrors()) {

                WfActivities oWfActivity = wfActivityRepository.findByName(wfActivity.getName());

                if(oWfActivity != null){
                    //if existe, update it
                    oWfActivity.setName(wfActivity.getName());
                    wfProcessRepository.save(modelProcess);
                }else{
                    wfActivityRepository.save(wfActivity);
                }
                return "redirect:/workflow/process/view/" + id;
            }
        }

        wfActivity.setWfStep(modelProcess.getActivitiesSet().size() + 1);
        wfActivity.setIsViewable(1);
        wfActivity.setSystemDefault(0);
        wfActivity.setClassName("com.library.models.CustumClass");
        wfActivity.setStateColor("default");

        model.addAttribute("modelProcess", modelProcess);
        model.addAttribute("wfActivity", wfActivity);
        return "views/workflow/process/view";
    }

    /**
     * CREATE NEW PROCESS
     * @param wfProcess
     * @param result
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "workflow/process/create", method = {RequestMethod.GET, RequestMethod.POST})
    public String actionCreate(@Valid @ModelAttribute WfProcess wfProcess,
                               BindingResult result, ModelMap model, HttpServletRequest request) {

        if(request.getMethod().equals("POST")){
            if (!result.hasErrors()) {

                WfProcess modelProcess = wfProcessRepository.findByProcessCode(wfProcess.getProcessCode());

                if(modelProcess != null){
                    //if existe, update it
                    modelProcess.setName(wfProcess.getName());
                    modelProcess.setDescription(wfProcess.getDescription());
                    wfProcessRepository.save(modelProcess);
                }else{
                    //otherwise, create a new one
                    wfProcess.setId(new Helper().getUUID());
                    wfProcess.setDescription(wfProcess.getDescription()  + " :: " + new UtilsDate().getCurrentDate("yyyy/MM/dd HH:mm:ss"));
                    wfProcessRepository.save(wfProcess);
                }

                return "redirect:/workflow/process";
            }
        }

        model.addAttribute("wfProcess", wfProcess);
        return "views/workflow/process/create";
    }
}