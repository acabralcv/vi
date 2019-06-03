package com.app.controllers;

//import MyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {

    //private final MyService myService;

    public HomeController(/*MyService myService*/) {
        //this.myService = myService;
        //teste
    }

    /**
     *
     * @return
     */
    @GetMapping("/about")
    public String actionAbout() {
        //service.message
        //return myService.message();

        //service.appName
        //return myService.appName();
        return "views/home/about";
    }

    /**
     *
     * @param model
     * @return
     */
    @GetMapping(value = {"/", "home", "index", "default"})
    public String home(ModelMap model) {
        model.addAttribute("appName","App Name Test");
        return "views/home/index";
    }

}
