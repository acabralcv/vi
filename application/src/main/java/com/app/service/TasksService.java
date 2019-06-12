package com.app.service;

import com.app.helpers.Params;
import com.app.helpers.ServiceProxy;
import com.library.helpers.BaseResponse;
import com.library.helpers.Helper;
import com.library.helpers.UtilsDate;
import com.library.models.Recluso;
import com.library.models.Tasks;
import com.library.models.User;
import com.library.models.Workflow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.UUID;

public class TasksService {

    @Autowired
    private final Environment env;

    public TasksService(Environment _env){
        this.env = _env;
    }


    /**
     *
     * @param type task type. Ex: Helper.TaskType.RECLUSO_PENDING_APROVING, Helper.TaskType.OTHER, ..
     * @param message message to be displayed to the target user
     * @param descripition  task descripition
     * @param userId user who send notification/task
     * @param targetUserId target user who notification/task was send to
     * @return
     */
    public Tasks addUserTask(String type, String message, String descripition, UUID userId, UUID targetUserId){

        Tasks task = new Tasks();
        task.setActionType(Helper.TaskActionType.TASK_ACTION_TASK.toString());

        task.setTaskType(type);
        task.setMessage(message);
        task.setDescription(descripition);

        task.setUser(new UserService(this.env).findOne(userId.toString()));
        task.setTargetUser(new UserService(this.env).findOne(targetUserId.toString()));

        return this.saveTask(task);
    }



    /**
     *
     * @param taskType task type. Ex: Helper.TaskType.RECLUSO_PENDING_APROVING, Helper.TaskType.OTHER, ..
     * @param message message to be displayed to the target user
     * @param descripition  task descripition
     * @param userId user who send notification/task
     * @param targetUserId target user who notification/task was send to
     * @return
     */
    public Tasks addUserNotification(String taskType, String message, String descripition, UUID userId, UUID targetUserId){

        Tasks task = new Tasks();
        task.setActionType(Helper.TaskActionType.TASK_ACTION_NOTIFICATION.toString());

        task.setTaskType(taskType);
        task.setMessage(message);
        task.setDescription(descripition);

        task.setUser(new UserService(this.env).findOne(userId.toString()));
        task.setTargetUser(new UserService(this.env).findOne(targetUserId.toString()));

        return this.saveTask(task);
    }

    /**
     * PRIVATE METHOD TO SAVE TASKS
     * @param task
     * @return
     */
    private Tasks saveTask(Tasks task){

        BaseResponse oBaseResponse = (new ServiceProxy(this.env)).postJsonData("api/tasks/create", task, new ArrayList<>());

        Tasks createdTask = (Tasks) BaseResponse.convertToModel(oBaseResponse, new Tasks());

        return createdTask;
    }
}
