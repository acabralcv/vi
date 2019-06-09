package com.library.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@Entity
@Table(name = "tbl_cadeia")
public class Cadeia extends AuditModel {

    @Id
    private UUID id;

    @NotBlank
    @Size(min = 3, max = 50)
    private String nome;

    @Size(min = 3, max = 50)
    @Column(nullable = true)
    private String localizacao;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_image_id", referencedColumnName = "id", nullable = true)
    private Image profileImage;

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

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String alcunha) {
        this.localizacao = localizacao;
    }


}
