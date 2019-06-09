package com.library.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "tbl_user")
public class User extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Size(max = 256)
    private String accessToken;

    @NotBlank
    @Size(min = 3, max = 30)
    private String username;

    @NotBlank
    @Size(min = 3, max = 30)
    private String email;

    @NotBlank
    @Size(min = 3, max = 150)
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_image_id", referencedColumnName = "id")
    private Image profileImage;

    @Column(name = "is_editable", nullable = false)
    private Integer isEditable  = 1;

    @OneToMany(mappedBy="user") /* this will look for 'user' property in the 'UserProfiles' model */
    @JsonIgnore
    private Set<UserProfiles> UserProfiles;

    public void setProfileImageId(UUID profile_image_id) {
        this.profile_image_id = profile_image_id;
    }

    /** No table field **/
    @Transient
    public UUID profile_image_id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getIsEditable() {
        return isEditable;
    }

    public void setIsEditable(Integer isEditable) {
        this.isEditable = isEditable;
    }

    public Set<com.library.models.UserProfiles> getUserProfiles() {
        return UserProfiles;
    }

    public void setUserProfiles(Set<com.library.models.UserProfiles> userProfiles) {
        UserProfiles = userProfiles;
    }

    public Image getProfileImage() {
        return profileImage;
    }

    public UUID getProfileImageId() {
        return profile_image_id;
    }

    public void setProfileImage(Image profileImage) {
        this.profileImage = profileImage;
    }
}