package com.library.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "tbl_profile")
public class Profile extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @OneToMany(mappedBy="profile") //this will look for 'profile' property in the 'UserProfiles' model
    private Set<UserProfiles> UserProfiles;

    @NotBlank
    @Size(min = 3, max = 30)
    private String name;

    @Column(name = "is_editable", nullable = false)
    private Integer isEditable  = 1;

    @Column(name = "force_access_check", nullable = false)
    private Integer forceAccessCheck  = 0;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Set<com.library.models.UserProfiles> getUserProfiles() {
        return UserProfiles;
    }

    public void setUserProfiles(Set<com.library.models.UserProfiles> userProfiles) {
        UserProfiles = userProfiles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIsEditable() {
        return isEditable;
    }

    public void setIsEditable(Integer isEditable) {
        this.isEditable = isEditable;
    }

    public Integer getForceAccessCheck() {
        return forceAccessCheck;
    }

    public void setForceAccessCheck(Integer forceAccessCheck) {
        this.forceAccessCheck = forceAccessCheck;
    }
}