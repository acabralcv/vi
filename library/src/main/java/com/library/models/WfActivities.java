package com.library.models;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "tbl_activities")
public class WfActivities  extends AuditModel{

    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name="process_id", nullable=false)
    private WfProcess process;

    @Column(name = "wf_type", nullable = false, columnDefinition = "varchar(64)")
    private  String wfType;

    @Column(name = "wf_step", nullable = false, columnDefinition = "int default 1")
    private  int wfStep;

    @Column(name = "name", nullable = false, columnDefinition = "varchar(150)")
    private String name;

    @Column(name = "class_name", nullable = false, columnDefinition = "varchar(256)")
    private String className;

    @Column(name = "action_name", nullable = false, columnDefinition = "varchar(64)")
    private String actionName;

    @Column(name = "is_viewable", nullable = false, columnDefinition = "int default 1")
    private int isViewable;

    @Column(name = "system_default", nullable = false, columnDefinition = "int default 0")
    private int systemDefault;

    @Column(name = "state_color", nullable = false, columnDefinition = "varchar(64) default 'default'")
    private String stateColor;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getWfType() {
        return wfType;
    }

    public void setWfType(String wfType) {
        this.wfType = wfType;
    }

    public int getWfStep() {
        return wfStep;
    }

    public void setWfStep(int wfStep) {
        this.wfStep = wfStep;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public int getIsViewable() {
        return isViewable;
    }

    public void setIsViewable(int isViewable) {
        this.isViewable = isViewable;
    }

    public int getSystemDefault() {
        return systemDefault;
    }

    public void setSystemDefault(int systemDefault) {
        this.systemDefault = systemDefault;
    }

    public String getStateColor() {
        return stateColor;
    }

    public void setStateColor(String stateColor) {
        this.stateColor = stateColor;
    }

    public WfProcess getProcess() {
        return process;
    }

    public void setProcess(WfProcess process) {
        this.process = process;
    }
}
