package com.app.service;

import com.app.helpers.Params;
import com.app.helpers.ServiceProxy;
import com.library.helpers.BaseResponse;
import com.library.helpers.Helper;
import com.library.helpers.UtilsDate;
import com.library.models.Recluso;
import com.library.models.Tasks;
import com.library.models.Workflow;

import java.util.ArrayList;
import java.util.UUID;

public class TasksService {


    /**
     *
     * @param task
     * @return
     */
    public static Tasks addTask(Tasks task){

        BaseResponse oBaseResponse = (new ServiceProxy()).postJsonData("api/tasks/create", task, new ArrayList<>());

        Tasks createdTask = (Tasks) BaseResponse.convertToModel(oBaseResponse, new Tasks());

        return createdTask;
    }
}
