package com.app.controllers;

//import MyService;
import com.app.service.TasksService;
import com.library.helpers.Helper;
import com.library.models.Tasks;
import com.library.repository.EventslogRepository;
import com.library.repository.UserRepository;
import com.library.service.EventsLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.time.format.DateTimeFormatter;
import java.util.*;


@Controller
public class HomeController {

    //private final MyService myService;
    private static final Logger LOG = Logger.getLogger(HomeController.class.getName());

    @Autowired
    private EventslogRepository eventslogRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Environment env;

    public HomeController(/*MyService myService*/) { }

    /**
     * @param model
     * @return
     */
    @GetMapping(value = {"/", "home", "index", "default"})
    public String home(ModelMap model) {

        model.addAttribute("appKey", new Helper().getUUID());
        model.addAttribute("appName","App Name Test");
        return "views/home/index";
    }

    /**
     * @return
     */
    @GetMapping("/about")
    public String actionAbout() {

        return "views/home/about";
    }


    /**
     * @return
     */
    @GetMapping("/teste")
    public String actionTeste() {

        /**
         * create a log for ELK
         */
        new EventsLogService().AddEventologs("Nava visita. ");

        /*

                Tasks task = new TasksService(env)
                        .addUserTask(
                                Helper.TaskType.RECLUSO_PENDING_APROVING.toString(),
                                "Recluso a aguardar aprovação",
                                "Novo foi registado no sistema e encontra-se a aguardar aprovação",
                                UUID.fromString("4c8c4044-7e46-4934-ab6b-58d63db1d75f"),
                                UUID.fromString("4c8c4044-7e46-4934-ab6b-58d63db1d75f")
                        );
        */


        // LOG.log(Level.INFO, task.getMessage() + "; Por " + task.getUser().getName() + "; Data " + new Date());

        //teste evenets log
        /*
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Madrid"));
            Date currentDate = calendar.getTime();
            new EventsLogService(eventslogRepository).AddEventologs(null,"Excption in class '" + this.getClass().getName()
                    + "' method " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()", "Test: Someone int the app! :) :) :) at " + currentDate.toString(),null, null);
        */

        return "views/home/about";
    }



    @GetMapping("/processos")
    public String actionTeste1() {
        return "views/estatistica/index";
    }

}
