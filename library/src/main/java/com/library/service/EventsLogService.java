package com.library.service;

import com.library.repository.EventslogRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class EventsLogService {

    @Autowired
    private EventslogRepository eventslogRepository;

    public String AddEventologs(String type, String message, String description,  UUID id_target_table){

        System.out.println("\nError in " + this.getClass().getName());
        System.out.println("\n" + message + "\n" + description);

        return message;
    }
}
