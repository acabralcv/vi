package com.library.models;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@Entity
@Table(name = "tbl_complexo")
public class Complexo extends AuditModel {

    @Id
    private UUID id;

    @NotBlank
    @Size(min = 3, max = 150)
    private String nome;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_cadeia", referencedColumnName = "id", nullable = true)
    private Cadeia cadeia;
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


    public Cadeia getCadeia() {

        return cadeia;
    }

    public void setIlha(Cadeia cadeia) {

        this.cadeia = cadeia;
    }
}
