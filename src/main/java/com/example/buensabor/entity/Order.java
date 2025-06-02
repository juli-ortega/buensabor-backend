package com.example.buensabor.entity;



import java.sql.Date;
import com.example.buensabor.entity.enums.DeliveryType;
import com.example.buensabor.entity.enums.OrderStatus;
import com.example.buensabor.Bases.BaseEntity;


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
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order extends BaseEntity{
    /*table order {
  id int [primary key]
  client_id int [ref: > client.id]
  company_id int [ref: > company.id]
  description string
  status_id int [ref: > order_status.id]
  init_at datetime
  finalized_at datetime 
  delivery_type_id int [ref: > delivery_type.id]
  total double
} */
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "init_at")
    private Date initAt;

    @Column(name = "finalized_at")
    private Date finalizedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_type")
    private DeliveryType deliveryType;

    @Column(name = "total")
    private double total;
}
