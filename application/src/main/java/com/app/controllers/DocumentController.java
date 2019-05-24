package com.app.controllers;

import com.app.helpers.ServiceProxy;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.helpers.BaseResponse;
import com.library.helpers.HelperPaging;
import com.library.models.Document;
import com.library.models.Domain;
import com.library.models.Profile;
import com.library.models.User;
import com.library.repository.DocumentRepository;
import com.library.repository.DomainRepository;
import org.json.simple.JSONObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;

@Controller
public class DocumentController {

    DocumentRepository documentRepository;
    final ObjectMapper objectMapper = new ObjectMapper();
    final ServiceProxy oServiceProxy = new ServiceProxy();

    @RequestMapping(value = "documents", method = RequestMethod.GET)
    public String actionDocuments(ModelMap model, @PageableDefault(page = 0, size = 10) @SortDefault.SortDefaults({@SortDefault(sort = "dateCreated", direction = Sort.Direction.DESC), @SortDefault(sort = "name", direction = Sort.Direction.ASC)})Pageable pageable){

        //get info
        BaseResponse objResponse = (new ServiceProxy())
                .getJsonData("api/storage/documents", (new ServiceProxy()).encodePageableParams(pageable));

        //Pageable result objt
        JSONObject dataResponse = (JSONObject) objResponse.getData();

        model.addAttribute("objPaging", (new HelperPaging().getResponsePaging(pageable, dataResponse)));
        model.addAttribute("documents", (ArrayList<Profile>) dataResponse.get("content"));

        return  "/views/document/index";
    }
}
