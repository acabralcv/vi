package com.app.helpers;

import com.library.helpers.BaseResponse;
import com.library.helpers.Helper;
import com.library.helpers.UtilsDate;
import com.library.models.Recluso;
import com.library.models.Workflow;
import com.library.repository.StatesRepository;
import com.library.repository.WorkflowRepository;
import com.library.service.EventsLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public class FlowConfig {

    private Integer step;
    private String name;
    private String processCode;
    private String resourse;
    private Object objEntity;

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
        o1.setResourse("api/workflows/teste_01");
        o1.setName("Estado teste 01");

        o2.setStep(2);
        o2.setProcessCode("PROCESS_USER_REGISTRATION");
        o2.setResourse("api/workflows/teste_01");
        o2.setName("Estado teste 02");

        o3.setStep(3);
        o3.setProcessCode("PROCESS_USER_REGISTRATION");
        o3.setResourse("api/workflows/teste_01");
        o3.setName("Estado teste 03");

        processes.add(o1);
        processes.add(o2);
        processes.add(o3);

        return processes;
    }

    public BaseResponse transitionFlow(FlowConfig flowConfig){

        try {

            BaseResponse oBaseResponse = new BaseResponse();
            ArrayList<FlowConfig> flows = this.getProcess();

            for (FlowConfig oConfig: flows){

                if( oConfig.getProcessCode() == flowConfig.getProcessCode() && oConfig.getStep() == flowConfig.getStep()){

                    System.out.println(oConfig.getResourse());

                    oBaseResponse = (new ServiceProxy())
                            .postJsonData(oConfig.getResourse(), flowConfig.getObjEntity(), new ArrayList<>());

                    if(oBaseResponse.getStatusAction() ==  1){

                        //let's try to get workflow
                        Workflow workflow = this.getWorflowByProcess(flowConfig.getProcessCode());

                        if(workflow == null && flowConfig.getStep() != 1)
                            throw new Exception("Não foi possivel determinar o workflow. Processo: " + flowConfig.getProcessCode() + " :: Step: " + flowConfig.getStep());

                        if(workflow == null)
                            workflow = this.startWorkflow(flowConfig);

                        new FlowHelper(this.statesRepository)
                                .updateState(workflow, flowConfig.getStep(), oConfig.getName());
                    }

                    return  oBaseResponse;
                }
            };

            String message = "Cannot find the process configuration: "
                    + " Process Code: " + flowConfig.getProcessCode() + " :: Step: " + flowConfig.getStep();

            return new BaseResponse().getObjResponse(0,message, null);

        }catch (Exception e){
//            new EventsLogService(eventslogRepository).AddEventologs(null,"Excption in class '" + this.getClass().getName()
//                    + "' method " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",e.getMessage(),null, null);
            return new BaseResponse(0, e.getMessage(), null);
        }
    }

    public Workflow getWorflowByProcess(String processCode) {

        Optional<Workflow> workflow = workflowRepository.findByProcessCode(processCode);

        return workflow.isPresent() ? workflow.get() : null;
    }

    public Workflow startWorkflow(FlowConfig flowConfig) {

        Workflow workflow = new Workflow();
        workflow.setId(new Helper().getUUID());
        workflow.setStatus(Helper.STATUS_ACTIVE);
        workflow.setDateCreated(UtilsDate.getDateTime());
        workflow.setIsConcluded(0);

        workflow.setProcessCode(flowConfig.getProcessCode());
        workflow.setName(flowConfig.getProcessCode());

        return this.workflowRepository.save(workflow);
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

    public Object getObjEntity() {
        return objEntity;
    }

    public void setObjEntity(Object objEntity) {
        this.objEntity = objEntity;
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
