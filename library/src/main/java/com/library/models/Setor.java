package com.library.models;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@Entity
@Table(name = "tbl_setor")
public class Setor extends AuditModel {

    @Id
    private UUID id;

    @NotBlank
    @Size(min = 3, max = 150)
    private String nome;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_complexo", referencedColumnName = "id", nullable = true)
    private Complexo complexo;


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


    public Complexo getcomplexo() {
        return complexo;
    }

    public void setComplexo(Complexo complexo) {
        this.complexo = complexo;
    }
}
