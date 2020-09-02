package com.internet.shop;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Product;
import com.internet.shop.service.ProductService;

public class Application {
    private static Injector injector = Injector.getInstance("com.internet.shop");

    public static void main(String[] args) {
        ProductService productService = (ProductService) injector.getInstance(ProductService.class);
        Product iPhone = new Product("Iphone", 1000);
        Product xBox = new Product("xBox", 2000);
        Product ps4 = new Product("ps4", 1000);
        Product xBox360 = new Product("xbox360", 2500);
        xBox360.setId(2L);
        productService.create(iPhone);
        productService.create(xBox);
        productService.create(ps4);
        productService.delete(1L);
        productService.getAll().forEach(System.out::println);
        productService.update(xBox360);
        System.out.println(productService.get(2L));
    }
}
