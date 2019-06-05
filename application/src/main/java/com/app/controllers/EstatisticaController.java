package com.app.controllers;


import com.app.helpers.ServiceProxy;
import com.app.service.DomainService;
import com.library.helpers.BaseResponse;
import com.library.helpers.HelperPaging;
import com.library.models.Domain;
import org.json.simple.JSONObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
public class EstatisticaController {

    @RequestMapping(value = "estatisticas", method = RequestMethod.GET)
    public String actionIndex(ModelMap model, @PageableDefault(sort = {"name"}, value = 10, page = 0) Pageable pageable) {

        return  "/views/estatistica/index";
    }
}
