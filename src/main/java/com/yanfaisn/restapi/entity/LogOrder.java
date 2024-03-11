package com.yanfaisn.restapi.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Entity
@Data
public class LogOrder {

    @Id
    private String id;
    @JoinColumn
    @ManyToOne
    private Orders order;
    @JoinColumn
    @ManyToOne
    private Users user;
    private Integer logType;
    private String logMessage;
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;
}
