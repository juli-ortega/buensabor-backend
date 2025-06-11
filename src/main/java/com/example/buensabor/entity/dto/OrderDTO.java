package com.example.buensabor.entity.dto;

import java.sql.Date;
import java.util.List;

import com.example.buensabor.Bases.BaseDTO;
import com.example.buensabor.entity.enums.DeliveryType;
import com.example.buensabor.entity.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO extends BaseDTO {
    private ClientDTO client;
    private CompanyDTO company;
    private String description;
    private OrderStatus status;
    private Date initAt;
    private Date finalizedAt;
    private DeliveryType deliveryType;
    private double total;
    private List<OrderProductDTO> orderProductDTOs;
}
