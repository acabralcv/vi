package com.library.models;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@Entity
@Table(name = "tbl_ilha")
public class Ilha extends AuditModel {

    @Id
    private UUID id;

    @NotBlank
    @Size(min = 3, max = 150)
    private String nome;

    @NotBlank
    @Column(name = "area_geografica", nullable = false, columnDefinition = "varchar(256) default 'SOTAVENTO'")
    private String areaGeografica;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAreaGeografica() {
        return areaGeografica;
    }

    public void setAreaGeografica(String areaGeografica) {
        this.areaGeografica = areaGeografica;
    }
}