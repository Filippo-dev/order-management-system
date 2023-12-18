package com.company.ordermanagementsystem.controller;

import com.company.ordermanagementsystem.dto.CreateOrderRequest;
import com.company.ordermanagementsystem.dto.OrderDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {


    @GetMapping
    public ResponseEntity<List<OrderDTO>> getOrders() {
        return ResponseEntity
                .status(200)
                .body(List.of());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrder(@PathVariable UUID id) {
        return ResponseEntity
                .status(200)
                .body(new OrderDTO(id, UUID.randomUUID(), "CREATED", null, List.of(), 0.0));
    }

    @PostMapping
    public ResponseEntity<UUID> createOrder(@RequestBody CreateOrderRequest createOrderRequest) {
        return ResponseEntity
                .status(201)
                .body(UUID.randomUUID());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable UUID id) {
        return ResponseEntity
                .status(204)
                .build();
    }


}
