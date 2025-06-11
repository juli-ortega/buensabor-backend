package com.example.buensabor.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.buensabor.Bases.BaseControllerImplementation;
import com.example.buensabor.entity.dto.OrderDTO;
import com.example.buensabor.service.OrderService;

@RestController
@RequestMapping(path = "api/v1/order")
public class OrderController extends BaseControllerImplementation<OrderDTO, OrderService>{
    
}
