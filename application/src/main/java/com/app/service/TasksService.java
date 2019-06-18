package com.app.service;

import com.app.helpers.Params;
import com.app.helpers.ServiceProxy;
import com.library.helpers.BaseResponse;
import com.library.helpers.Helper;
import com.library.helpers.UtilsDate;
import com.library.models.*;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.UUID;

public class TasksService {

    @Autowired
    private final Environment env;

    private final ServiceProxy oServiceProxy;

    public TasksService(Environment _env){
        this.env = _env;
        oServiceProxy = new ServiceProxy(this.env);
    }
	

    /**
     * RETURNS USER TAKS - targetUserId => User
     * @param user
     * @return
     */
    public ArrayList<Tasks> getUserTasks(User user, Pageable pageable){

        ArrayList<Params> p = oServiceProxy.encodePageableParams(pageable);
        p.add(new Params("user_id", user.getId().toString()));

        BaseResponse oBaseResponse = oServiceProxy
                .getJsonData("api/tasks/user-tasks", p);

        JSONObject dataResponse = (JSONObject) oBaseResponse.getData();

        return dataResponse != null ? (ArrayList<Tasks>) dataResponse.get("content") : new ArrayList<>();
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
        task.setActionType(Helper.TaskActionType.TASK_ACTION_INFO.toString());

        task.setTaskType(type);
        task.setMessage(message);
        task.setDescription(descripition);
        task.setIsRemovable(1);
        //serviço??
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
        task.setIsRemovable(1);

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
