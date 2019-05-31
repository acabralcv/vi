package com.api.controllers;

import com.library.models.Pais;
import com.library.helpers.Helper;
import com.library.helpers.BaseResponse;
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

    @RequestMapping(value = {"api/paises"}, method = {RequestMethod.GET})
    public ResponseEntity actionDetails(@PageableDefault(sort = {"name"}, value = 10, page = 0) Pageable pageable) {

        Pageable pageableBuilded = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("nome")
                .ascending()
                .and(Sort.by("dateCreated").descending()));

        Page<Pais> paises = paisRepository.findByStatus(Helper.STATUS_ACTIVE, pageableBuilded);

        return ResponseEntity.ok().body(new BaseResponse().getObjResponse(1,"ok", paises ));
    }
}
