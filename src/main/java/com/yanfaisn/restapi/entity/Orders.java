package com.yanfaisn.restapi.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.yanfaisn.restapi.model.StatusOrder;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Entity
@Data
public class Orders {

    @Id
    private String id;
    private String number;
    @Temporal(TemporalType.DATE)
    private Date date;
    @JoinColumn
    @ManyToOne
    private Users user;
    private String userAddress;
    private BigDecimal amount;
    private BigDecimal shippingCost;
    private String total;
    @Enumerated(EnumType.STRING)
    private StatusOrder statusOrder;
    @Temporal(TemporalType.TIMESTAMP)
    private Date created_at;
}
