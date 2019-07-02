package com.api.controllers;

import com.library.helpers.BaseResponse;
import com.library.helpers.Helper;
import com.library.helpers.UtilsDate;
import com.library.models.Setor;
import com.library.models.Ala;
import com.library.repository.AlaRepository;
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
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@RestController
public class AlasController {

    @Autowired
    private Environment env;

    @Autowired
    private AlaRepository alaRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * @param id_setor
     * @param pageable
     * @return
     */
    @RequestMapping(value = {"api/alas"}, method = {RequestMethod.GET})
    public ResponseEntity actionDetails(@RequestParam(name = "id_setor", required = false) UUID id_setor, @PageableDefault(sort = {"nome"}, value = 10, page = 0) Pageable pageable) {

        try {

            if (id_setor != null) {

               Setor setor = new Setor();
                setor.setId(id_setor);

                ArrayList<Ala> alas = alaRepository.findBySetor(setor);

                return ResponseEntity.ok().body(new BaseResponse().getObjResponse(1, "ok", alas));
            } else {

                Pageable pageableBuilded = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("nome")
                        .ascending()
                        .and(Sort.by("dateCreated").descending()));

                Page<Ala> alas = alaRepository.findByStatus(Helper.STATUS_ACTIVE, pageableBuilded);

                return ResponseEntity.ok().body(new BaseResponse().getObjResponse(1, "ok", alas));
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
    @RequestMapping(value = {"api/alas/details"}, method = {RequestMethod.GET})
    public ResponseEntity actionDetails(@RequestParam(name = "id") UUID id) {

        try {

            Optional<Ala> oAla = alaRepository.findById(id);

            return ResponseEntity.ok().body(new BaseResponse().getObjResponse(oAla != null ? 1 : 0, "ok", oAla));

        } catch (Exception e) {
            new EventsLogService(userRepository).AddEventologs(null, "Excption in class '" + this.getClass().getName()
                    + "' method " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()", e.getMessage(), null, null);
            return ResponseEntity.ok().body(new BaseResponse(0, e.getMessage(), null));
        }
    }

    /**
     * @param ala
     * @return
     */
    @RequestMapping(value = {"api/alas/create"}, method = {RequestMethod.POST})
    public ResponseEntity actionCreate(@RequestBody Ala ala) {

        try {

            ala.setId(new Helper().getUUID());
            ala.setStatus(Helper.STATUS_ACTIVE);
            ala.setDateCreated(UtilsDate.getDateTime());

            Ala crestedAla = alaRepository.save(ala);

            return ResponseEntity.ok().body(new BaseResponse().getObjResponse(1, "ok", crestedAla));

        } catch (Exception e) {
            new EventsLogService(userRepository).AddEventologs(null, "Excption in class '" + this.getClass().getName()
                    + "' method " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()", e.getMessage(), null, null);
            return ResponseEntity.ok().body(new BaseResponse(0, e.getMessage(), null));
        }
    }

    /**
     * @param ala
     * @return
     */
    @RequestMapping(value = {"api/alas/update"}, method = {RequestMethod.POST})
    public ResponseEntity actionUpdate(@RequestBody Ala ala) {

        try {

            ala.setStatus(Helper.STATUS_ACTIVE);
            ala.setDateUpdated(UtilsDate.getDateTime());

            Ala crestedAla = alaRepository.save(ala);

            return ResponseEntity.ok().body(new BaseResponse().getObjResponse(1, "ok", crestedAla));

        } catch (Exception e) {
            new EventsLogService(userRepository).AddEventologs(null, "Excption in class '" + this.getClass().getName()
                    + "' method " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()", e.getMessage(), null, null);
            return ResponseEntity.ok().body(new BaseResponse(0, e.getMessage(), null));



        }
    }
}
