package com.internet.shop.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.Getter;

@Data
public class ShoppingCart {
    private Long id;
    private Long userId;
    private List<Product> products;
    private boolean deleted;

    public ShoppingCart(Long userId) {
        this.products = new ArrayList<>();
        this.userId = userId;
    }

    public ShoppingCart(Long id, Long userId, List<Product> products) {
        this.id = id;
        this.userId = userId;
        this.products = products;
    }

    public ShoppingCart(Long id, Long userId, List<Product> products, boolean deleted) {
        this.id = id;
        this.userId = userId;
        this.products = products;
        this.deleted = deleted;
    }
}
