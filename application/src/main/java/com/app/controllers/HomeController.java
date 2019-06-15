package com.app.controllers;

//import MyService;
import com.app.service.TasksService;
import com.library.helpers.Helper;
import com.library.models.Tasks;
import com.library.repository.EventslogRepository;
import com.library.repository.UserRepository;
import com.library.service.EventsLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

//import org.apache.log4j.Level;
//import org.apache.log4j.Logger;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;


@Controller
public class HomeController {

    //private final MyService myService;
//    private static final Logger LOG = Logger.getLogger(HomeController.class.getName());

    @Autowired
    private EventslogRepository eventslogRepository;

    @Autowired
    private UserRepository userRepository;

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

//        Tasks task = new Tasks();
//        task.setType(Helper.TaskType.RECLUSO_PENDING_APROVING.toString());
//        task.setMessage("Recluso a aguardar aprovação");
//        task.setDescription("Novo foi registado no sistema e encontra-se a aguardar aprovação");
//        task.setUser(userRepository.findById(UUID.fromString("4c8c4044-7e46-4934-ab6b-58d63db1d75f")).get());
//        task.setTargetUser(userRepository.findById(UUID.fromString("4c8c4044-7e46-4934-ab6b-58d63db1d75f")).get());
//
//        TasksService.addTask(task);

//        LOG.log(Level.INFO, "Welcome at to App" + new Date());

        //teste evenets log
        /*Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Madrid"));
        Date currentDate = calendar.getTime();
        new EventsLogService(eventslogRepository).AddEventologs(null,"Excption in class '" + this.getClass().getName()
                + "' method " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()", "Test: Someone int the app! :) :) :) at " + currentDate.toString(),null, null);
*/
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



    @GetMapping("/processos")
    public String actionTeste() {
        return "views/estatistica/index";
    }

}
