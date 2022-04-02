package com.microservice.example.orders.controllers;

import com.microservice.example.orders.entities.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class OrdersController {
    private List<Order> orders;

    public OrdersController() {
        orders = new ArrayList<>(List.of(
                new Order(UUID.randomUUID().toString(), "Apples", 1.50f, 5),
                new Order(UUID.randomUUID().toString(), "TV", 350.50f, 1),
                new Order(UUID.randomUUID().toString(), "Phone", 800.99f, 1)
        ));
    }

    @RequestMapping("/list")
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(orders);
    }

    @PostMapping("/buy")
    public ResponseEntity<?> buy(@RequestBody Order order) {
        order.id = UUID.randomUUID().toString();
        orders.add(order);

        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }
}
