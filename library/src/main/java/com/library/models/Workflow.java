package com.library.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "wf_workflow")
public class Workflow extends AuditModel {

    @Id
    private UUID id;

    @NotBlank
    @Size(min = 3, max = 128)
    @Column(nullable = false)
    private String name;

    @Column(name = "process_code", nullable = false)
    private String processCode;

    //target table related with workflow
    @Column(name = "target_table_id", nullable = true)
    private UUID targetTableId;

    @OneToMany(mappedBy="workflow") /* this will look for 'workflow' property in the 'States' Entity */
    @JsonIgnore
    private Set<States> states;

    @Column(name = "is_concluded", nullable = true, columnDefinition = "int default 0")
    private Integer isConcluded;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getTargetTableId() {
        return targetTableId;
    }

    public void setTargetTableId(UUID targetTableId) {
        this.targetTableId = targetTableId;
    }

    public Set<States> getStates() {
        return states;
    }

    public void setStates(Set<States> states) {
        this.states = states;
    }

    public String getProcessCode() {
        return processCode;
    }

    public void setProcessCode(String processCode) {
        this.processCode = processCode;
    }

    public Integer getIsConcluded() {
        return isConcluded;
    }

    public void setIsConcluded(Integer isConcluded) {
        this.isConcluded = isConcluded;
    }


}
