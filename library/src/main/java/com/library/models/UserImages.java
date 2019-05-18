package com.library.models;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "tbl_user_images")
public class UserImages extends AuditModel {

    @Id
    private UUID id;

    @Transient /* não eh uma propiedade da DB */
    private UUID imageId;


    @Transient /* não eh uma propiedade da DB */
    private UUID userId;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @ManyToOne
    @JoinColumn(name="image_id", nullable=false)
    private Image image;

}