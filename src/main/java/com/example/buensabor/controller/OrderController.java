package com.example.buensabor.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.buensabor.Bases.BaseControllerImplementation;
import com.example.buensabor.entity.dto.OrderDTO;
import com.example.buensabor.entity.dto.CreateDTOs.OrderCreateDTO;
import com.example.buensabor.entity.dto.UpdateDTOs.OrderUpdateDTO;
import com.example.buensabor.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping(path = "api/v1/order")
public class OrderController extends BaseControllerImplementation<OrderDTO, OrderService>{
    
    @Autowired  
    private OrderService orderService;

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ResponseEntity<?> save(@RequestBody OrderCreateDTO orderCreateDTO) {
        try {
            return ResponseEntity.ok().body(orderService.save(orderCreateDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error to create the order: " + e.getMessage());
        }        
    }

    @PutMapping("/{orderId}/description")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ResponseEntity<?> updateDescription(@PathVariable Long orderId, @RequestBody OrderUpdateDTO orderUpdateDTO) {
        try {
            return ResponseEntity.ok().body(orderService.updateDescription(orderId, orderUpdateDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating description: " + e.getMessage());
        }
    }

    @PutMapping("/{orderId}/cancel")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ResponseEntity<?> cancelOrder(@PathVariable Long orderId) {
        try {
            return ResponseEntity.ok().body(orderService.cancelOrder(orderId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error cancelling order: " + e.getMessage());
        }
    }
    
}
