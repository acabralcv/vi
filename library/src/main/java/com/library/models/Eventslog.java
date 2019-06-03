package com.library.models;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "tbl_logs_event")
public class Eventslog extends AuditModel {

    @Id
    private UUID id;

    @Column(name = "target_table_id", nullable = true)
    private UUID targetTableId;

    @Column(name = "message", nullable = false, columnDefinition = "varchar(256)")
    private String message;

    @Column(name = "description", nullable = true, columnDefinition = "varchar(5000)")
    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = true)
    private User user;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UUID getTargetTableId() {
        return targetTableId;
    }

    public void setTargetTableId(UUID targetTableId) {
        this.targetTableId = targetTableId;
    }
}