package com.library.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;


@Entity
@Table(name = "tbl_recluso_cela")
public class ReclusoCela extends  AuditModel {

    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name="recluso_id", nullable=false)
    //@JsonIgnore
    private Recluso recluso;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    //@JsonIgnore
    private Cela cela;

    @JsonFormat(shape = JsonFormat.Shape.STRING ,pattern = "dd/MM/yyyy" , timezone="UTC")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_remocao", nullable = true, updatable = true)
    private Date dateRemocao;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Recluso getRecluso() {
        return recluso;
    }

    public void setRecluso(Recluso recluso) {
        this.recluso = recluso;
    }

    public Cela getCela() {
        return cela;
    }

    public void setCela(Cela cela) {
        this.cela = cela;
    }

    public Date getDateRemocao() {
        return dateRemocao;
    }

    public void setDateRemocao(Date dateRemocao) {
        this.dateRemocao = dateRemocao;
    }
}
