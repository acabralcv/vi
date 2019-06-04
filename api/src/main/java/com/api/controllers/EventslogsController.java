package com.api.controllers;

import com.library.helpers.BaseResponse;
import com.library.helpers.Helper;
import com.library.models.Domain;
import com.library.models.Eventslog;
import com.library.models.Profile;
import com.library.repository.EventslogRepository;
import com.library.service.EventsLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@RestController
public class EventslogsController {

    @Autowired
    EventslogRepository eventslogRepository;

    @RequestMapping(value = {"api/eventslog"}, method = {RequestMethod.GET})
    public ResponseEntity actionDetails(@PageableDefault(sort = {"message"}, value = 10, page = 0) Pageable pageable) {

        try  {


            Pageable pageableBuilded = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("dateCreated")
                    .descending()
                    .and(Sort.by("message").ascending()));
            Page<Eventslog> eventslog = eventslogRepository.findByStatus(Helper.STATUS_ACTIVE, pageableBuilded);

            return ResponseEntity.ok().body(new BaseResponse().getObjResponse(1,"ok", eventslog ));

        }catch (Exception e){
            new EventsLogService(eventslogRepository).AddEventologs(null,"Excption in class '" + this.getClass().getName()
                    + "' method " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",e.getMessage(),null, null);
            return ResponseEntity.ok().body(new BaseResponse(0, e.getMessage(), null));
        }
    }


    @RequestMapping(value = {"api/eventslog/details"}, method = {RequestMethod.GET})
    public ResponseEntity actionDetails(@RequestParam(name = "id") UUID id) {

        try {

            Optional<Eventslog> oEventslog = (Optional) eventslogRepository.findById(id);

            return ResponseEntity.ok().body(new BaseResponse().getObjResponse(oEventslog != null ? 1 : 0,"ok", oEventslog));

        }catch (Exception e){
            new EventsLogService(eventslogRepository).AddEventologs(null,"Excption in class '" + this.getClass().getName()
                    + "' method " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",e.getMessage(),null, null);
            return ResponseEntity.ok().body(new BaseResponse(0, e.getMessage(), null));
        }
    }






    /**
     * TESTES WORKFLOW
     */
    @RequestMapping(value = {"api/workflows/teste_01"}, method = {RequestMethod.POST})
    public ResponseEntity actionTesteFlow_01(@RequestBody Profile objProfile) {

        try {

            return ResponseEntity.ok().body(new BaseResponse().getObjResponse(1,"ok", objProfile));

        }catch (Exception e){
            new EventsLogService(eventslogRepository).AddEventologs(null,"Excption in class '" + this.getClass().getName()
                    + "' method " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",e.getMessage(),null, null);
            return ResponseEntity.ok().body(new BaseResponse(0, e.getMessage(), null));
        }
    }
}
