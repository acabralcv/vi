package com.library.models;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditModel implements Serializable {

    //2019-05-14 16:13:49.854
    @JsonFormat(shape = JsonFormat.Shape.STRING ,pattern = "dd/MM/yyyy" , timezone="UTC")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_created", nullable = false, updatable = false)
    @CreatedDate
    private Date dateCreated;

    @JsonFormat(shape = JsonFormat.Shape.STRING ,pattern = "dd/MM/yyyy" , timezone="UTC")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_updated", nullable = false)
    @LastModifiedDate
    private Date dateUpdated;

    @Column(name = "status", nullable = false)
    private Integer status = 1;

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
