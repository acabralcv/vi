package com.controllers;

import com.service.MyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@Controller
public class HomeController {

    private final MyService myService;

    public HomeController(MyService myService) {
        this.myService = myService;
    }

    @GetMapping("/about")
    public String actionAbout() {
        //service.message
        //return myService.message();

        //service.appName
        return myService.appName();
    }

    @GetMapping(value = {"/", "home", "index", "default"})
    public String home(ModelMap model) {
        model.addAttribute("appName", myService.appName());
        return "views/home/index";
    }

}
