package com.controllers;


import com.helpers.Helper;
import com.helpers.UtilsDate;
import com.models.WfProcess;
import com.repository.WfProcessRepository;
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

    @RequestMapping(value = "workflow/process", method = RequestMethod.GET)
    public String actionIndex(ModelMap model) {

        model.addAttribute("processes", wfProcessRepository.findAll());
        return "views/workflow/process/index";
    }

    @RequestMapping(value = "workflow/process/view/{id}", method = RequestMethod.GET)
    public String actionView(ModelMap model, @PathVariable UUID id) {

        WfProcess modelProcess = wfProcessRepository.findById(id)
                .map(process ->{
                    process.setName(process.getName() + "");
                    return process;
                }).orElseThrow(() -> new ResourceNotFoundException(WfProcess.class.getName() + " not found with id " + id));

        model.addAttribute("modelProcess", modelProcess);
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