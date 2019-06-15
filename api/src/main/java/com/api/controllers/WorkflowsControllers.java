package com.api.controllers;

import com.library.helpers.BaseResponse;
import com.library.helpers.Helper;
import com.library.models.*;
import com.library.repository.*;
import com.library.service.EventsLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;
@RestController
public class WorkflowsControllers {

    @Autowired
    private WorkflowRepository workflowRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StatesRepository statesRepository;

    @Autowired
    private PaisRepository paisRepository;

    @RequestMapping(value = {"api/worflows"}, method = {RequestMethod.GET})
    public ResponseEntity actionPaises(@PageableDefault(sort = {"name"}, value = 10, page = 0) Pageable pageable) {

        try{

            Pageable pageableBuilded = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("dateCreated").descending());

            Page<Workflow> workflows = workflowRepository.findByStatus(Helper.STATUS_ACTIVE, pageableBuilded);

            return ResponseEntity.ok().body(new BaseResponse().getObjResponse(1,"ok", workflows ));

        }catch (Exception e){
            new EventsLogService(userRepository).AddEventologs(null,"Excption in class '" + this.getClass().getName()
                    + "' method " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",e.getMessage(),null, null);
            return ResponseEntity.ok().body(new BaseResponse(0, e.getMessage(), null));
        }
    }

    @RequestMapping(value = {"api/worflows/details"}, method = {RequestMethod.GET})
    public ResponseEntity actionDetails(@RequestParam(name = "id", required = false) UUID id,
                                        @RequestParam(name = "targetTableId", required = false) UUID targetTableId) {

        try {

            Optional<Workflow> workflow = null;

            if(id != null) {
                workflow = workflowRepository.findById(id).map(w -> { w.setStates(w.getStates()); return w; });
            }else {
                workflow = workflowRepository.findByTargetTableId(targetTableId).map(w -> {w.setStates(w.getStates());return w;});
            }


            return ResponseEntity.ok().body(new BaseResponse(workflow != null ? 1 : 0, "ok", workflow));

        } catch (Exception e) {
            new EventsLogService(userRepository).AddEventologs(null, "Excption in class '" + this.getClass().getName()
                    + "' method " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()", e.getMessage(), null, null);
            return ResponseEntity.ok().body(new BaseResponse(0, e.getMessage(), null));
        }
    }




    @RequestMapping(value = {"api/worflows/states"}, method = {RequestMethod.GET})
    public ResponseEntity actionGetStates(@RequestParam(name = "id") UUID id) {

        try{

            ArrayList<States> states = new ArrayList<>();
            Optional<Workflow> workflowOptional = workflowRepository.findById(id);

            //ultimos 6
            Pageable pageableBuilded = PageRequest.of(0, 10, Sort.by("step").descending());

            if(workflowOptional.isPresent())
                states = statesRepository.findByWorkflow(workflowOptional.get());

            return ResponseEntity.ok().body(new BaseResponse().getObjResponse(1,"ok", states ));

        }catch (Exception e){
            new EventsLogService(userRepository).AddEventologs(null,"Excption in class '" + this.getClass().getName()
                    + "' method " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",e.getMessage(),null, null);
            return ResponseEntity.ok().body(new BaseResponse(0, e.getMessage(), null));
        }
    }



    /**
     * TESTES WORKFLOW
     */
    @RequestMapping(value = {"api/workflows/teste_01"}, method = {RequestMethod.POST})
    public ResponseEntity actionTesteFlow_01(@RequestBody Workflow workflow) {

        try {

            UUID id = UUID.fromString("6f9bb942-2d41-4076-96ba-b01e92ad6acc");

            Pais pais = paisRepository.findById(id).get();

            return ResponseEntity.ok().body(new BaseResponse().getObjResponse(1,"ok", pais));

        }catch (Exception e){
            new EventsLogService(userRepository).AddEventologs(null,"Excption in class '" + this.getClass().getName()
                    + "' method " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",e.getMessage(),null, null);
            return ResponseEntity.ok().body(new BaseResponse(0, e.getMessage(), null));
        }
    }
}
