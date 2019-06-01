package com.api.controllers;

import com.library.helpers.BaseResponse;
import com.library.helpers.Helper;
import com.library.helpers.UtilsDate;
import com.library.models.Recluso;
import com.library.models.User;
import com.library.repository.ReclusoRepository;
import com.library.service.EventsLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;


@RestController
public class ReclusosController {

    @Autowired
    private ReclusoRepository reclusoRepository;

    /**
     * @param id
     * @return
     */
    @RequestMapping(value = {"api/reclusos/details"}, method = {RequestMethod.GET})
    public ResponseEntity actionDetails(@RequestParam(name = "id") UUID id) {

        Optional<Recluso> oRecluso = (Optional) reclusoRepository.findById(id);

        return ResponseEntity.ok().body(new BaseResponse().getObjResponse(oRecluso != null ? 1 : 0, "ok", oRecluso));
    }


    /**
     *
     * @param model
     * @param pageable
     * @return
     */
    @RequestMapping(value = "api/reclusos", method = RequestMethod.GET)
    public ResponseEntity actionIndex(ModelMap model, Pageable pageable) {

        Page<Recluso> reclusos = reclusoRepository.findByStatus(Helper.STATUS_ACTIVE,
                PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("dateCreated")
                        .descending()
                        .and(Sort.by("nome").ascending())));

        return ResponseEntity.ok().body(new BaseResponse().getObjResponse(1,"ok", reclusos ));
    }

    @RequestMapping(value = {"api/reclusos/create"}, method = {RequestMethod.POST})
    public ResponseEntity actionCreate(@Valid @RequestBody Recluso objRecluso) {

        try {

            Long max = reclusoRepository.count();

            objRecluso.setId(new Helper().getUUID());
            objRecluso.setDataNascimento(UtilsDate.getDateTime());
            objRecluso.setDateCreated(UtilsDate.getDateTime());
            objRecluso.setStatus(Helper.STATUS_ACTIVE);
            objRecluso.setNumRecluso(max.toString());
            objRecluso.setNacionalidade(objRecluso.getNacionalidade());

            Recluso crestedRecluso = reclusoRepository.save(objRecluso);

            return ResponseEntity.ok().body(new BaseResponse().getObjResponse(1,"ok", crestedRecluso ));

        }catch(Exception e){
            new EventsLogService().AddEventologs(null,"Excption in " + this.getClass().getName(), e.getMessage(),null);
            return ResponseEntity.ok().body(new BaseResponse().getObjResponse(0,e.getMessage(), null));
        }
    }

}
