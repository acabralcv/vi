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
import java.util.Optional;
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
     * Caso receber um 'Activity POST' vai adcioná-lo e associala-lo ao processo
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = {"workflow/process/view/{id}", "workflow/process/view/{id}/{id_activity}"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String actionView(@Valid @ModelAttribute WfActivities wfActivity,
                             BindingResult result,
                             ModelMap model,
                             HttpServletRequest request,
                             @PathVariable UUID id,
                             @PathVariable(required = false) UUID id_activity
                             ) {

        WfProcess modelProcess = wfProcessRepository.findById(id)
                .map(process ->{
                    process.setName(process.getName() + "");
                    process.setActivitiesSet(process.getActivitiesSet());
                    return process;
                }).orElseThrow(() -> new ResourceNotFoundException(WfProcess.class.getName() + " not found with id " + id));

        wfActivity.setProcess(modelProcess);
        wfActivity.setWfType(modelProcess.getProcessCode());

        //ad
        if(request.getMethod().equals("POST")){
            if (!result.hasErrors()) {

                WfActivities oWfActivity = wfActivityRepository.findByNameAndWfType(wfActivity.getName(), wfActivity.getWfType());

                if(oWfActivity != null){
                    //if existe, update it
                    oWfActivity.setName(wfActivity.getName() + ":: UPDATED");
                    wfProcessRepository.save(modelProcess);
                }else{
                    wfActivityRepository.save(wfActivity);
                }
                return "redirect:/workflow/process/view/" + id;
            }
        }

        if(id_activity != null) {

            WfActivities auxWfActivity = wfActivityRepository.findById(id_activity).get();

            //Edit mode
            if (auxWfActivity != null) {
                wfActivity.setId(auxWfActivity.getId());
                wfActivity.setClassName(auxWfActivity.getClassName());
                wfActivity.setActionName(auxWfActivity.getActionName());
                wfActivity.setProcess(auxWfActivity.getProcess());
                wfActivity.setWfStep(auxWfActivity.getWfStep());
                wfActivity.setIsViewable(auxWfActivity.getIsViewable());
                wfActivity.setSystemDefault(auxWfActivity.getSystemDefault());
                wfActivity.setClassName(auxWfActivity.getClassName());
                wfActivity.setStateColor(auxWfActivity.getStateColor());
            } else {
                wfActivity.setWfStep(modelProcess.getActivitiesSet().size() + 1);
                wfActivity.setIsViewable(1);
                wfActivity.setSystemDefault(0);
                wfActivity.setClassName("com.library.models.CustumClass");
                wfActivity.setStateColor("default");
            }
        }

            model.addAttribute("modelProcess", modelProcess);
            model.addAttribute("wfActivity", wfActivity);
            return "views/workflow/process/view";
        }


    /**
     * CREATE OR UPDATE PROCESS
     * @param wfProcess
     * @param result
     * @param model
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(value = {"workflow/process/save", "workflow/process/save/{id}"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String actionSave(@Valid @ModelAttribute WfProcess wfProcess,
                               BindingResult result,
                               ModelMap model, HttpServletRequest request,
                               @PathVariable(required = false) UUID id) {

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
                    wfProcess.setDescription(wfProcess.getDescription()  + " :: " + new UtilsDate().getCurrentDate());
                    wfProcessRepository.save(wfProcess);
                }

                return "redirect:/workflow/process";
            }
        }

        if(id != null) {

            WfProcess auxWfProcess = wfProcessRepository.findById(id).get();

            //Edit mode
            if (auxWfProcess != null) {
                wfProcess.setId(auxWfProcess.getId());
                wfProcess.setProcessCode(auxWfProcess.getProcessCode());
                wfProcess.setName(auxWfProcess.getName());
                wfProcess.setDescription(auxWfProcess.getDescription());
            }
        }

        wfProcess.setName("bla  bla");
        model.addAttribute("wfProcess", wfProcess);
        return "views/workflow/process/save";
    }



    @RequestMapping(value = {"workflow/process/disable/{id}"}, method = {RequestMethod.POST})
    public String actionDelete( BindingResult result, ModelMap model, HttpServletRequest request,@PathVariable(required = true) UUID id) {

        if(request.getMethod().equals("POST")){

            wfProcessRepository.findById(id)
                    .map(process ->{
                        process.setStatus(Helper.STATUS_DISABLED);
                        process.setDatedUpdated(UtilsDate.getCurrentDate());
                        return wfProcessRepository.save(process);
                    }).orElseThrow(() -> new ResourceNotFoundException(WfProcess.class.getName() + " not found with id " + id));

                return "redirect:/workflow/process";
        }

        return "views/workflow/process/view/" + id;
    }
}