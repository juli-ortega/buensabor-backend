package com.example.buensabor.entity;

import java.util.Date;

import com.example.buensabor.Bases.BaseEntity;
import com.example.buensabor.entity.enums.PayStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Payment extends BaseEntity{

    @Column(name = "order_id")
    private Order order;

    @Column(name = "mercado_pago_id")
    private String mercadoPagoId;

    @Enumerated(EnumType.STRING) // Assuming PayStatus is an enum
    @Column(name = "pay_status")
    private PayStatus payStatus;

    @Column(name = "amount")
    private double amount;

    @Column(name = "approved_date")
    private Date approvedDate;
}


