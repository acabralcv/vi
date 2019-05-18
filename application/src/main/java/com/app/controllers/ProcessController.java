package com.app.controllers;


import com.library.helpers.Helper;
import com.library.helpers.UtilsDate;
import com.library.models.WfActivities;
import com.library.models.WfProcess;
import com.library.repository.WfActivityRepository;
import com.library.repository.WfProcessRepository;
import com.library.service.EventsLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class ProcessController {

    @Autowired
    private WfProcessRepository wfProcessRepository;

    @Autowired
    private WfActivityRepository wfActivityRepository;

    @RequestMapping(value = "workflow/process", method = RequestMethod.GET)
    public String actionIndex(ModelMap model, Pageable pageable) {

        Pageable firstPageWithTwoElements = PageRequest.of(0, 5);

        Page<WfProcess> process = wfProcessRepository.findByStatus(Helper.STATUS_ACTIVE,
                PageRequest.of(0, 3, Sort.by("dateCreated")
                        .descending()
                        .and(Sort.by("name").ascending())));

        int totalPages = process.getTotalPages();
        List<Integer> pageNumbers = null;

        if (totalPages > 0) {
            pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
        }

        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("processes", process);
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
    @RequestMapping(value = {"workflow/process/view/{id}"}, method = {RequestMethod.GET, RequestMethod.POST})
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

        wfActivity.setProcess(modelProcess);
        wfActivity.setWfType(modelProcess.getProcessCode());

        //ad
        if(request.getMethod().equals("POST")){
            if (!result.hasErrors()) {

                WfActivities oWfActivity = wfActivityRepository.findByNameAndWfType(wfActivity.getName(), wfActivity.getWfType());

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

        model.addAttribute("modelProcess", modelProcess);
        //model.addAttribute("wfActivity", wfActivity);
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
    @RequestMapping(value = {"workflow/process/create"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String actionCreate(@Valid @ModelAttribute WfProcess wfProcess,
                               BindingResult result,
                               ModelMap model, HttpServletRequest request,
                               @PathVariable(required = false) UUID id) {

        try {

            if (request.getMethod().equals("POST")) {
                if (!result.hasErrors()) {

                    WfProcess modelProcess = wfProcessRepository.findByProcessCode(wfProcess.getProcessCode());

                    if (modelProcess != null)
                        new Exception("Process code allread added");

                    wfProcess.setId(new Helper().getUUID());
                    wfProcessRepository.save(wfProcess);
                }

                return "redirect:/workflow/process";
            }

            model.addAttribute("wfProcess", wfProcess);

        }catch(Exception e){
            new EventsLogService().AddEventologs(null,"Excption in " + this.getClass().getName(), e.getMessage(),null);
        }
        return "views/workflow/process/create";
    }


    @RequestMapping(value = {"workflow/process/update/{id}"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String actionUpdate(@Valid @ModelAttribute WfProcess wfProcess,
                               BindingResult result,
                               ModelMap model,
                               HttpServletRequest request,
                               @PathVariable UUID id) {
        try{

            WfProcess objProcess = wfProcessRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(WfProcess.class.getName() + " not found with id '" + id + "'"));

            //if activity POST is provided
            if(!request.getMethod().equals("POST")){
                wfProcess = objProcess;
            }else{
                if (!result.hasErrors()) {

                    wfProcess.setDateUpdated(UtilsDate.getDateTime());
                    wfProcess.setStatus(Helper.STATUS_ACTIVE);

                    wfProcessRepository.save(wfProcess);

                    //redirect back to process view
                    return "redirect:/workflow/process/view/" + objProcess.getId();
                }
            }

            model.addAttribute("wfProcess", wfProcess);

        }catch (Exception e){
            new EventsLogService().AddEventologs(null,"Excption in " + this.getClass().getName(), e.getMessage(),null);
        }
        return "views/workflow/process/update";
    }


    @RequestMapping(value = {"workflow/process/disable/{id}"}, method = {RequestMethod.POST})
    public String actionDisable( BindingResult result, ModelMap model, HttpServletRequest request,@PathVariable(required = true) UUID id) {

        if(request.getMethod().equals("POST")){

            wfProcessRepository.findById(id)
                    .map(process ->{
                        process.setStatus(Helper.STATUS_DISABLED);
                        process.setDateUpdated(UtilsDate.getDateTime());
                        return wfProcessRepository.save(process);
                    }).orElseThrow(() -> new ResourceNotFoundException(WfProcess.class.getName() + " not found with id " + id));

                return "redirect:/workflow/process";
        }

        return "views/workflow/process/view/" + id;
    }
}