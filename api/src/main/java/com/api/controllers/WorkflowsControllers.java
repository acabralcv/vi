package com.api.controllers;

import com.library.helpers.BaseResponse;
import com.library.helpers.Helper;
import com.library.models.Pais;
import com.library.models.User;
import com.library.models.Workflow;
import com.library.repository.*;
import com.library.service.EventsLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;
@RestController
public class WorkflowsControllers {

    @Autowired
    private WorkflowRepository workflowRepository;

    @Autowired
    private EventslogRepository eventslogRepository;

    @RequestMapping(value = {"api/worflows"}, method = {RequestMethod.GET})
    public ResponseEntity actionPaises(@PageableDefault(sort = {"name"}, value = 10, page = 0) Pageable pageable) {

        try{

            Pageable pageableBuilded = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("dateCreateddateCreated").descending());

            Page<Workflow> workflows = workflowRepository.findByStatus(Helper.STATUS_ACTIVE, pageableBuilded);

            return ResponseEntity.ok().body(new BaseResponse().getObjResponse(1,"ok", workflows ));

        }catch (Exception e){
            new EventsLogService(eventslogRepository).AddEventologs(null,"Excption in class '" + this.getClass().getName()
                    + "' method " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",e.getMessage(),null, null);
            return ResponseEntity.ok().body(new BaseResponse(0, e.getMessage(), null));
        }
    }

    @RequestMapping(value = {"api/worflows/details"}, method = {RequestMethod.GET})
    public ResponseEntity actionDetails(@RequestParam(name = "id") UUID id) {

        try {

            Optional<Workflow> workflow = workflowRepository.findById(id)
                    .map(w -> {
                        w.setStates(w.getStates());
                        return w;
                    });

            return ResponseEntity.ok().body(new BaseResponse(workflow != null ? 1 : 0, "ok", workflow));

        } catch (Exception e) {
            new EventsLogService(eventslogRepository).AddEventologs(null, "Excption in class '" + this.getClass().getName()
                    + "' method " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()", e.getMessage(), null, null);
            return ResponseEntity.ok().body(new BaseResponse(0, e.getMessage(), null));
        }
    }

}
