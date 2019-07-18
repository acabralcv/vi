package com.api.controllers;

import com.library.helpers.BaseResponse;
import com.library.helpers.Helper;
import com.library.helpers.UtilsDate;
import com.library.models.Image;
import com.library.models.Recluso;
import com.library.models.ReclusoCela;
import com.library.models.User;
import com.library.repository.*;
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
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;


@RestController
public class ReclusosController {

    @Autowired
    private ReclusoRepository reclusoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ReclusoCelaRepository reclusoCelaRepository;

    /** get recludo details
     * @param id
     * @return
     */
    @RequestMapping(value = {"api/reclusos/details"}, method = {RequestMethod.GET})
    public ResponseEntity actionDetails(@RequestParam(name = "id") UUID id) {

        try{

            Optional<Recluso> oRecluso  = reclusoRepository.findById(id);

            return ResponseEntity.ok().body(new BaseResponse().getObjResponse(oRecluso != null ? 1 : 0, "ok", oRecluso.get()));

        }catch (Exception e){
            new EventsLogService(userRepository).AddEventologs(null,"Excption in class '" + this.getClass().getName()
                    + "' method " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",e.getMessage(),null, null);
            return ResponseEntity.ok().body(new BaseResponse(0, e.getMessage(), null));
        }
    }


    /**
     * get reclusos
     * @param model
     * @param pageable
     * @return
     */
    @RequestMapping(value = "api/reclusos", method = RequestMethod.GET)
    public ResponseEntity actionIndex(ModelMap model, Pageable pageable) {

        try{

            Page<Recluso> reclusos = reclusoRepository.findByStatus(Helper.STATUS_ACTIVE,
                    PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("dateCreated")
                            .descending()
                            .and(Sort.by("nome").ascending())));

            return ResponseEntity.ok().body(new BaseResponse().getObjResponse(1,"ok", reclusos ));

        }catch (Exception e){
            new EventsLogService(userRepository).AddEventologs(null,"Excption in class '" + this.getClass().getName()
                    + "' method " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",e.getMessage(),null, null);
            return ResponseEntity.ok().body(new BaseResponse(0, e.getMessage(), null));
        }
    }


    @RequestMapping(value = "api/recluso-celas", method = RequestMethod.GET)
    public ResponseEntity actionReclusoCelas(@RequestParam(name = "recluso_id") UUID recluso_id) {

        try{

            Recluso recluso = new Recluso();
            recluso.setId(recluso_id);

            ArrayList<ReclusoCela> reclusoCelas = reclusoCelaRepository.findByRecluso(recluso);

            return ResponseEntity.ok().body(new BaseResponse().getObjResponse(1,"ok", reclusoCelas ));

        }catch (Exception e){
            new EventsLogService(userRepository).AddEventologs(null,"Excption in class '" + this.getClass().getName()
                    + "' method " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",e.getMessage(),null, null);
            return ResponseEntity.ok().body(new BaseResponse(0, e.getMessage(), null));
        }
    }

    /**
     * create a new recluso
     * @param objRecluso
     * @return
     */
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

            /**
             * create a log for ELK
             */
            if(crestedRecluso != null)
                new EventsLogService(userRepository).AddEventologs(Helper.LogsType.LOGS_RECLUSO_CREATED.toString(),
                        "Novo recluso registrado. ", crestedRecluso.getNome(),null, null);

            return ResponseEntity.ok().body(new BaseResponse().getObjResponse(1,"ok", crestedRecluso ));

        }catch (Exception e){
            new EventsLogService(userRepository).AddEventologs(null,"Excption in class '" + this.getClass().getName()
                    + "' method " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",e.getMessage(),null, null);
            return ResponseEntity.ok().body(new BaseResponse(0, e.getMessage(), null));
        }
    }


    /**
     * Assign recluso to a "cela"
     * @param objReclusoCela
     * @return
     */
    @RequestMapping(value = {"api/reclusos/add-recluso-cela"}, method = {RequestMethod.POST})
    public ResponseEntity actionAddReclusoCela(@RequestBody ReclusoCela objReclusoCela) {

        try {

            //ReclusoCela oReclusoCela = new ReclusoCela();
            Optional<ReclusoCela> reclusoCela = reclusoCelaRepository.findByReclusoAndCela(objReclusoCela.getRecluso(), objReclusoCela.getCela());

            if(reclusoCela.isPresent()){
                objReclusoCela = reclusoCela.get();
                objReclusoCela.setDateUpdated(UtilsDate.getDateTime());
                objReclusoCela.setStatus(Helper.STATUS_ACTIVE);
            }else{
                objReclusoCela.setId(new Helper().getUUID());
                objReclusoCela.setDateCreated(UtilsDate.getDateTime());
                objReclusoCela.setStatus(Helper.STATUS_ACTIVE);
            }

            objReclusoCela.setDateRemocao(null);
            ReclusoCela crestedReclusoCela = reclusoCelaRepository.save(objReclusoCela);


            ArrayList<ReclusoCela> reclusoCelas = reclusoCelaRepository.findByRecluso(objReclusoCela.getRecluso());

            //vamos remover o recluso de outras celas caso ja lá estava
            for (ReclusoCela auxReclusoCela : reclusoCelas) {
                if(auxReclusoCela.getId() != objReclusoCela.getId()){
                    auxReclusoCela.setStatus(Helper.STATUS_DISABLED);
                    auxReclusoCela.setDateUpdated(UtilsDate.getDateTime());
                    auxReclusoCela.setDateRemocao(UtilsDate.getDateTime());
                    reclusoCelaRepository.save(auxReclusoCela);
                }
            }


            return ResponseEntity.ok().body(new BaseResponse().getObjResponse(1,"ok", objReclusoCela ));

        }catch (Exception e){
            new EventsLogService(userRepository).AddEventologs(null,"Excption in class '" + this.getClass().getName()
                    + "' method " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",e.getMessage(),null, null);
            return ResponseEntity.ok().body(new BaseResponse(0, e.getMessage(), null));
        }
    }


    /**
     *
     * @param reclusoPosted
     * @return
     */
    @RequestMapping(value = {"api/reclusos/add-profile-image"}, method = {RequestMethod.POST})
    public ResponseEntity actionAddReclusoImage(@RequestBody Recluso reclusoPosted) {

        try {

            Optional<Recluso> oRecluso = (Optional) reclusoRepository.findById(reclusoPosted.getId());

            Optional<Image> oImage = imageRepository.findById(reclusoPosted.getProfileImage().getId());

            if( !oRecluso.isPresent())
                throw new Exception("Recluso não encontrado.");

            if( !oImage.isPresent())
                throw new Exception("Imagem não encontrado.");

            Recluso objRecluso = oRecluso.get();
            objRecluso.setProfileImage(oImage.get());

            //update user
            Recluso savedRecluso = reclusoRepository.save(objRecluso);

            return ResponseEntity.ok().body(new BaseResponse().getObjResponse(1,"ok", savedRecluso ));

        }catch (Exception e){
            new EventsLogService(userRepository).AddEventologs(Helper.LogsType.LOGS_ERROR.toString(),"Excption in class '" + this.getClass().getName()
                    + "' method " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",e.getMessage(),null, null);
            return ResponseEntity.ok().body(new BaseResponse(0, e.getMessage(), null));
        }
    }

    /**
     *
     * @param objRecluso
     * @return
     */
    @RequestMapping(value = {"api/reclusos/delete"}, method = {RequestMethod.POST})
    public ResponseEntity actionDelete(@RequestBody Recluso objRecluso) {

        try{

            Recluso oRecluso = reclusoRepository.findById(objRecluso.getId())
                    .orElseThrow(() -> new Exception(Recluso.class.getName() + " not found with id '" + objRecluso.getId().toString() + "' on actionDelete"));

            oRecluso.setDateUpdated(UtilsDate.getDateTime());
            oRecluso.setStatus(Helper.STATUS_DISABLED);
            reclusoRepository.save(oRecluso);

            return ResponseEntity.ok().body(new BaseResponse().getObjResponse(1,"ok", objRecluso));

        }catch (Exception e){
            new EventsLogService(userRepository).AddEventologs(null,"Excption in class '" + this.getClass().getName()
                    + "' method " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",e.getMessage(),null, null);
            return ResponseEntity.ok().body(new BaseResponse(0, e.getMessage(), null));
        }
    }
}