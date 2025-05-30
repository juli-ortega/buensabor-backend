package com.example.buensabor.entity.dto;

import java.sql.Date;

import com.example.buensabor.Bases.BaseDTO;
import com.example.buensabor.entity.enums.PayForm;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FactureDTO extends BaseDTO {
    private ClientDTO client;
    private CompanyDTO company;
    private Date fechaFacturacion;
    private Integer mpPaymentId;
    private Integer mpMerchantOrderId;
    private String mpPreferenceId;
    private String mpPaymentType;
    private PayForm formaPago;
    private Double totalVenta;
    private OrderDTO order;
}
