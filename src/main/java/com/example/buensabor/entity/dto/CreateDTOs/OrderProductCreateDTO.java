package com.example.buensabor.entity.dto.CreateDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductCreateDTO {
    private Long productId;
    private int quantity;
}
