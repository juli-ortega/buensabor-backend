package com.example.buensabor.entity.dto.OrderDTOs;

import java.util.Date;
import java.util.List;

import com.example.buensabor.Bases.BaseDTO;
import com.example.buensabor.entity.dto.OrderProductDTO;
import com.example.buensabor.entity.dto.ClientDTOs.ClientBasicDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO extends BaseDTO{
    private ClientBasicDTO client;
    private Long companyId;
    private String description;
    private String status;
    private Date initAt;
    private Date finalizedAt;
    private String deliveryType;
    private Double total;
    private List<OrderProductBasicDTO> orderProducts;
}
