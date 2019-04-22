package com.models;

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
    @Size(min = 3, max = 150)
    private String name;

    @Column(name = "is_editable", nullable = false)
    private Integer isEditable  = 1;

    @OneToMany(mappedBy="user") //this will look for 'user' property in the 'UserProfiles' model
    private Set<UserProfiles> UserProfiles;
}