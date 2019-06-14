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

    //to differentiate the various types of notifications
    @Column(name = "action_type", nullable = false, columnDefinition = "varchar(64)")
    private String actionType;

    //to categorize the type of user tasks/notification
    @Column(name = "task_type", nullable = false, columnDefinition = "varchar(64)")
    private String taskType;

    @Column(name = "id_tabela_alvo", nullable = true)
    private UUID idTabelaAlvo;

    @Column(name = "message", nullable = false, columnDefinition = "varchar(256)")
    private String message;

    @Column(name = "description", nullable = true, columnDefinition = "varchar(5000)")
    private String description;

    @Column(name = "is_viewed", nullable = false, columnDefinition = "int default 0 ")
    private Integer isViewed;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    //@JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name="target_user_id", nullable=false)
    //@JsonIgnore
    private User targetUser;

    @OneToMany(mappedBy="task") /* this will look for 'task' property in the 'Document' bean */
    //@JsonIgnore
    private Set<Document> documents;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getIsViewed() {
        return isViewed;
    }

    public void setIsViewed(Integer isViewed) {
        this.isViewed = isViewed;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getTargetUser() {
        return targetUser;
    }

    public void setTargetUser(User targetUser) {
        this.targetUser = targetUser;
    }

    public Set<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(Set<Document> documents) {
        this.documents = documents;
    }

    public UUID getIdTabelaAlvo() {
        return idTabelaAlvo;
    }

    public void setIdTabelaAlvo(UUID idTabelaAlvo) {
        this.idTabelaAlvo = idTabelaAlvo;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }
}
