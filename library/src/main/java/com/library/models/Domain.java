package com.library.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;



@Entity
/**
 * Combinatio of type and value should be unique
 *
 **/
@Table(name = "tbl_domain", uniqueConstraints={@UniqueConstraint(columnNames = {"domain_type" , "value"})})
public class Domain extends AuditModel {

    @Id
    private UUID id;

    @NotBlank
    @Size(max = 64)
    @Column(name = "domain_type", nullable = false)
    private String domainType;

    @NotBlank
    @Size(max = 64)
    private String value;

    @NotBlank
    @Size(max = 64)
    private String name;

    //@NotBlank
    @Column(name = "ordem", nullable = false, columnDefinition = "int default 1")
    private Integer ordem;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDomainType() {
        return domainType;
    }

    public void setDomainType(String domainType) {
        this.domainType = domainType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrdem() {
        return ordem;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }
}