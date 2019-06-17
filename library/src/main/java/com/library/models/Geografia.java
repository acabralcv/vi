package com.library.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

/**
 * Deve supotar Concelho, Zona, Freguesia
 */

@Entity
@Table(name = "tbl_geografia")
public class Geografia extends AuditModel {

    @Id
    private UUID id;


    @NotBlank
    @Size(min = 3, max = 30)
    @Column(name = "tipo", nullable = false, columnDefinition = "varchar(30) default 'CONCHELO'")
    private String tipo;

    @NotBlank
    @Size(min = 3, max = 150)
    private String nome;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id", referencedColumnName = "id", nullable = true)
    private Geografia parent;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Geografia getParent() {
        return parent;
    }

    public void setParent(Geografia parent) {
        this.parent = parent;
    }
}
