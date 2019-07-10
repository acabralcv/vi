package com.api.controllers;

import com.library.helpers.BaseResponse;
import com.library.helpers.Helper;
import com.library.helpers.UtilsDate;
import com.library.models.Cela;
import com.library.models.Ala;
import com.library.repository.CelaRepository;
import com.library.repository.AlaRepository;
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

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@RestController
public class CelasController {

    @Autowired
    private Environment env;

    @Autowired
    private CelaRepository celaRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * @param id_ala
     * @param pageable
     * @return
     */
    @RequestMapping(value = {"api/celas"}, method = {RequestMethod.GET})
    public ResponseEntity actionDetails(@RequestParam(name = "id_ala", required = false) UUID id_ala, @PageableDefault(sort = {"nome"}, value = 10, page = 0) Pageable pageable) {



        try {


            if (id_ala != null) {

                Ala ala = new Ala();
                ala.setId(id_ala);

                ArrayList<Cela> celas = celaRepository.findByAla(ala);

                return ResponseEntity.ok().body(new BaseResponse().getObjResponse(1, "ok", celas));
            } else {

                Pageable pageableBuilded = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("nome")
                        .ascending()
                        .and(Sort.by("dateCreated").descending()));

                Page<Cela> celas = celaRepository.findByStatus(Helper.STATUS_ACTIVE, pageableBuilded);

                return ResponseEntity.ok().body(new BaseResponse().getObjResponse(1, "ok", celas));
            }

        } catch (Exception e) {
            new EventsLogService(userRepository).AddEventologs(null, "Excption in class '" + this.getClass().getName()
                    + "' method " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()", e.getMessage(), null, null);

            return ResponseEntity.ok().body(new BaseResponse(0, e.getMessage(), null));
        }
    }

    /**
     * @param id
     * @return
     */
    @RequestMapping(value = {"api/celas/details"}, method = {RequestMethod.GET})
    public ResponseEntity actionDetails(@RequestParam(name = "id") UUID id) {

        try {

            Optional<Cela> oCela = celaRepository.findById(id);

            return ResponseEntity.ok().body(new BaseResponse().getObjResponse(oCela != null ? 1 : 0, "ok", oCela.get()));

        } catch (Exception e) {
            new EventsLogService(userRepository).AddEventologs(null, "Excption in class '" + this.getClass().getName()
                    + "' method " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()", e.getMessage(), null, null);
            return ResponseEntity.ok().body(new BaseResponse(0, e.getMessage(), null));
        }
    }

    /**
     * @param cela
     * @return
     */
    @RequestMapping(value = {"api/celas/create"}, method = {RequestMethod.POST})
    public ResponseEntity actionCreate(@RequestBody Cela cela) {

        try {

            cela.setId(new Helper().getUUID());
            cela.setStatus(Helper.STATUS_ACTIVE);
            cela.setDateCreated(UtilsDate.getDateTime());

            Cela crestedCela = celaRepository.save(cela);

            return ResponseEntity.ok().body(new BaseResponse().getObjResponse(1, "ok", crestedCela));

        } catch (Exception e) {
            new EventsLogService(userRepository).AddEventologs(null, "Excption in class '" + this.getClass().getName()
                    + "' method " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()", e.getMessage(), null, null);
            return ResponseEntity.ok().body(new BaseResponse(0, e.getMessage(), null));
        }
    }

    /**
     * @param cela
     * @return
     */
    @RequestMapping(value = {"api/celas/update"}, method = {RequestMethod.POST})
    public ResponseEntity actionUpdate(@RequestBody Cela cela) {

        try {

            cela.setStatus(Helper.STATUS_ACTIVE);
            cela.setDateUpdated(UtilsDate.getDateTime());

            Cela crestedCela = celaRepository.save(cela);

            return ResponseEntity.ok().body(new BaseResponse().getObjResponse(1, "ok", crestedCela));

        } catch (Exception e) {
            new EventsLogService(userRepository).AddEventologs(null, "Excption in class '" + this.getClass().getName()
                    + "' method " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()", e.getMessage(), null, null);
            return ResponseEntity.ok().body(new BaseResponse(0, e.getMessage(), null));

        }
    }

    /**
     *
     * @param objCela
     * @return
     */
    @RequestMapping(value = {"api/celas/delete"}, method = {RequestMethod.POST})
    public ResponseEntity actionDelete(@RequestBody Cela objCela) {

        try{

            Cela oCela = celaRepository.findById(objCela.getId())
                    .orElseThrow(() -> new Exception(Cela.class.getName() + " not found with id '" + objCela.getId().toString() + "' on actionDelete"));

            oCela.setDateUpdated(UtilsDate.getDateTime());
            oCela.setStatus(Helper.STATUS_DISABLED);
            celaRepository.save(oCela);

            return ResponseEntity.ok().body(new BaseResponse().getObjResponse(1,"ok", oCela));

        }catch (Exception e){
            new EventsLogService(userRepository).AddEventologs(null,"Excption in class '" + this.getClass().getName()
                    + "' method " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",e.getMessage(),null, null);
            return ResponseEntity.ok().body(new BaseResponse(0, e.getMessage(), null));
        }
    }
}
