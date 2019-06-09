package com.app.service;

import com.app.helpers.Params;
import com.app.helpers.ServiceProxy;
import com.library.helpers.BaseResponse;
import com.library.models.States;
import com.library.models.User;
import com.library.models.Workflow;
import net.bytebuddy.agent.builder.AgentBuilder;
import org.json.simple.JSONObject;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.UUID;

public class WorkflowService {

    private static final ServiceProxy oServiceProxy = new ServiceProxy();

    public static Workflow findOne(UUID id){

       ServiceProxy oServiceProxy = new ServiceProxy();

        BaseResponse oBaseResponse = oServiceProxy
                .buildParams("api/worflows/details", new Params().Add(new Params("id", id.toString())).Get())
                .getTarget()
                .get(BaseResponse.class);

        oServiceProxy.close();

       return (Workflow) BaseResponse.convertToModel(oBaseResponse, new Workflow());
    }

    public static JSONObject findAll(org.springframework.data.domain.Pageable pageable){

        //get info
        BaseResponse objResponse = (new ServiceProxy())
                .getJsonData("api/worflows", (new ServiceProxy()).encodePageableParams(pageable));

        //Pageable result objt
        JSONObject dataResponse = (JSONObject) objResponse.getData();

        return dataResponse;
    }

    public static Workflow findByTarget(UUID targetTableId){

        ServiceProxy oServiceProxy = new ServiceProxy();
        BaseResponse oBaseResponse = oServiceProxy
                .buildParams("api/worflows/details", new Params().Add(new Params("targetTableId", targetTableId.toString())).Get())
                .getTarget()
                .get(BaseResponse.class);
        oServiceProxy.close();

        Workflow oWorkflow = (Workflow) BaseResponse.convertToModel(oBaseResponse, new Workflow());

        return oWorkflow;
    }

    public static ArrayList<States> getWfStates(UUID workflowId){

        BaseResponse oBaseResponse = oServiceProxy
                .buildParams("api/worflows/states", new Params().Add(new Params("id", workflowId.toString())).Get())
                .getTarget()
                .get(BaseResponse.class);
        oServiceProxy.close();

        //the result si type of ArrayList<States>
        ArrayList<States> states = (ArrayList<States>) oBaseResponse.getData();


        return states;
    }
}
