package com.app.helpers;

import com.library.helpers.BaseResponse;
import com.library.helpers.Helper;
import com.library.helpers.UtilsDate;
import com.library.models.States;
import com.library.models.Workflow;
import com.library.repository.StatesRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Stream;

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

        Optional<States> auxState = this.statesRepository.findByWorkflowAndAndStep(workflow, step);

        //if the state for this process allread exist we just update it
        if(auxState.isPresent()){
            oStates = auxState.get();
            oStates.setDateUpdated(UtilsDate.getDateTime());
        }else {
            oStates.setId(new Helper().getUUID());
            oStates.setStatus(Helper.STATUS_ACTIVE);
            oStates.setDateCreated(UtilsDate.getDateTime());
            oStates.setWorkflow(workflow);
            oStates.setName(name);
            oStates.setIsViewable(1);
            oStates.setStep(step);
        }

        States savedState = this.statesRepository.save(oStates);

        return new BaseResponse().getObjResponse(1, "ok", savedState);
    }

}
//
//class WfProcess{
//
//    private String processoCode;
//    private String name;
//    private List<WfActivity> activities;
//
//    public WfProcess(){}
//
//    public WfProcess(String processoCode, String name, List<WfActivity> activities){
//        this.processoCode = processoCode;
//        this.name = name;
//        this.activities = activities;
//    }
//}
//
//
//class WfActivity{
//
//    private String name;
//    private String resource;
//
//    public WfActivity(){}
//
//    public WfActivity(String name, String resource){
//        this.name = name;
//        this.resource = resource;
//    }
//}
