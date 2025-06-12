package com.example.buensabor.entity.dto.CreateDTOs;

import java.util.List;

import com.example.buensabor.entity.enums.DeliveryType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateDTO {
    private String description;
    private DeliveryType deliveryType;
    private List<OrderProductCreateDTO> orderProductDTOs;
}