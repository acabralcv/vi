package com.api.controllers;

import com.library.helpers.BaseResponse;
import com.library.helpers.Helper;
import com.library.helpers.UtilsDate;
import com.library.models.Complexo;
import com.library.models.Setor;
import com.library.repository.SetorRepository;
import com.library.repository.UserRepository;
import com.library.service.EventsLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class SetoresController {



    @Autowired
    private Environment env;

    @Autowired
    private SetorRepository setorRepository;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = {"api/setores"}, method = {RequestMethod.GET})
    public ResponseEntity actionDetails(@PageableDefault(sort = {"nome"}, value = 10, page = 0) Pageable pageable) {

        try  {

            Pageable pageableBuilded = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("nome")
                    .ascending()
                    .and(Sort.by("dateCreated").descending()));

            Page<Setor> setores = setorRepository.findByStatus(Helper.STATUS_ACTIVE, pageableBuilded);

            return ResponseEntity.ok().body(new BaseResponse().getObjResponse(1,"ok", setores ));

        }catch (Exception e){
            new EventsLogService(userRepository).AddEventologs(null,"Excption in class '" + this.getClass().getName()
                    + "' method " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",e.getMessage(),null, null);

            return ResponseEntity.ok().body(new BaseResponse(0, e.getMessage(), null));
        }
    }

    @RequestMapping(value = {"api/setores/create"}, method = {RequestMethod.POST})
    public ResponseEntity actionCreate(@RequestBody Setor setor) {

        try {

            setor.setId(new Helper().getUUID());
            setor.setStatus(Helper.STATUS_ACTIVE);
            setor.setDateCreated(UtilsDate.getDateTime());

            Setor crestedSetor = setorRepository.save(setor);

            return ResponseEntity.ok().body(new BaseResponse().getObjResponse(1,"ok", crestedSetor ));

        }catch (Exception e){
            new EventsLogService(userRepository).AddEventologs(null,"Excption in class '" + this.getClass().getName()
                    + "' method " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",e.getMessage(),null, null);
            return ResponseEntity.ok().body(new BaseResponse(0, e.getMessage(), null));
        }
    }


}
