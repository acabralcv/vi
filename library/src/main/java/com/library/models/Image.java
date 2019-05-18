package com.library.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;
import java.util.UUID;


@Entity
@Table(name = "tbl_images")
public class Image extends AuditModel {

    @Id
    private UUID id;

    @Column(name = "storage_id", nullable = true)
    private String storageId;

    @OneToMany(mappedBy = "image")
    //this will look for 'image' property in the 'UserImages' model
    private Set<UserImages> UserImages;

    @NotBlank
    @Size(max = 64)
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = true)
    private String description;

    @Size(max = 32)
    @Column(name = "image_type", nullable = true)
    private String imageType;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Set<com.library.models.UserImages> getUserImages() {
        return UserImages;
    }

    public void setUserImages(Set<com.library.models.UserImages> userImages) {
        UserImages = userImages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public String getStorageId() {
        return storageId;
    }

    public void setStorageId(String storageId) {
        this.storageId = storageId;
    }
}