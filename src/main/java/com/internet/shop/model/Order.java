package com.internet.shop.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class Order {
    private Long id;
    private Long userId;
    private List<Product> products;

    public Order() {
    }

    public Order(Long userId) {
        this.userId = userId;
        this.products = new ArrayList<>();
    }

    public Order(Long id, Long userId, List<Product> products) {
        this.id = id;
        this.userId = userId;
        this.products = products;
    }
}
