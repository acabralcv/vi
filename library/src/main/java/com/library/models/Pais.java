package com.library.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@Entity
@Table(name = "tbl_pais")
public class Pais extends AuditModel {

    @Id
    private UUID id;

    @NotBlank
    @Size(min = 3, max = 150)
    private String nome;

    @Column(name = "continente", nullable = true)
    private String continente;

    @Column(name = "capital", nullable = true)
    private String capital;

    @Column(name = "ordem", nullable = false, columnDefinition = "int default 100")
    private String ordem;

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

    public String getContinente() {
        return continente;
    }

    public void setContinente(String continente) {
        this.continente = continente;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getOrdem() {
        return ordem;
    }

    public void setOrdem(String ordem) {
        this.ordem = ordem;
    }
}