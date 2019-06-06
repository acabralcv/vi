package com.library.models;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "tbl_complexo")
public class Complexo extends AuditModel {

    @Id
    private UUID id;
}
