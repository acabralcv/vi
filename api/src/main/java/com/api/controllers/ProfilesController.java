package com.api.controllers;

import com.library.helpers.BaseResponse;
import com.library.helpers.Helper;
import com.library.helpers.UtilsDate;
import com.library.models.Profile;
import com.library.models.UserProfiles;
import com.library.models.WfProcess;
import com.library.repository.ProfileRepository;
import com.library.service.EventsLogService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
public class ProfilesController {

    @Autowired
    private ProfileRepository profileRepository;

//    @Autowired
//    private UserProfileRepository userProfileRepository;

    /**
     *
     * @param id
     * @return
     */
    @RequestMapping(value = {"api/profiles/details"}, method = {RequestMethod.GET})
    public Profile actionDetails(@RequestParam(name = "id") UUID id) {

        return profileRepository.findById(id).get();
    }


    /**
     *
     * @param model
     * @param pageable
     * @return
     */
    @RequestMapping(value = "api/profiles", method = RequestMethod.GET)
    public BaseResponse actionIndex(ModelMap model, Pageable pageable) {

        Page<Profile> profiles = profileRepository.findByStatus(Helper.STATUS_ACTIVE,
                PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("dateCreated")
                        .descending()
                        .and(Sort.by("name").ascending())));

        return new BaseResponse().getObjResponse(1,"ok", profiles );
    }

    /**
     *
     * @param objProfile
     * @return
     */
    @RequestMapping(value = {"api/profiles/create"}, method = {RequestMethod.POST})
    public BaseResponse actionCreate(@Valid @RequestBody Profile objProfile) {

        try {

            if( profileRepository.findByName(objProfile.getName()).size() > 0)
                new Exception("Já existe um 'Perfil' com o mesmo nome.");

            objProfile.setId(new Helper().getUUID());
            objProfile.setDateUpdated(UtilsDate.getDateTime());
            profileRepository.save(objProfile);

            return new BaseResponse().getObjResponse(1,"ok", objProfile );

        }catch(Exception e){
            new EventsLogService().AddEventologs(null,"Excption in " + this.getClass().getName(), e.getMessage(),null);
            return new BaseResponse().getObjResponse(0,e.getMessage(), null);
        }
    }


    /**
     *
     * @param objProfile
     * @return
     */
    @RequestMapping(value = {"api/profiles/update"}, method = {RequestMethod.POST})
    public BaseResponse actionUpdate(@Valid @RequestBody Profile objProfile) {

        try{

            Profile oProfile = profileRepository.findById(objProfile.getId())
                    .orElseThrow(() -> new Exception(Profile.class.getName() + " not found with id '" + objProfile.getId().toString() + "'"));


            oProfile.setDateUpdated(UtilsDate.getDateTime());
            oProfile.setStatus(Helper.STATUS_ACTIVE);
            profileRepository.save(oProfile);
            return new BaseResponse().getObjResponse(1,"ok", objProfile);

        }catch (Exception e){
            new EventsLogService().AddEventologs(null,"Excption in " + this.getClass().getName(), e.getMessage(),null);
            return new BaseResponse().getObjResponse(0,e.getMessage(), null);
        }
    }

    /**
     *
     * @param objProfile
     * @return
     */
    @RequestMapping(value = {"api/profiles/delete"}, method = {RequestMethod.POST})
    public BaseResponse actionDelete(@Valid @RequestBody Profile objProfile) {

        try{

            Profile oProfile = profileRepository.findById(objProfile.getId())
                    .orElseThrow(() -> new Exception(Profile.class.getName() + " not found with id '" + objProfile.getId().toString() + "' on actionDelete"));

            oProfile.setDateUpdated(UtilsDate.getDateTime());
            oProfile.setStatus(Helper.STATUS_DISABLED);
            profileRepository.save(oProfile);
            return new BaseResponse().getObjResponse(1,"ok", objProfile);

        }catch (Exception e){
            new EventsLogService().AddEventologs(null,"Excption in " + this.getClass().getName(), e.getMessage(),null);
            return new BaseResponse().getObjResponse(0,e.getMessage(), null);
        }
    }
}