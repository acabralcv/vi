package com.api.controllers;

import com.library.helpers.BaseResponse;
import com.library.helpers.Helper;
import com.library.helpers.UtilsDate;
import com.library.models.Profile;
import com.library.repository.ProfileRepository;
import com.library.repository.UserRepository;
import com.library.service.EventsLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
public class ProfilesController {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     *
     * @param id
     * @return
     */
    @RequestMapping(value = {"api/profiles/details"}, method = {RequestMethod.GET})
    public ResponseEntity actionDetails(@RequestParam(name = "id") UUID id) {

         try{

            Optional<Profile> oProfile = (Optional) profileRepository.findById(id);

            return ResponseEntity.ok().body(new BaseResponse().getObjResponse(oProfile != null ? 1 : 0,"ok", oProfile));

        }catch (Exception e){
            new EventsLogService(userRepository).AddEventologs(null,"Excption in class '" + this.getClass().getName()
                    + "' method " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",e.getMessage(),null, null);
            return ResponseEntity.ok().body(new BaseResponse(0, e.getMessage(), null));
        }
    }


    /**
     *
     * @param model
     * @param pageable
     * @return
     */
    @RequestMapping(value = "api/profiles", method = RequestMethod.GET)
    public ResponseEntity actionIndex(ModelMap model, @PageableDefault(page = 0, size = 10) Pageable pageable) {

        try{

            Page<Profile> profiles = profileRepository.findByStatus(Helper.STATUS_ACTIVE,
                    PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("name").descending()) );

            return ResponseEntity.ok().body(new BaseResponse().getObjResponse(1,"ok", profiles ));

        }catch (Exception e){
            new EventsLogService(userRepository).AddEventologs(null,"Excption in class '" + this.getClass().getName()
                    + "' method " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",e.getMessage(),null, null);
            return ResponseEntity.ok().body(new BaseResponse(0, e.getMessage(), null));
        }
    }

    /**
     *
     * @param objProfile
     * @return
     */
    @RequestMapping(value = {"api/profiles/create"}, method = {RequestMethod.POST})
    public ResponseEntity actionCreate(@Valid @RequestBody Profile objProfile) {

        try {

            if( profileRepository.findByName(objProfile.getName()).size() > 0)
                throw new Exception("Já existe um 'Perfil' com o mesmo nome.");

            objProfile.setId(new Helper().getUUID());
            //objProfile.setStatus(Helper.STATUS_ACTIVE);
            objProfile.setDateCreated(UtilsDate.getDateTime());
            profileRepository.save(objProfile);

            return ResponseEntity.ok().body(new BaseResponse().getObjResponse(1,"ok", objProfile ));

        }catch (Exception e){
            new EventsLogService(userRepository).AddEventologs(null,"Excption in class '" + this.getClass().getName()
                    + "' method " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",e.getMessage(),null, null);
            return ResponseEntity.ok().body(new BaseResponse(0, e.getMessage(), null));
        }
    }


    /**
     *
     * @param objProfile
     * @return
     */
    @RequestMapping(value = {"api/profiles/update"}, method = {RequestMethod.POST})
    public ResponseEntity actionUpdate(@Valid @RequestBody Profile objProfile) {

        try{

            Profile oProfile = profileRepository.findById(objProfile.getId())
                    .orElseThrow(() -> new Exception(Profile.class.getName() + " not found with id '" + objProfile.getId().toString() + "'"));

            oProfile.setDateUpdated(UtilsDate.getDateTime());
            oProfile.setName(objProfile.getName());
            oProfile.setStatus(Helper.STATUS_ACTIVE);
            profileRepository.save(objProfile);
            return ResponseEntity.ok().body(new BaseResponse().getObjResponse(1,"ok", objProfile));

        }catch (Exception e){
            new EventsLogService(userRepository).AddEventologs(null,"Excption in class '" + this.getClass().getName()
                    + "' method " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",e.getMessage(),null, null);
            return ResponseEntity.ok().body(new BaseResponse(0, e.getMessage(), null));
        }
    }

    /**
     *
     * @param objProfile
     * @return
     */
    @RequestMapping(value = {"api/profiles/delete"}, method = {RequestMethod.POST})
    public ResponseEntity actionDelete(@RequestBody Profile objProfile) {

        try{

            Profile oProfile = profileRepository.findById(objProfile.getId())
                    .orElseThrow(() -> new Exception(Profile.class.getName() + " not found with id '" + objProfile.getId().toString() + "' on actionDelete"));

            oProfile.setDateUpdated(UtilsDate.getDateTime());
            oProfile.setStatus(Helper.STATUS_DISABLED);
            profileRepository.save(oProfile);

            return ResponseEntity.ok().body(new BaseResponse().getObjResponse(1,"ok", objProfile));

        }catch (Exception e){
            new EventsLogService(userRepository).AddEventologs(null,"Excption in class '" + this.getClass().getName()
                    + "' method " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",e.getMessage(),null, null);
            return ResponseEntity.ok().body(new BaseResponse(0, e.getMessage(), null));
        }
    }
}