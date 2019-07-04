package com.library.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@Entity
@Table(name = "tbl_ala")
public class Cela extends AuditModel {

    @Id
    private UUID id;

    @NotBlank
    @Size(min = 3, max = 150)
    private String nome;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_ala", referencedColumnName = "id", nullable = true)
    private Ala ala;

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

    public Ala getAla() {
        return ala;
    }

    public void setAla(Ala ala) {
        ala = ala;
    }
}
