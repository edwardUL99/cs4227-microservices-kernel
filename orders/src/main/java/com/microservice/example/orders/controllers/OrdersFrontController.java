package com.microservice.example.orders.controllers;

import ie.ul.microservices.kernel.api.client.FrontController;
import ie.ul.microservices.kernel.api.client.HealthResponse;
import ie.ul.microservices.kernel.api.client.Shutdown;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/front")
public class OrdersFrontController implements FrontController {
    private final Shutdown shutdown;
    private final Logger log = LoggerFactory.getLogger(OrdersFrontController.class);

    @Autowired
    public OrdersFrontController(Shutdown shutdown) {
        this.shutdown = shutdown;
    }

    @Override
    @GetMapping("/health")
    public ResponseEntity<HealthResponse> health() {
        return null;
    }

    @PostMapping("/shutdown")
    @Override
    public void shutdown() {
        log.info("Instructed to shutdown by microservice kernel");
        shutdown.execute();
    }
}
