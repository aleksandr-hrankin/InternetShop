package com.internet.shop.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.internet.shop.One;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShoppingCart {
    private Long id;
    private List<Product> products;
    private Long userId;

    public ShoppingCart(Long userId) {
        this.products = new ArrayList<>();
        this.userId = userId;
    }
}
