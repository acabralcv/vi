package com.api.controllers;

import com.library.helpers.BaseResponse;
import com.library.helpers.Helper;
import com.library.helpers.UtilsDate;
import com.library.models.User;
import com.library.models.User;
import com.library.models.UserProfiles;
import com.library.repository.UserRepository;
import com.library.repository.UserRepository;
import com.library.service.EventsLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
public class UsersController {

    @Autowired
    private UserRepository userRepository;

    /**
     *
     * @param id
     * @return
     */
    @RequestMapping(value = {"api/users/details"}, method = {RequestMethod.GET})
    public User actionDetails(@RequestParam(name = "id") UUID id) {

        return userRepository.findById(id)
                .map(user -> {
                    user.setUserProfiles(user.getUserProfiles());
                    return user;
                }).get();
    }

    /**
     *
     * @param model
     * @param pageable
     * @return
     */
    @RequestMapping(value = "api/users", method = RequestMethod.GET)
    public BaseResponse actionIndex(ModelMap model, Pageable pageable) {

        Page<User> users = userRepository.findByStatus(Helper.STATUS_ACTIVE,
                PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("dateCreated")
                        .descending()
                        .and(Sort.by("name").ascending())));

        return new BaseResponse().getObjResponse(1,"ok", users );
    }

    /**
     *
     * @param objUser
     * @return
     */
    @RequestMapping(value = {"api/users/create"}, method = {RequestMethod.POST})
    public BaseResponse actionCreate(@Valid @RequestBody User objUser) {

        try {

            if( userRepository.findByName(objUser.getUsername()).size() > 0)
                new Exception("Já existe utilizador com o mesmo 'username'.");


            if( userRepository.findByName(objUser.getEmail()).size() > 0)
                new Exception("Já existe utilizador com o mesmo 'email'.");

            objUser.setId(new Helper().getUUID());
            objUser.setAccessToken(new Helper().genToken(128)); //2 * 126 = 256
            objUser.setIsEditable(1);
            objUser.setDateCreated(UtilsDate.getDateTime());
            objUser.setStatus(Helper.STATUS_ACTIVE);
            userRepository.save(objUser);

            return new BaseResponse().getObjResponse(1,"ok", objUser );

        }catch(Exception e){
            new EventsLogService().AddEventologs(null,"Excption in " + this.getClass().getName(), e.getMessage(),null);
            return new BaseResponse().getObjResponse(0,e.getMessage(), null);
        }
    }


    @RequestMapping(value = {"api/users/add-profile"}, method = {RequestMethod.POST})
    public BaseResponse actionAddUserProfile(@Valid @RequestBody UserProfiles oUserProfile) {

        try {

            User oUser = userRepository.findById(oUserProfile.getUserId()).get();

            if( oUser == null)
                new Exception("Utilizador não encontrado.");


            oUser.getUserProfiles().add(new UserProfiles());

            oUserProfile.setId(new Helper().getUUID());
            oUserProfile.setDatedUpdated(UtilsDate.getDateTime());
            //userProfileRepository.save(oUserProfile);

            return new BaseResponse().getObjResponse(1,"ok", null );

        }catch(Exception e){
            new EventsLogService().AddEventologs(null,"Excption in " + this.getClass().getName(), e.getMessage(),null);
            return new BaseResponse().getObjResponse(0,e.getMessage(), null);
        }
    }

    /**
     *
     * @param objUser
     * @return
     */
    @RequestMapping(value = {"api/users/update"}, method = {RequestMethod.POST})
    public BaseResponse actionUpdate(@Valid @RequestBody User objUser) {

        try{

            User oUser = userRepository.findById(objUser.getId())
                    .orElseThrow(() -> new Exception(User.class.getName() + " not found with id '" + objUser.getId().toString() + "'"));


            oUser.setDatedUpdated(UtilsDate.getDateTime());
            oUser.setStatus(Helper.STATUS_ACTIVE);
            userRepository.save(oUser);
            return new BaseResponse().getObjResponse(1,"ok", objUser);

        }catch (Exception e){
            new EventsLogService().AddEventologs(null,"Excption in " + this.getClass().getName(), e.getMessage(),null);
            return new BaseResponse().getObjResponse(0,e.getMessage(), null);
        }
    }

    /**
     *
     * @param objUser
     * @return
     */
    @RequestMapping(value = {"api/users/delete"}, method = {RequestMethod.POST})
    public BaseResponse actionDelete(@Valid @RequestBody User objUser) {

        try{

            User oUser = userRepository.findById(objUser.getId())
                    .orElseThrow(() -> new Exception(User.class.getName() + " not found with id '" + objUser.getId().toString() + "' on actionDelete"));

            oUser.setDatedUpdated(UtilsDate.getDateTime());
            oUser.setStatus(Helper.STATUS_DISABLED);
            userRepository.save(oUser);
            return new BaseResponse().getObjResponse(1,"ok", objUser);

        }catch (Exception e){
            new EventsLogService().AddEventologs(null,"Excption in " + this.getClass().getName(), e.getMessage(),null);
            return new BaseResponse().getObjResponse(0,e.getMessage(), null);
        }
    }

}