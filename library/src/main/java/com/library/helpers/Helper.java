package com.library.helpers;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.UUID;

public class Helper {

    public static int STATUS_ACTIVE = 1;
    public static int STATUS_DISABLED = 0;

    public enum TaskType {
        TESTE, //to be removed
        RECLUSO_PENDING_APROVING, //to be edited
        //...
        OTHER
    }


    /**
     * DON'T CHANGE THIS, AND DON'T USE IT IN YOUR CODE
     * IT IS PROPOSE IS TO BE USED 'com/app/service/TasksService.java' IN ONLY
     */
    public enum TaskActionType {
        TASK_ACTION_TASK,
        TASK_ACTION_NOTIFICATION,
        TASK_ACTION_ALERT,
        TASK_ACTION_INFO,
        TASK_ACTION_WARNING,
    }


    public enum LogsType {

        LOGS_ERROR,
        LOGS_INFO,
        LOGS_WARNING,
        LOGS_SUCCESS,

        LOGS_RECLUSO_CREATED,
        LOGS_RECLUSO_UPDATED,

        LOGS_USER_CREATED,
        LOGS_USER_UPDATED,
    }

    /**
     * Generate a unique GUUID
     * @param lenght
     * @return
     */
    public String genToken(Integer lenght) {

        SecureRandom secureRandom = new SecureRandom();
        byte[] token = new byte[lenght];
        secureRandom.nextBytes(token);

        return new BigInteger(1, token).toString(16); //hex encoding
    }


    public UUID getUUID() {

        return UUID.randomUUID();
    }
}