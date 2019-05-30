package com.library.models;

import java.util.UUID;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "tbl_recluso")
public class Recluso extends AuditModel {

    @Id
    private UUID id;

    @NotBlank
    @Size(min = 3, max = 150)
    private String nome;

    @NotBlank
    @Column(name = "num_recluso", nullable = false)
    private String numRecluso;

    @NotBlank
    @Column(name = "sexo", nullable = false)
    private String sexo;

    @Column(name = "data_nascimento", nullable = false)
    private Date dataNascimento;

//    @Column(name = "id_nacionalidade", nullable = true)
//    private String idNacionalidade;
//
//    @Column(name = "id_concelho", nullable = true)
//    private String idConcelho;
//
//    @Column(name = "id_freguesia", nullable = true)
//    private String idFreguesia;
//
//    @Column(name = "pather_name", nullable = true)
//    private String patherName;
//
//    @Column(name = "mother_mae", nullable = true)
//    private String motherName;


//    @Transient
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "id_nacionalidade", referencedColumnName = "id")
//    private Image nacionalidade;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_image_id", referencedColumnName = "id")
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

    public String getNumRecluso() {
        return numRecluso;
    }

    public void setNumRecluso(String numRecluso) {
        this.numRecluso = numRecluso;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Image getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(Image profileImage) {
        this.profileImage = profileImage;
    }
}
