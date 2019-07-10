package com.app.controllers;

//import MyService;
import com.app.helpers.ServiceProxy;
import com.app.service.TasksService;
import com.app.service.UserService;
import com.library.helpers.BaseResponse;
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
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
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

//    @RequestMapping(value = "/username", method = RequestMethod.GET)
//    @ResponseBody
//    public String currentUserName(Principal principal) {
//        return principal.getName();
//    }

    /**
     * @param model
     * @return
     */
    @GetMapping(value = {"/", "home", "index", "default"})
    public String home(ModelMap model) {

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (!(authentication instanceof AnonymousAuthenticationToken)) {
//            System.out.println(" AUTHENTICATED!" + authentication.getName());
//
//            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//            boolean authorized = authorities.contains(new SimpleGrantedAuthority("admin"));
//
//            System.out.println(" getAuthorities!" + authorized);
//        }else{
//            System.out.println("NOT AUTHENTICATED!");
//
//        }



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
//        User user = new User();
//        user.setId(UUID.fromString("d7224655-312a-40fe-ac2b-d02ae239846f"));
//       ArrayList<Tasks> userTasks = new TasksService(env).getUserTasks(user, PageRequest.of(0,10));
//       if(userTasks.size() == 0)
//           return "redirect:/task/";

        model.addAttribute("appKey", new Helper().getUUID());
        model.addAttribute("appName","App Name Test");
        //model.addAttribute("userTasks",userTasks);
        model.addAttribute("userTasks", new ArrayList<>());
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
    public String actionAbout(Principal principal ) {

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

    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }

    @RequestMapping(value="/login", method = RequestMethod.GET)
    public String actionLogin () {
        return "login";
    }





}
