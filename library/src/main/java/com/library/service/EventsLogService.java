package com.library.service;

import com.library.helpers.Helper;
import com.library.helpers.UtilsDate;
import com.library.models.Eventslog;
import com.library.models.User;
import com.library.repository.EventslogRepository;
import com.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class EventsLogService {

    @Autowired
    private Environment env;

    @Autowired
    private UserRepository userRepository;

    private static final Logger LOG = Logger.getLogger(EventsLogService.class.getName());

    public EventsLogService(){
    }

    public EventsLogService(Environment _env){
        this.env = _env;
    }

    public EventsLogService(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    public String AddEventologs(String message){

        System.out.println("\n" + message );

        LOG.log(Level.INFO, " SIGP_USER_ACTION at: "
                + " Tipo: " + Helper.LogsType.LOGS_INFO.toString()
                + " Mensagem: " + message
                + " Descrição: "
                + " User: "
                + " Target: ");

        return message;
    }


    public String AddEventologs(String type, String message, String description){

        System.out.println("\n" + message + "\n" + description);

        if(type == null || type.equalsIgnoreCase(""))
            type = Helper.LogsType.LOGS_ERROR.toString();

        LOG.log(Level.INFO, " SIGP_USER_ACTION at: "
                + " Tipo: " + type
                + " Mensagem: " + message
                + " Descrição: " + description
                + " User: "
                + " Target: ");

        return message;
    }

    /**
     * Persist events log
     * @param type
     * @param message
     * @param description
     * @param user_id
     * @param id_target_table
     * @return
     */
    public String AddEventologs(String type, String message, String description, UUID user_id, UUID id_target_table){

        //deixa isto assim enquanto não temos authenticação
        if(true)
            return  this.AddEventologs(type, message, description);

        System.out.println("\n" + message + "\n" + description);

        Optional<User> user = this.userRepository.findById(user_id);
        Optional<User> userTargetUser = this.userRepository.findById(id_target_table);

        String userName = user.isPresent() ? user.get().getName() : " ";
        String targetName = userTargetUser.isPresent() ? userTargetUser.get().getName() : " ";

        if(type == null || type.equalsIgnoreCase(""))
            type = Helper.LogsType.LOGS_ERROR.toString();

        LOG.log(Level.INFO, " SIGP_USER_ACTION at: "
                + " Tipo: " + type
                + " Mensagem: " + message
                + " Descrição: " + description
                + " User: " + userName
                + " Target: " + targetName);

        return message;
    }
}
