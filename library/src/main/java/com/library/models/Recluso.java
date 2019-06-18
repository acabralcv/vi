package com.library.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

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

    @Size(min = 3, max = 150)
    @Column(nullable = true)
    private String alcunha;

    @NotBlank
    @Column(name = "num_recluso", nullable = false)
    private String numRecluso;

    @NotBlank
    @Column(name = "sexo", nullable = false)
    private String sexo;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(name = "data_nascimento", nullable = true)
    private Date dataNascimento;

    @Column(name = "nome_pai", nullable = true)
    private String nomePai;

    @Column(name = "nome_mae", nullable = true)
    private String nomeMae;

    @Column(name = "contacto_familiar", nullable = true)
    private String contactoFamiliar;

    @Column(name = "num_doc_identificacao", nullable = true)
    private String numDocIdentificacao;



    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "estado_civil", referencedColumnName = "id", nullable = false)
    private Domain estadoCivil;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tipo_documento", referencedColumnName = "id", nullable = true)
    private Domain tipoDocumento;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "nivel_escolaridade", referencedColumnName = "id", nullable = true)
    private Domain nivelEscolaridade;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profissao", referencedColumnName = "id", nullable = true)
    private Domain profissao;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_nacionalidade", referencedColumnName = "id", nullable = true)
    private Pais nacionalidade;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_ilha", referencedColumnName = "id", nullable = true)
    private Ilha ilha;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_concelho", referencedColumnName = "id", nullable = true)
    private Geografia concelho;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_freguesia", referencedColumnName = "id", nullable = true)
    private Geografia freguesia;

    @Column(name = "zona", nullable = true)
    private String zona;

    @Column(name = "bairo", nullable = true)
    private String bairo;

    //@JsonIgnore
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

    public Pais getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(Pais nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public String getNomePai() {
        return nomePai;
    }

    public void setNomePai(String nomePai) {
        this.nomePai = nomePai;
    }

    public String getNomeMae() {
        return nomeMae;
    }

    public void setNomeMae(String nomeMae) {
        this.nomeMae = nomeMae;
    }

    public Geografia getConcelho() {
        return concelho;
    }

    public void setConcelho(Geografia concelho) {
        this.concelho = concelho;
    }

    public Geografia getFreguesia() {
        return freguesia;
    }

    public void setFreguesia(Geografia freguesia) {
        this.freguesia = freguesia;
    }

    public Domain getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(Domain estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getAlcunha() {
        return alcunha;
    }

    public void setAlcunha(String alcunha) {
        this.alcunha = alcunha;
    }

    public String getContactoFamiliar() {
        return contactoFamiliar;
    }

    public void setContactoFamiliar(String contactoFamiliar) {
        this.contactoFamiliar = contactoFamiliar;
    }

    public String getNumDocIdentificacao() {
        return numDocIdentificacao;
    }

    public void setNumDocIdentificacao(String numDocIdentificacao) {
        this.numDocIdentificacao = numDocIdentificacao;
    }

    public Domain getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(Domain tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public Domain getNivelEscolaridade() {
        return nivelEscolaridade;
    }

    public void setNivelEscolaridade(Domain nivelEscolaridade) {
        this.nivelEscolaridade = nivelEscolaridade;
    }

    public Domain getProfissao() {
        return profissao;
    }

    public void setProfissao(Domain profissao) {
        this.profissao = profissao;
    }

    public Ilha getIlha() {
        return ilha;
    }

    public void setIlha(Ilha ilha) {
        this.ilha = ilha;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getBairo() {
        return bairo;
    }

    public void setBairo(String bairo) {
        this.bairo = bairo;
    }
}
