package com.microservice.example.orders;

import ie.ul.microservices.kernel.api.KernelMicroservice;
import org.springframework.boot.SpringApplication;

@KernelMicroservice
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}