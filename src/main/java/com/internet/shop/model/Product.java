package com.internet.shop.model;

import lombok.Data;

@Data
public class Product {
    private Long id;
    private String name;
    private double price;
    private boolean deleted;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public Product(Long id, String name, double price, boolean deleted) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.deleted = deleted;
    }

}
