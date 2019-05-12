package com.app.controllers;

import com.library.helpers.Helper;
import com.library.helpers.UtilsDate;
import com.library.models.WfActivities;
import com.library.models.WfProcess;
import com.library.repository.WfActivityRepository;
import com.library.repository.WfProcessRepository;
import com.library.service.EventsLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.UUID;

@Controller
public class ActivitiesController {

    @Autowired
    private WfProcessRepository wfProcessRepository;

    @Autowired
    private WfActivityRepository wfActivityRepository;


    /**
     * CREATE NEW PROCESS ACTIVITY
     * @param wfActivity
     * @param result
     * @param model
     * @param request
     * @param process_id
     * @return
     */
    @RequestMapping(value = {"workflow/activities/create/{process_id}"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String actionCreate(@Valid @ModelAttribute WfActivities wfActivity,
                               BindingResult result,
                               ModelMap model,
                               HttpServletRequest request,
                               @PathVariable UUID process_id) {

        //try to find the process
        WfProcess objProcess = wfProcessRepository.findById(process_id)
                .map(process ->{
                    process.setName(process.getName() + "");
                    process.setActivitiesSet(process.getActivitiesSet());
                    return process;
                }).orElseThrow(() -> new ResourceNotFoundException(WfProcess.class.getName() + " not found with id '" + process_id + "'"));

        //set unchanged values to the activity object
        wfActivity.setProcess(objProcess);
        wfActivity.setWfType(objProcess.getProcessCode());

        //if activity POST is provided
        if(request.getMethod().equals("POST")){
            if (!result.hasErrors()) {

                wfActivity.setStatus(Helper.STATUS_ACTIVE);

                if( wfActivity.getId() != null && wfActivityRepository.findById(wfActivity.getId()).isPresent()){
                    //if existe, lets update the updated date
                    wfActivity.setDatedUpdated(UtilsDate.getDateTime());
                }else{
                    wfActivity.setId(new Helper().getUUID());
                    wfActivity.setDateCreated(UtilsDate.getDateTime());
                }

                wfActivityRepository.save(wfActivity);

                //redirect back to process view
                return "redirect:/workflow/process/view/" + process_id;
            }
        }

        //default vaalues
        wfActivity.setSystemDefault(0);
        wfActivity.setIsViewable(1);
        wfActivity.setWfStep(objProcess.getActivitiesSet().size() + 1);;
        wfActivity.setStateColor("#fff");

        wfActivity.setProcess(objProcess);
        wfActivity.setWfType(objProcess.getProcessCode());
        model.addAttribute("objProcess", objProcess);
        model.addAttribute("wfActivity", wfActivity);

        return "views/workflow/activities/create";
    }


    /**
     * UPDATE THE PROCESS ACTIVITY
     * @param wfActivity
     * @param result
     * @param model
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(value = {"workflow/activities/update/{id}"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String actionUpdate(@Valid @ModelAttribute WfActivities wfActivity,
                               BindingResult result,
                               ModelMap model,
                               HttpServletRequest request,
                               @PathVariable UUID id) {
        try{

            WfActivities objActivity = wfActivityRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(WfProcess.class.getName() + " not found with id '" + id + "'"));

            //lets get the process obj from activity
            WfProcess objProcess = objActivity.getProcess();

            //if activity POST is provided
            if(!request.getMethod().equals("POST")){
                wfActivity = objActivity;
            }else{
                if (!result.hasErrors()) {

                    wfActivity.setProcess(objProcess);
                    wfActivity.setWfType(objProcess.getProcessCode());
                    wfActivity.setDatedUpdated(UtilsDate.getDateTime());
                    wfActivity.setStatus(Helper.STATUS_ACTIVE);

                    wfActivityRepository.save(wfActivity);

                    //redirect back to process view
                    return "redirect:/workflow/process/view/" + objProcess.getId();
                }
            }
            wfActivity.setProcess(objProcess);
            wfActivity.setWfType(objProcess.getProcessCode());
            model.addAttribute("objProcess", objProcess);
            model.addAttribute("wfActivity", wfActivity);

        }catch (Exception e){
            new EventsLogService().AddEventologs(null,"Excption in " + this.getClass().getName(), e.getMessage(),null);
        }
        return "views/workflow/activities/update";
    }


    @RequestMapping(value = {"workflow/activities/disable/{id}"}, method = {RequestMethod.POST})
    public String actionDelete(@Valid @ModelAttribute WfActivities wfActivity,
                               BindingResult result,
                               ModelMap model,
                               HttpServletRequest request,
                               @PathVariable UUID id) {

        try{

            WfActivities objActivity = wfActivityRepository.findById(id)
                    .map(activity ->{
                        activity.setStatus(Helper.STATUS_DISABLED);
                        activity.setDatedUpdated(UtilsDate.getDateTime());
                        return wfActivityRepository.save(activity);
                    })
                    .orElseThrow(() -> new ResourceNotFoundException(WfProcess.class.getName() + " not found with id '" + id + "'"));

            //lets get the process obj from activity
            WfProcess objProcess = objActivity.getProcess();

            return "redirect:/workflow/process/view/" + objProcess.getId();

        }catch (Exception e){
            new EventsLogService().AddEventologs(null,"Excption in " + this.getClass().getName(), e.getMessage(),null);
        }
        return "redirect:/workflow/process/";
    }


    }
