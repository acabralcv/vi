package com.app.helpers;

import com.library.helpers.BaseResponse;
import com.library.models.Recluso;
import com.library.models.Workflow;
import com.library.repository.StatesRepository;
import com.library.repository.WorkflowRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public class FlowConfig {

    private Integer step;
    private String name;
    private String processCode;
    private String resourse;
    private Object oEntity;

    @Autowired
    private StatesRepository statesRepository;

    @Autowired
    private WorkflowRepository workflowRepository;

    public FlowConfig(){
    }

    public FlowConfig(StatesRepository statesRepository, WorkflowRepository workflowRepository){
        this.statesRepository = statesRepository;
        this.workflowRepository = workflowRepository;
    }

    public ArrayList<FlowConfig> getProcess(){

        ArrayList<FlowConfig> processes = new ArrayList<>();

        FlowConfig o1 = new FlowConfig(),
                   o2 = new FlowConfig(),
                   o3 = new FlowConfig();

        o1.setStep(1);
        o1.setProcessCode("PROCESS_USER_REGISTRATION");
        o1.setName("Estado teste 01");

        o2.setStep(2);
        o1.setProcessCode("PROCESS_USER_REGISTRATION");
        o2.setName("Estado teste 02");

        o3.setStep(3);
        o1.setProcessCode("PROCESS_USER_REGISTRATION");
        o3.setName("Estado teste 03");

        processes.add(o1);
        processes.add(o2);
        processes.add(o3);

        return processes;
    }

    public BaseResponse transitionFlow(FlowConfig flowConfig){

        BaseResponse oBaseResponse = new BaseResponse();
        ArrayList<FlowConfig> flows = this.getProcess();

        for (FlowConfig oConfig: flows){

            if( oConfig.getProcessCode() == flowConfig.getProcessCode() && oConfig.getStep() == flowConfig.getStep()){

                System.out.println(flowConfig.getResourse());

                oBaseResponse = (new ServiceProxy())
                        .postJsonData(flowConfig.getResourse(), flowConfig.getoEntity(), new ArrayList<>());

                if(oBaseResponse.getStatusAction() ==  1){

                    //let's try to get workflow
                    Workflow workflow = this.getWorflowByProcess(flowConfig.getProcessCode(), true, flowConfig.getStep());

                    new FlowHelper(this.statesRepository)
                            .updateState(workflow, flowConfig.getStep(), oConfig.getName());
                }

                return  oBaseResponse;
            }
        };

        String message = "Cannot find the process configuration: "
                + " Process Code: " + flowConfig.getProcessCode() + " :: Step: " + flowConfig.getStep();

        return new BaseResponse().getObjResponse(0,message, null);
    }

    public Workflow getWorflowByProcess(String processCode, boolean startNew, int step) {

        Optional<Workflow> workflow = workflowRepository.findByProcessCode(processCode);

        if(workflow == null && startNew == true){
            //start process
            return new Workflow();
        }

        return workflow.get();
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public String getResourse() {
        return resourse;
    }

    public void setResourse(String resourse) {
        this.resourse = resourse;
    }

    public Object getoEntity() {
        return oEntity;
    }

    public void setoEntity(Object oEntity) {
        this.oEntity = oEntity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProcessCode() {
        return processCode;
    }

    public void setProcessCode(String processCode) {
        this.processCode = processCode;
    }
}
