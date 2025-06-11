package com.example.buensabor.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.buensabor.Bases.BaseControllerImplementation;
import com.example.buensabor.entity.dto.OrderDTO;
import com.example.buensabor.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping(path = "api/v1/order")
public class OrderController extends BaseControllerImplementation<OrderDTO, OrderService>{
    
    @Autowired  
    private OrderService orderService;

    @PostMapping("")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    @Override
    public ResponseEntity<?> save(@RequestBody OrderDTO orderDTO) {
        try {
            return ResponseEntity.ok().body(orderService.save(orderDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error to create the order: " + e.getMessage());
        }        
    }
    
}
