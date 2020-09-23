package com.internet.shop.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class ShoppingCart {
    private Long id;
    private Long userId;
    private List<Product> products;
    private boolean deleted;

    public ShoppingCart() {
    }

    public ShoppingCart(Long userId) {
        this.userId = userId;
        this.products = new ArrayList<>();
    }

    public ShoppingCart(Long id, Long userId, List<Product> products, boolean deleted) {
        this.id = id;
        this.userId = userId;
        this.products = products;
        this.deleted = deleted;
    }
}
