package com.internet.shop;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Product;
import com.internet.shop.service.ProductService;
import java.util.List;

public class Application {
    private static Injector injector = Injector.getInstance("com.internet.shop");

    public static void main(String[] args) {
        ProductService productService = (ProductService) injector.getInstance(ProductService.class);
        Product iphone = new Product("iPhone", 1000);
        Product xbox = new Product("xBox", 2000);
        Product ps4 = new Product("ps4", 1000);

        productService.create(iphone);
        productService.create(xbox);
        productService.create(ps4);
        List<Product> products = productService.getAll();

        System.out.println("Before changes");
        products.forEach(System.out::println);
        productService.delete(iphone.getId());
        xbox.setName("xbox360");
        xbox.setPrice(2500);
        productService.update(xbox);

        System.out.println("After changes");
        productService.getAll().forEach(System.out::println);
    }
}
