package com.api.controllers;

import com.library.models.Ilha;
import com.library.models.Pais;
import com.library.helpers.Helper;
import com.library.helpers.BaseResponse;
import com.library.repository.EventslogRepository;
import com.library.repository.IlhaRepository;
import com.library.service.EventsLogService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import com.library.repository.PaisRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaisesController {

    @Autowired
    PaisRepository paisRepository;

    @Autowired
    IlhaRepository ilhaRepository;

    @Autowired
    EventslogRepository eventslogRepository;

    @RequestMapping(value = {"api/paises"}, method = {RequestMethod.GET})
    public ResponseEntity actionPaises(@PageableDefault(sort = {"name"}, value = 10, page = 0) Pageable pageable) {

        try{

            Pageable pageableBuilded = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("nome")
                    .ascending()
                    .and(Sort.by("dateCreated").descending()));

            Page<Pais> paises = paisRepository.findByStatus(Helper.STATUS_ACTIVE, pageableBuilded);

            return ResponseEntity.ok().body(new BaseResponse().getObjResponse(1,"ok", paises ));

        }catch (Exception e){
            new EventsLogService(eventslogRepository).AddEventologs(null,"Excption in class '" + this.getClass().getName()
                    + "' method " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",e.getMessage(),null, null);
            return ResponseEntity.ok().body(new BaseResponse(0, e.getMessage(), null));
        }
    }

    @RequestMapping(value = {"api/ilhas"}, method = {RequestMethod.GET})
    public ResponseEntity actionIlhas(@PageableDefault(sort = {"name"}, value = 10, page = 0) Pageable pageable) {

        try{

            Pageable pageableBuilded = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("nome")
                    .ascending()
                    .and(Sort.by("dateCreated").descending()));

            Page<Ilha> ilhas = ilhaRepository.findByStatus(Helper.STATUS_ACTIVE, pageableBuilded);

            return ResponseEntity.ok().body(new BaseResponse().getObjResponse(1,"ok", ilhas ));

        }catch (Exception e){
            new EventsLogService(eventslogRepository).AddEventologs(null,"Excption in class '" + this.getClass().getName()
                    + "' method " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",e.getMessage(),null, null);
            return ResponseEntity.ok().body(new BaseResponse(0, e.getMessage(), null));
        }
    }
}
