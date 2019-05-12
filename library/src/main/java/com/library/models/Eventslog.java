package com.library.models;

import javax.persistence.*;
import java.util.UUID;


@Entity
@Table(name = "tbl_logs_event")
public class Eventslog extends AuditModel {

    @Id
    private UUID id;

    @Column(name = "user_id", nullable = true)
    private UUID userId;

    @Column(name = "target_table_id", nullable = true)
    private UUID targetTableId;

    @Column(name = "message", nullable = false, columnDefinition = "varchar(256)")
    private int message;

    @Column(name = "description", nullable = true, columnDefinition = "varchar(5000)")
    private String description;
}