package com.microservice.example.orders.entities;

public class Order {
    public String id;
    public String name;
    public float price;
    public int quantity;

    public Order(String id, String name, float price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}
