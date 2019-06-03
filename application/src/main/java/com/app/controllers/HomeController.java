package com.app.controllers;

//import MyService;
import com.library.helpers.Helper;
import com.library.repository.EventslogRepository;
import com.library.repository.UserRepository;
import com.library.service.EventsLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {

    //private final MyService myService;

    @Autowired
    private EventslogRepository eventslogRepository;

    public HomeController(/*MyService myService*/) {
        //this.myService = myService;
    }

    /**
     * @param model
     * @return
     */
    @GetMapping(value = {"/", "home", "index", "default"})
    public String home(ModelMap model) {
        //teste evenets log
        new EventsLogService(eventslogRepository).AddEventologs(null,"Excption in class '" + this.getClass().getName()
                + "' method " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()", "Teste: Someone int the app! :) :) :) ",null, null);

        model.addAttribute("appKey", new Helper().getUUID());
        model.addAttribute("appName","App Name Test");
        return "views/home/index";
    }

    /**
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

}
