package com.library.models;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@Entity
@Table(name = "wf_states")
public class States extends AuditModel{

    @Id
    private UUID id;

    @Column(name = "step", nullable = false, columnDefinition = "int default 1")
    private Integer step;

    @NotBlank
    @Size(min = 3, max = 128)
    @Column(nullable = false)
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "workflow_id", referencedColumnName = "id")
    private Workflow workflow;

    @Column(name = "is_viewable", nullable = true, columnDefinition = "int default 1")
    private Integer isViewable;

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

    public Workflow getWorkflow() {
        return workflow;
    }

    public void setWorkflow(Workflow workflow) {
        this.workflow = workflow;
    }

    public Integer getIsViewable() {
        return isViewable;
    }

    public void setIsViewable(Integer isViewable) {
        this.isViewable = isViewable;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }
}
