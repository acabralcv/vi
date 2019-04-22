package com.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Entity
@Table(name = "tbl_process")
public class WfProcess  extends AuditModel{

    @Id
    private UUID id;

    @NotBlank
    @Column(name = "process_code", nullable = false, columnDefinition = "varchar(64)")
    private  String processCode;

    @NotBlank
    @Column(name = "name", nullable = false, columnDefinition = "varchar(150)")
    private String name;

    @NotBlank
    @Column(name = "description", nullable = false, columnDefinition = "varchar(256)")
    private String description;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getProcessCode() {
        return processCode;
    }

    public void setProcessCode(String processCode) {
        this.processCode = processCode;
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
}