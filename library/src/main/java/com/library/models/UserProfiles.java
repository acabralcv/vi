package com.library.models;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "tbl_user_profiles")
public class UserProfiles extends AuditModel {

    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name="profile_id", nullable=false)
    private Profile profile;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @Column(name = "is_editable", nullable = false, columnDefinition = "int default 1")
    private Integer isEditable;

    @Column(name = "force_access_check", nullable = false, columnDefinition = "int default 0")
    private Integer forceAccessCheck;
}