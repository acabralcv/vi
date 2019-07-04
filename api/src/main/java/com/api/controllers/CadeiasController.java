package com.api.controllers;

import com.library.helpers.BaseResponse;
import com.library.helpers.Helper;
import com.library.helpers.UtilsDate;
import com.library.models.Pais;
import com.library.models.Cadeia;
import com.library.models.Recluso;
import com.library.repository.CadeiaRepository;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@RestController
public class CadeiasController {

    @Autowired
    private Environment env;

    @Autowired
    private CadeiaRepository cadeiaRepository;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = {"api/cadeias"}, method = {RequestMethod.GET})
    public ResponseEntity actionDetails(@PageableDefault(sort = {"nome"}, value = 10, page = 0) Pageable pageable) {

        try  {

            Pageable pageableBuilded = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("nome")
                    .ascending()
                    .and(Sort.by("dateCreated").descending()));

            Page<Cadeia> cadeias = cadeiaRepository.findByStatus(Helper.STATUS_ACTIVE, pageableBuilded);

            return ResponseEntity.ok().body(new BaseResponse().getObjResponse(1,"ok", cadeias ));

        }catch (Exception e){
            new EventsLogService(userRepository).AddEventologs(null,"Excption in class '" + this.getClass().getName()
                    + "' method " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",e.getMessage(),null, null);

            return ResponseEntity.ok().body(new BaseResponse(0, e.getMessage(), null));
        }
    }

    /**
     *
     * @param id
     * @return
     */
    @RequestMapping(value = {"api/cadeias/details"}, method = {RequestMethod.GET})
    public ResponseEntity actionDetails(@RequestParam(name = "id") UUID id) {

        try{

            Optional<Cadeia> oCadeia  = cadeiaRepository.findById(id);

            return ResponseEntity.ok().body(new BaseResponse().getObjResponse(oCadeia != null ? 1 : 0, "ok", oCadeia));

        }catch (Exception e){
            new EventsLogService(userRepository).AddEventologs(null,"Excption in class '" + this.getClass().getName()
                    + "' method " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",e.getMessage(),null, null);
            return ResponseEntity.ok().body(new BaseResponse(0, e.getMessage(), null));
        }
    }


    /**
     *
     * @param cadeia
     * @return
     */
    @RequestMapping(value = {"api/cadeias/create"}, method = {RequestMethod.POST})
    public ResponseEntity actionCreate(@RequestBody Cadeia cadeia) {

        try {

            cadeia.setId(new Helper().getUUID());
            cadeia.setStatus(Helper.STATUS_ACTIVE);
            cadeia.setDateCreated(UtilsDate.getDateTime());

            Cadeia crestedCadeia = cadeiaRepository.save(cadeia);

            return ResponseEntity.ok().body(new BaseResponse().getObjResponse(1,"ok", crestedCadeia ));

        }catch (Exception e){
            new EventsLogService(userRepository).AddEventologs(null,"Excption in class '" + this.getClass().getName()
                    + "' method " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",e.getMessage(),null, null);
            return ResponseEntity.ok().body(new BaseResponse(0, e.getMessage(), null));
        }
    }


    /**
     *
     * @param cadeia
     * @return
     */
    @RequestMapping(value = {"api/cadeias/update"}, method = {RequestMethod.POST})
    public ResponseEntity actionUpdate(@RequestBody Cadeia cadeia) {

        try {

            cadeia.setStatus(Helper.STATUS_ACTIVE);
            cadeia.setDateUpdated(UtilsDate.getDateTime());

            Cadeia crestedCadeia = cadeiaRepository.save(cadeia);

            return ResponseEntity.ok().body(new BaseResponse().getObjResponse(1,"ok", crestedCadeia ));

        }catch (Exception e){
            new EventsLogService(userRepository).AddEventologs(null,"Excption in class '" + this.getClass().getName()
                    + "' method " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",e.getMessage(),null, null);
            return ResponseEntity.ok().body(new BaseResponse(0, e.getMessage(), null));

        }
    }
}