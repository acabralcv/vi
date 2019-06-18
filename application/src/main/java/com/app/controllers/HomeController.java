package com.app.controllers;

//import MyService;
import com.app.service.TasksService;
import com.app.service.UserService;
import com.library.helpers.Helper;
import com.library.helpers.UtilsDate;
import com.library.models.Tasks;
import com.library.models.User;
import com.library.repository.EventslogRepository;
import com.library.repository.UserRepository;
import com.library.service.EventsLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

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

    public HomeController(/*MyService myService*/) {
        //this.myService = myService;
        //ok
        //ok
    }

    /**
     * @param model
     * @return
     */
    @GetMapping(value = {"/", "home", "index", "default"})
    public String home(ModelMap model) {


        /**
         * create a log for ELK
         */
        new EventsLogService().AddEventologs("Nava visita. ");

       // LOG.log(Level.INFO, task.getMessage() + "; Por " + task.getUser().getName() + "; Data " + new Date());

        //teste evenets log
        /*Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Madrid"));
        Date currentDate = calendar.getTime();
        new EventsLogService(eventslogRepository).AddEventologs(null,"Excption in class '" + this.getClass().getName()
                + "' method " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()", "Test: Someone int the app! :) :) :) at " + currentDate.toString(),null, null);
*/
        User user = new User();
        user.setId(UUID.fromString("d7224655-312a-40fe-ac2b-d02ae239846f"));
       ArrayList<Tasks> userTasks = new TasksService(env).getUserTasks(user, PageRequest.of(0,10));
       if(userTasks.size() == 0)
           return "redirect:/task/";

        model.addAttribute("appKey", new Helper().getUUID());
        model.addAttribute("appName","App Name Test");
        model.addAttribute("userTasks",userTasks);
        return "views/home/index";
    }
        /*
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Madrid"));
            Date currentDate = calendar.getTime();
            new EventsLogService(eventslogRepository).AddEventologs(null,"Excption in class '" + this.getClass().getName()
                    + "' method " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()", "Test: Someone int the app! :) :) :) at " + currentDate.toString(),null, null);
        */

    /**
     * @return
     */
    @GetMapping("/about")
    public String actionAbout() {

		String response = "Welcome to JavaInUse" + new Date();
		LOG.log(Level.INFO, response);

        //service.message
        //return myService.message();

        //service.appName
        //return myService.appName();
        return "views/home/about";
    }



    @GetMapping("/processos")
    public String actionTeste1() {
        return "views/estatistica/index";
    }


    @GetMapping("/task")
    public String actionTask() {

        new TasksService(env).addUserTask(
                Helper.TaskType.RECLUSO_PENDING_APROVING.toString(),
                "Recluso a aguardar aprovação at " + UtilsDate.getDateTime(),
                "Novo foi registado no sistema e encontra-se a aguardar aprovação",
                UUID.fromString("d7224655-312a-40fe-ac2b-d02ae239846f"),
                UUID.fromString("d7224655-312a-40fe-ac2b-d02ae239846f")
            );

        new TasksService(env).addUserTask(
                        Helper.TaskType.OTHER.toString(),
                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit at " + UtilsDate.getDateTime(),
                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam",
                        UUID.fromString("d7224655-312a-40fe-ac2b-d02ae239846f"),
                        UUID.fromString("d7224655-312a-40fe-ac2b-d02ae239846f")
                );

        return "redirect:/home";
    }

}
