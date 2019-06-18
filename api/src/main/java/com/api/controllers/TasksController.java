package com.api.controllers;

import com.library.helpers.BaseResponse;
import com.library.helpers.Helper;
import com.library.helpers.UtilsDate;
import com.library.models.Profile;
import com.library.models.Tasks;
import com.library.models.User;
import com.library.repository.*;
import com.library.service.EventsLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@RestController
public class TasksController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TasksRepository tasksRepository;

    /**
     * @param id
     * @return
     */
    @RequestMapping(value = {"api/tasks/details"}, method = {RequestMethod.GET})
    public ResponseEntity actionDetails(@RequestParam(name = "id") UUID id) {

        try {

            Optional<Tasks> task = tasksRepository.findById(id);

            if (task != null)
                return ResponseEntity.ok().body(new BaseResponse(1, "ok", task.get()));
            else
                return ResponseEntity.ok().body(new BaseResponse(0, "ok", null));

        } catch (Exception e) {
            new EventsLogService(userRepository).AddEventologs(null, "Excption in class '" + this.getClass().getName()
                    + "' method " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()", e.getMessage(), null, null);
            return ResponseEntity.ok().body(new BaseResponse(0, e.getMessage(), null));
        }

    }

    @RequestMapping(value = "api/user-tasks", method = RequestMethod.GET)
    public ResponseEntity actionIndex(ModelMap model, @RequestParam(name = "user_id") UUID user_id, Pageable pageable) {

        try{

            Page<Tasks> tasks = null;
            Optional<User> user = userRepository.findById(user_id);

            if(user.isPresent()){
                tasks = tasksRepository.findByStatusAndUser(Helper.STATUS_ACTIVE,
                        user.get(),
                        PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("dateCreated").descending()));
            }

            return ResponseEntity.ok().body(new BaseResponse().getObjResponse(1,"ok", null ));

        }catch (Exception e){
            new EventsLogService(userRepository).AddEventologs(null,"Excption in class '" + this.getClass().getName()
                    + "' method " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",e.getMessage(),null, null);
            return ResponseEntity.ok().body(new BaseResponse(0, e.getMessage(), null));
        }
    }


    /**
     *
     * @param task
     * @return
     */
    @RequestMapping(value = {"api/tasks/create"}, method = {RequestMethod.POST})
    public ResponseEntity actionCreate(@RequestBody Tasks task) {

        try {

            Optional<Tasks> auxTask = tasksRepository.findIfExists(Helper.STATUS_ACTIVE, task.getUser(), task.getTargetUser(), task.getTaskType());

            if(auxTask.isPresent()){

                //lets clone it, make sure that the message and decription will be up to date
                Tasks oTask = auxTask.get();
                oTask.setDescription(task.getDescription());
                oTask.setMessage(task.getMessage());
                oTask.setDateUpdated(UtilsDate.getDateTime());

                //let's task be updateble
                task = oTask;

            }else {

                task.setId(new Helper().getUUID());
                task.setStatus(Helper.STATUS_ACTIVE);
                task.setDateCreated(UtilsDate.getDateTime());
            }

            if(task.getTaskType() == null || task.getTaskType().equalsIgnoreCase(""))
                task.setTaskType(Helper.TaskType.OTHER.toString());

            if(task.getIsViewed() == null)
                task.setIsViewed(Helper.STATUS_ACTIVE);


            Tasks tasksSaved = tasksRepository.save(task);

            return ResponseEntity.ok().body(new BaseResponse().getObjResponse(1,"ok", tasksSaved ));

        }catch (Exception e){
            new EventsLogService(userRepository).AddEventologs(null,"Excption in class '" + this.getClass().getName()
                    + "' method " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",e.getMessage(),null, null);
            return ResponseEntity.ok().body(new BaseResponse(0, e.getMessage(), null));
        }
    }

}