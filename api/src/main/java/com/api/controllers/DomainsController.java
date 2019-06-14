package com.api.controllers;

import com.library.models.Domain;
import com.library.helpers.Helper;
import com.library.helpers.UtilsDate;
import com.library.helpers.BaseResponse;
import com.library.repository.UserRepository;
import com.library.service.EventsLogService;
import com.library.repository.DomainRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.UUID;
import javax.validation.Valid;

@RestController
public class DomainsController {

    @Autowired
    private DomainRepository domainRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     *
     * @param domainType
     * @param pageable
     * @return
     */
    @RequestMapping(value = {"api/domains"}, method = {RequestMethod.GET})
    public ResponseEntity actionDetails(@RequestParam(name = "domainType", required = false) String domainType, @PageableDefault(sort = {"name"}, value = 10, page = 0) Pageable pageable) {

        try  {

            Page<Domain> domains = null;
            Pageable pageableBuilded = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("dateCreated")
                    .descending()
                    .and(Sort.by("name").ascending()));

            if(domainType != null)
                domains = domainRepository.findByDomainType(domainType,pageableBuilded);
            else
                domains = domainRepository.findByStatus(Helper.STATUS_ACTIVE, pageableBuilded);

            return ResponseEntity.ok().body(new BaseResponse().getObjResponse(1,"ok", domains ));

        }catch (Exception e){
            new EventsLogService(userRepository).AddEventologs(null,"Excption in class '" + this.getClass().getName()
                    + "' method " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",e.getMessage(),null, null);
            return ResponseEntity.ok().body(new BaseResponse(0, e.getMessage(), null));
        }
    }


    @RequestMapping(value = {"api/domains/details"}, method = {RequestMethod.GET})
    public ResponseEntity actionDetails(@RequestParam(name = "id") UUID id) {

        try {

            Optional<Domain> oDominio = (Optional) domainRepository.findById(id);

            return ResponseEntity.ok().body(new BaseResponse().getObjResponse(oDominio != null ? 1 : 0,"ok", oDominio));

        }catch (Exception e){
            new EventsLogService(userRepository).AddEventologs(null,"Excption in class '" + this.getClass().getName()
                    + "' method " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",e.getMessage(),null, null);
            return ResponseEntity.ok().body(new BaseResponse(0, e.getMessage(), null));
        }
    }

    @RequestMapping(value = {"api/domains/create"}, method = {RequestMethod.POST})
    public ResponseEntity actionCreate(@Valid @RequestBody Domain objDomain) {

        try {

            if( domainRepository.findByNameAndDomainType(objDomain.getName(), objDomain.getDomainType()) != null)
                new Exception("Já existe um 'Dominio' do mesmo tipo com mesmo nome.");

            objDomain.setId(new Helper().getUUID());
            objDomain.setDateUpdated(UtilsDate.getDateTime());
            domainRepository.save(objDomain);

            return ResponseEntity.ok().body(new BaseResponse().getObjResponse(1,"ok", objDomain ));

        }catch (Exception e){
            new EventsLogService(userRepository).AddEventologs(null,"Excption in class '" + this.getClass().getName()
                    + "' method " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",e.getMessage(),null, null);
            return ResponseEntity.ok().body(new BaseResponse(0, e.getMessage(), null));
        }
    }

}