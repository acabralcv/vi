package com.library.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "tbl_user_profiles")
public class CadeiaProfiles extends AuditModel {

    @Id
    private UUID id;

    /* não eh uma propiedade da DB */
    @Transient
    private UUID profileId;

    /* não eh uma propiedade da DB */
    @Transient
    private UUID userId;

    @ManyToOne
    @JoinColumn(name="profile_id", nullable=false)
    @JsonIgnore
    private Profile profile;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    @JsonIgnore
    private User user;

    @Column(name = "is_editable", nullable = false, columnDefinition = "int default 1")
    private Integer isEditable;

    @Column(name = "force_access_check", nullable = false, columnDefinition = "int default 0")
    private Integer forceAccessCheck;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getProfileId() {
        return profileId;
    }

    public void setProfileId(UUID profileId) {
        this.profileId = profileId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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