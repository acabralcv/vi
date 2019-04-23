package com.library.models;

import com.sun.istack.internal.NotNull;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@Entity
@Table(name = "tbl_states")
public class WfStates  extends AuditModel {

    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name="workflow_id", nullable=false)
    private WfWorkflow workflow;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "feature_id", nullable = false)
    private UUID featureId;

    @Column(name = "wf_step", nullable = false, columnDefinition = "int default 1")
    private int wfStep;

    @Column(name = "name", nullable = false, columnDefinition = "varchar(150)")
    private String name;

    @Column(name = "description", nullable = false, columnDefinition = "varchar(256)")
    private String description;

    @Column(name = "is_viewable",nullable = false, columnDefinition = "int default 1")
    private int isViewable;

    @Column(name = "state_color", nullable = false, columnDefinition = "varchar(30) default 'default'")
    private int stateColor;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getFeatureId() {
        return featureId;
    }

    public void setFeatureId(UUID featureId) {
        this.featureId = featureId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIsViewable() {
        return isViewable;
    }

    public void setIsViewable(int isViewable) {
        this.isViewable = isViewable;
    }

    public int getStateColor() {
        return stateColor;
    }

    public void setStateColor(int stateColor) {
        this.stateColor = stateColor;
    }
}