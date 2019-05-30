package com.api.controllers;

import com.library.helpers.BaseResponse;
import com.library.helpers.Helper;
import com.library.helpers.UtilsDate;
import com.library.models.*;
import com.library.models.User;
import com.library.repository.*;
import com.library.repository.UserRepository;
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
import java.util.Set;
import java.util.UUID;

@RestController
public class UsersController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserProfilesRepository userProfilesRepository;

    /**
     *
     * @param id
     * @return
     */
    @RequestMapping(value = {"api/users/details"}, method = {RequestMethod.GET})
    public ResponseEntity actionDetails(@RequestParam(name = "id") UUID id) {

        Optional<User> oUser = userRepository.findById(id)
                .map(user -> {
                    user.setProfileImage(user.getProfileImage());
                    return user;
                });

        if(oUser != null)
            return ResponseEntity.ok().body(new BaseResponse(1, "ok", oUser.get()));
        else
            return ResponseEntity.ok().body(new BaseResponse(0, "ok", null));

    }

    /**
     *
     * @param user_id
     * @return
     */
    @RequestMapping(value = {"api/users/users-profiles"}, method = {RequestMethod.GET})
    public BaseResponse actionUserProfiles(@RequestParam(name = "user_id") UUID user_id) {
        User user = new User();
        user.setId(user_id);

        Set<Profile> oList = profileRepository.findProfileByStatus(1,user);

        return new BaseResponse(1, "ok", oList);
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
    public ResponseEntity actionCreate(@Valid @RequestBody User objUser) {

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
            User crestedUser = userRepository.save(objUser);

            return ResponseEntity.ok().body(new BaseResponse().getObjResponse(1,"ok", crestedUser ));

        }catch(Exception e){
            new EventsLogService().AddEventologs(null,"Excption in " + this.getClass().getName(), e.getMessage(),null);
            return ResponseEntity.ok().body(new BaseResponse().getObjResponse(0,e.getMessage(), null));
        }
    }

    /**
     *
     * @param oUserProfile
     * @return
     */
    @RequestMapping(value = {"api/users/add-profile"}, method = {RequestMethod.POST})
    public BaseResponse actionAddUserProfile(@Valid @RequestBody UserProfiles oUserProfile) {

        try {

            User oUser = userRepository.findById(oUserProfile.getUserId()).get();
            Profile oProfile = profileRepository.findById(oUserProfile.getProfileId()).get();

            if( oUser == null || oProfile == null)
                new Exception("Utilizador ou perfil não encontrado.");

            Boolean isNew = true;
            Set<UserProfiles> listUserProfiles = oUser.getUserProfiles();

            //check if user allread has the target profile
            for (UserProfiles auxUserProfile : listUserProfiles)
                if(auxUserProfile.getProfile().getId() == oUserProfile.getProfileId()){ isNew = false; break; }

            if(isNew == true){
                oUserProfile.setId(new Helper().getUUID());
                oUserProfile.setDateCreated(UtilsDate.getDateTime());
                oUserProfile.setStatus(Helper.STATUS_ACTIVE);
                oUserProfile.setUser(oUser);
                oUserProfile.setProfile(oProfile);
                oUserProfile.setForceAccessCheck(Helper.STATUS_ACTIVE);
                oUserProfile.setIsEditable(Helper.STATUS_ACTIVE);

                //update user profiles
                UserProfiles savedUserProfile = userProfilesRepository.save(oUserProfile);

                //add to the user profiles
                listUserProfiles.add(savedUserProfile);

                oUser.setUserProfiles(listUserProfiles);
            }
            //update user
            User savedUser = userRepository.save(oUser);

            return new BaseResponse().getObjResponse(1,"ok", savedUser );

        }catch(Exception e){
            new EventsLogService().AddEventologs(null,"Excption in " + this.getClass().getName(), e.getMessage(),null);
            return new BaseResponse().getObjResponse(0,e.getMessage(), null);
        }
    }


    @RequestMapping(value = {"api/users/add-profile-image"}, method = {RequestMethod.POST})
    public BaseResponse actionAddUserImage(@RequestBody User userPosted) {

        try {

            User oUser = userRepository.findById(userPosted.getId()).get();
            Image oImage = imageRepository.findById(userPosted.getProfileImageId()).get();

            if( oUser == null || oImage == null)
                new Exception("Utilizador não encontrado.");

            oUser.setProfileImage(oImage);

            //update user
            User savedUser = userRepository.save(oUser);

            return new BaseResponse().getObjResponse(1,"ok", savedUser );

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


            oUser.setDateUpdated(UtilsDate.getDateTime());
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

            oUser.setDateUpdated(UtilsDate.getDateTime());
            oUser.setStatus(Helper.STATUS_DISABLED);
            userRepository.save(oUser);
            return new BaseResponse().getObjResponse(1,"ok", objUser);

        }catch (Exception e){
            new EventsLogService().AddEventologs(null,"Excption in " + this.getClass().getName(), e.getMessage(),null);
            return new BaseResponse().getObjResponse(0,e.getMessage(), null);
        }
    }

}