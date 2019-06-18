package com.app.helpers;

import com.library.helpers.Helper;
import com.library.models.Tasks;
import org.json.simple.JSONObject;

import javax.annotation.Nullable;
import java.util.UUID;

//T(com.app.helpers.TaskHelper).getLinkByType(oTask)
public class TaskHelper {

    public static String getLinkByType(String taskType,@Nullable UUID targetTableId){

        if (taskType == Helper.TaskType.RECLUSO_PENDING_APROVING.toString()){
             return "/recluso/view/" + targetTableId.toString();
        }

        return "#";
    }

}
