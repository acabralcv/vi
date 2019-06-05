package com.library.service;

import com.library.helpers.Helper;
import com.library.helpers.UtilsDate;
import com.library.models.Eventslog;
import com.library.models.User;
import com.library.repository.EventslogRepository;
import com.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EventsLogService {

    @Autowired
    private EventslogRepository eventslogRepository;

    public EventsLogService(){
    }

    public EventsLogService(EventslogRepository eventslogRepository){
        this.eventslogRepository = eventslogRepository;
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

        Eventslog oEventslog = new Eventslog();
        oEventslog.setId(new Helper().getUUID());
        oEventslog.setDateCreated(UtilsDate.getDateTime());
        oEventslog.setMessage(message);
        oEventslog.setDescription(description);
        oEventslog.setStatus(Helper.STATUS_ACTIVE);
        oEventslog.setTargetTableId(id_target_table);

        eventslogRepository.save(oEventslog);

        System.out.println("\n" + message + "\n" + description);

        return oEventslog.getDescription();
    }
}
