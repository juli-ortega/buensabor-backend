package com.example.buensabor.entity;


import java.sql.Date;

import com.example.buensabor.Bases.BaseEntity;
import com.example.buensabor.entity.enums.PayForm;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "factures")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Facture extends BaseEntity {
    /* table facture{
  id int [primary key]
  client_id int [ref: > client.id]
  company_id int [ref: > company.id]
  fechaFacturacion datetime
  mpPaymentId Integer
  mpMerchantOrderId Integer
  mpPreferenceId String
  mpPaymentType String
  formaPago int [ref: > pay_form.id]
  totalVenta Double
  order_id int [ref: <> order.id] // uno a uno
} */
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client clientID;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company companyID;

    @Column(name = "fecha_facturacion")
    private Date fechaFacturacion;

    @Column(name = "mp_payment_id")
    private Integer mpPaymentId;

    @Column(name = "mp_merchant_order_id")
    private Integer mpMerchantOrderId;

    @Column(name = "mp_preference_id")
    private String mpPreferenceId;

    @Column(name = "mp_payment_type")
    private String mpPaymentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "forma_pago")
    private PayForm formaPago;

    @Column(name = "total_venta")
    private Double totalVenta;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order orderID;
}
