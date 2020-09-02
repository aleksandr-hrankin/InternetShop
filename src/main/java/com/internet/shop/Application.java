package com.internet.shop;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Product;
import com.internet.shop.service.ProductService;

public class Application {
    private static Injector injector = Injector.getInstance("com.internet.shop");

    public static void main(String[] args) {
        ProductService productService = (ProductService) injector.getInstance(ProductService.class);
        Product xbox360 = new Product("xbox360", 2500);
        xbox360.setId(2L);
        productService.create(new Product("iPhone", 1000));
        productService.create(new Product("xBox", 2000));
        productService.create(new Product("ps4", 1000));
        productService.delete(1L);
        productService.getAll().forEach(System.out::println);
        productService.update(xbox360);
        System.out.println(productService.get(2L));
    }
}
