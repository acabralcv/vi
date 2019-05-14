package com.api.controllers;

import com.library.helpers.BaseResponse;
import com.library.helpers.Helper;
import com.library.helpers.UtilsDate;
import com.library.models.Profile;
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

    @RequestMapping(value = "api/profiles", method = RequestMethod.GET)
    public Page<Profile> actionIndex(ModelMap model, Pageable pageable) {
        Pageable firstPageWithTwoElements = PageRequest.of(0, 5);

        Page<Profile> profiles = profileRepository.findByStatus(Helper.STATUS_ACTIVE,
                PageRequest.of(0, 3, Sort.by("dateCreated")
                        .descending()
                        .and(Sort.by("name").ascending())));

        int totalPages = profiles.getTotalPages();
        List<Integer> pageNumbers = null;

        if (totalPages > 0) {
            pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
        }

        return profiles;
    }


    @RequestMapping(value = {"api/profiles/create"}, method = {RequestMethod.POST})
    public BaseResponse actionCreate(@Valid @RequestBody Profile objProfile) {

        try {

            if( profileRepository.findByName(objProfile.getName()).size() > 0)
                new Exception("Já existe um 'Perfil' com o mesmo nome.");

            objProfile.setId(new Helper().getUUID());
            objProfile.setDatedUpdated(UtilsDate.getDateTime());
            profileRepository.save(objProfile);

            return new BaseResponse().getResponse(1,"ok", objProfile);

        }catch(Exception e){
            new EventsLogService().AddEventologs(null,"Excption in " + this.getClass().getName(), e.getMessage(),null);
            return new BaseResponse().getResponse(0,e.getMessage(), null);
        }
    }


}