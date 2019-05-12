package com.library.models;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "tbl_workflow")
public class WfWorkflow extends AuditModel {

    @Id
    private UUID id;

    @OneToMany(mappedBy="workflow")
    private Set<WfStates> workflowSet;

    @Column(name = "wf_type", nullable = false, columnDefinition = "varchar(64)")
    private  int wfType;

    @Column(name = "feature_id", nullable = false)
    private UUID featureId;

    @Column(nullable = false, columnDefinition = "varchar(150)")
    private String name;

    @Column(name = "is_concluded", nullable = false, columnDefinition = "int default 0")
    private int isConcluded;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getWfType() {
        return wfType;
    }

    public void setWfType(int wfType) {
        this.wfType = wfType;
    }

    public UUID getFeatureId() {
        return featureId;
    }

    public void setFeatureId(UUID featureId) {
        this.featureId = featureId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIsConcluded() {
        return isConcluded;
    }

    public void setIsConcluded(int isConcluded) {
        this.isConcluded = isConcluded;
    }
}