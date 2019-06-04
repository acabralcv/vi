package com.app.helpers;

import com.library.helpers.BaseResponse;
import com.library.helpers.Helper;
import com.library.helpers.UtilsDate;
import com.library.models.States;
import com.library.models.Workflow;
import com.library.repository.StatesRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class FlowHelper {


    @Autowired
    private StatesRepository statesRepository;

    public FlowHelper(){
    }

    public FlowHelper(StatesRepository statesRepository){
        this.statesRepository = statesRepository;
    }

    public BaseResponse updateState(Workflow workflow, Integer step, String name){

        States oStates = new States();
        oStates.setId(new Helper().getUUID());
        oStates.setStatus(Helper.STATUS_ACTIVE);
        oStates.setDateCreated(UtilsDate.getDateTime());
        oStates.setWorkflow(workflow);
        oStates.setName(name);
        oStates.setIsViewable(1);

        States savedState = this.statesRepository.save(oStates);

        return new BaseResponse().getObjResponse(1, "ok", savedState);
    }
}
