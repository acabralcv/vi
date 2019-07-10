package com.library.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.UUID;


@Entity
@Table(name = "tbl_recluso_cela")
public class ReclusoCela {

    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name="recluso_id", nullable=false)
    @JsonIgnore
    private Recluso recluso;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    @JsonIgnore
    private Cela cela;
}
