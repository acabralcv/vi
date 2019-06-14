package com.app.service;

import com.app.helpers.Params;
import com.app.helpers.ServiceProxy;
import com.library.helpers.BaseResponse;
import com.library.models.States;
import com.library.models.User;
import com.library.models.Workflow;
import net.bytebuddy.agent.builder.AgentBuilder;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.UUID;

public class WorkflowService {

    @Autowired
    private final Environment env;

    private final ServiceProxy oServiceProxy;

    public WorkflowService(Environment _env){
        this.env = _env;
        oServiceProxy = new ServiceProxy(this.env);
    }

    /**
     *
     * @param id
     * @return
     */
    public Workflow findOne(UUID id){

        BaseResponse oBaseResponse = oServiceProxy
                .buildParams("api/worflows/details", new Params().Add(new Params("id", id.toString())).Get())
                .getTarget()
                .get(BaseResponse.class);

        oServiceProxy.close();

       return (Workflow) BaseResponse.convertToModel(oBaseResponse, new Workflow());
    }

    /**
     *
     * @param pageable
     * @return
     */
    public JSONObject findAll(org.springframework.data.domain.Pageable pageable){

        //get info
        BaseResponse objResponse = oServiceProxy
                .getJsonData("api/worflows", oServiceProxy.encodePageableParams(pageable));

        //Pageable result objt
        JSONObject dataResponse = (JSONObject) objResponse.getData();

        return dataResponse;
    }

    /**
     *
     * @param targetTableId
     * @return
     */
    public Workflow findByTarget(UUID targetTableId){

        BaseResponse oBaseResponse = oServiceProxy
                .buildParams("api/worflows/details", new Params().Add(new Params("targetTableId", targetTableId.toString())).Get())
                .getTarget()
                .get(BaseResponse.class);

        oServiceProxy.close();

        Workflow oWorkflow = (Workflow) BaseResponse.convertToModel(oBaseResponse, new Workflow());

        return oWorkflow;
    }

    /**
     *
     * @param workflowId
     * @return
     */
    public ArrayList<States> getWfStates(UUID workflowId){

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
