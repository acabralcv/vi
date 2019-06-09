package com.app.controllers;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CadeiaController {

    @RequestMapping(value = "cadeias", method = RequestMethod.GET)
    public String actionIndex(ModelMap model, @PageableDefault(sort = {"name"}, value = 10, page = 0) Pageable pageable) {

        return  "/views/cadeia/index";
    }
}
