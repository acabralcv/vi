package com.library.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "tbl_tasks")
public class Tasks extends AuditModel {

    @Id
    private UUID id;

    //who is creating the task
    @Column(name = "user_id", nullable = false)
    private UUID userId;

    //who the tasks stand for
    @Column(name = "target_user_id", nullable = true)
    private UUID targetUserId;

    @Column(name = "message", nullable = false, columnDefinition = "varchar(256)")
    private int message;

    @Column(name = "description", nullable = true, columnDefinition = "varchar(5000)")
    private String description;

    @OneToMany(mappedBy="task") /* this will look for 'task' property in the 'Document' bean */
    @JsonIgnore
    private Set<Document> documents;


}
