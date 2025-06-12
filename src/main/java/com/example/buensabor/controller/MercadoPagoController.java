package com.example.buensabor.controller;

import java.util.Date;
import java.util.Map;

import org.apache.velocity.runtime.Runtime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.buensabor.entity.Order;
import com.example.buensabor.entity.enums.OrderStatus;
import com.example.buensabor.entity.enums.PayStatus;
import com.example.buensabor.repository.OrderRepository;
import com.example.buensabor.repository.PaymentRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadopago.client.payment.PaymentClient;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/v1/mercadopago")
public class MercadoPagoController {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    @PostMapping("/payments/webhook")
    public ResponseEntity<?> handleWebhook(HttpServletRequest request) {
        try {
            String type = request.getParameter("type");
            String paymentIdParam = request.getParameter("data.id");

            if ("payment".equals(type) && paymentIdParam != null) {
                Long paymentId = Long.parseLong(paymentIdParam);

                PaymentClient paymentClient = new PaymentClient();
                com.mercadopago.resources.payment.Payment mpPayment = paymentClient.get(paymentId);

                System.out.println("FORMA DE PAGO: " + mpPayment.getStatus());
                System.out.println("EXTERNAL: " + mpPayment.getExternalReference());
                System.out.println("ORDER_ID: " + mpPayment.getOrder().getId());
                System.out.println("CURRENCY_ID: " + mpPayment.getCurrencyId());

                if ("approved".equals(mpPayment.getStatus())) {
                    Long orderId = Long.parseLong(mpPayment.getExternalReference());

                    Order order = orderRepository.findById(orderId)
                            .orElseThrow(() -> new RuntimeException("Order not found"));

                    order.setStatus(OrderStatus.INKITCHEN);
                    order.setFinalizedAt(new Date());
                    orderRepository.save(order);

                    com.example.buensabor.entity.Payment paymentEntity = paymentRepository.findByOrderId(Long.parseLong(mpPayment.getExternalReference()))
                            .orElseThrow(() -> new RuntimeException("Payment not found by preferenceId: " + mpPayment.getExternalReference()));

                    paymentEntity.setPayForm(mpPayment.getPaymentTypeId());
                    paymentEntity.setPayStatus(PayStatus.approved);
                    paymentEntity.setApprovedDate(new Date());
                    paymentRepository.save(paymentEntity);
                }
            }

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing webhook: " + e.getMessage());
        }
    }


}
