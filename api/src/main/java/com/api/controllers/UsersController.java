package com.api.controllers;

import com.library.helpers.BaseResponse;
import com.library.helpers.Helper;
import com.library.models.User;
import com.library.repository.ProfileRepository;
import com.library.repository.UserRepository;
import com.library.service.EventsLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
public class UsersController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "api/users", method = RequestMethod.GET)
    public Page<User> actionIndex(ModelMap model, Pageable pageable) {
        Pageable firstPageWithTwoElements = PageRequest.of(0, 5);

        Page<User> users = userRepository.findByStatus(Helper.STATUS_ACTIVE,
                PageRequest.of(0, 3, Sort.by("dateCreated")
                        .descending()
                        .and(Sort.by("name").ascending())));

        int totalPages = users.getTotalPages();
        List<Integer> pageNumbers = null;

        if (totalPages > 0) {
            pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
        }

        return users;
    }

    @RequestMapping(value = {"api/users/create"}, method = {RequestMethod.POST})
    public BaseResponse actionCreate(@Valid @RequestBody User objUser) {

        try {

            if (userRepository.findByName(objUser.getName()).size() > 0)
                new Exception("Já existe um 'Perfil' com o mesmo nome.");

            objUser.setId(new Helper().getUUID());
            userRepository.save(objUser);

            return new BaseResponse().getResponse(1, "ok", objUser);

        } catch (Exception e) {
            new EventsLogService().AddEventologs(null, "Excption in " + this.getClass().getName(), e.getMessage(), null);
            return new BaseResponse().getResponse(0, e.getMessage(), null);
        }
    }

}