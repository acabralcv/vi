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

}