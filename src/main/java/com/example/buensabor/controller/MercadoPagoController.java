package com.example.buensabor.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.buensabor.entity.enums.OrderStatus;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadopago.resources.payment.Payment;

import net.minidev.json.JSONObject;



@RestController
@RequestMapping("api/v1/mercadopago")
public class MercadoPagoController {

    // @PostMapping("/payments/webhook")
    // public ResponseEntity<?> handleWebhook(@RequestBody String payload, @RequestHeader Map<String, String> headers) {
    //     try {
    //         ObjectMapper mapper = new ObjectMapper();
    //         JsonNode json = mapper.readTree(payload);

    //         String type = json.get("type").asText();

    //         if ("payment".equals(type)) {
    //             Long paymentId = json.get("data").get("id").asLong();

    //             Payment payment = Payment.findById(paymentId);

    //             if ("approved".equals(payment.getStatus())) {
    //                 Long orderId = Long.parseLong(payment.getExternalReference());

    //                 Order order = orderRepository.findById(orderId)
    //                         .orElseThrow(() -> new RuntimeException("Order not found"));

    //                 order.setStatus(OrderStatus.DELIVERED);
    //                 order.setFinalizedAt(new Date());
    //                 orderRepository.save(order);

    //                 PaymentEntity p = new PaymentEntity();
    //                 p.setOrder(order);
    //                 p.setMercadoPagoId(payment.getId());
    //                 p.setStatus(payment.getStatus());
    //                 p.setAmount(payment.getTransactionAmount());
    //                 p.setApprovedAt(new Date());
    //                 paymentRepository.save(p);
    //             }
    //         }

    //         return ResponseEntity.ok().build();
    //     } catch (Exception e) {
    //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing webhook: " + e.getMessage());
    //     }
    // }

}
