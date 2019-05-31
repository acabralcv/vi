package com.library.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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
}
