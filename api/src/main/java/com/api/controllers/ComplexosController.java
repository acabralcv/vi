package com.api.controllers;

import com.library.helpers.BaseResponse;
import com.library.helpers.Helper;
import com.library.helpers.UtilsDate;
import com.library.models.Cadeia;
import com.library.models.Complexo;
import com.library.models.Profile;
import com.library.repository.ComplexoRepository;
import com.library.repository.UserRepository;
import com.library.service.EventsLogService;
import com.sun.xml.internal.fastinfoset.algorithm.UUIDEncodingAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@RestController
public class ComplexosController {

    @Autowired
    private Environment env;

    @Autowired
    private ComplexoRepository complexoRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * @param id_cadeia
     * @param pageable
     * @return
     */
    @RequestMapping(value = {"api/complexos"}, method = {RequestMethod.GET})
    public ResponseEntity actionDetails(@RequestParam(name = "id_cadeia", required = false) UUID id_cadeia, @PageableDefault(sort = {"nome"}, value = 10, page = 0) Pageable pageable) {

        try {

            if (id_cadeia != null) {

                Cadeia cadeia = new Cadeia();
                cadeia.setId(id_cadeia);

                ArrayList<Complexo> complexos = complexoRepository.findByCadeia(cadeia);

                return ResponseEntity.ok().body(new BaseResponse().getObjResponse(1, "ok", complexos));
            } else {

                Pageable pageableBuilded = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("nome")
                        .ascending()
                        .and(Sort.by("dateCreated").descending()));

                Page<Complexo> complexos = complexoRepository.findByStatus(Helper.STATUS_ACTIVE, pageableBuilded);

                return ResponseEntity.ok().body(new BaseResponse().getObjResponse(1, "ok", complexos));
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
    @RequestMapping(value = {"api/complexos/details"}, method = {RequestMethod.GET})
    public ResponseEntity actionDetails(@RequestParam(name = "id") UUID id) {

        try {

            Optional<Complexo> oComplexo = complexoRepository.findById(id);

            return ResponseEntity.ok().body(new BaseResponse().getObjResponse(oComplexo != null ? 1 : 0, "ok", oComplexo));

        } catch (Exception e) {
            new EventsLogService(userRepository).AddEventologs(null, "Excption in class '" + this.getClass().getName()
                    + "' method " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()", e.getMessage(), null, null);
            return ResponseEntity.ok().body(new BaseResponse(0, e.getMessage(), null));
        }
    }

    /**
     * @param complexo
     * @return
     */
    @RequestMapping(value = {"api/complexos/create"}, method = {RequestMethod.POST})
    public ResponseEntity actionCreate(@RequestBody Complexo complexo) {

        try {

            complexo.setId(new Helper().getUUID());
            complexo.setStatus(Helper.STATUS_ACTIVE);
            complexo.setDateCreated(UtilsDate.getDateTime());

            Complexo crestedComplexo = complexoRepository.save(complexo);

            return ResponseEntity.ok().body(new BaseResponse().getObjResponse(1, "ok", crestedComplexo));

        } catch (Exception e) {
            new EventsLogService(userRepository).AddEventologs(null, "Excption in class '" + this.getClass().getName()
                    + "' method " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()", e.getMessage(), null, null);
            return ResponseEntity.ok().body(new BaseResponse(0, e.getMessage(), null));
        }
    }

    /**
     * @param complexo
     * @return
     */
    @RequestMapping(value = {"api/complexos/update"}, method = {RequestMethod.POST})
    public ResponseEntity actionUpdate(@RequestBody Complexo complexo) {

        try {

            complexo.setStatus(Helper.STATUS_ACTIVE);
            complexo.setDateUpdated(UtilsDate.getDateTime());

            Complexo crestedComplexo = complexoRepository.save(complexo);

            return ResponseEntity.ok().body(new BaseResponse().getObjResponse(1, "ok", crestedComplexo));

        } catch (Exception e) {
            new EventsLogService(userRepository).AddEventologs(null, "Excption in class '" + this.getClass().getName()
                    + "' method " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()", e.getMessage(), null, null);
            return ResponseEntity.ok().body(new BaseResponse(0, e.getMessage(), null));

        }
    }
}
