package com.internet.shop;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Product;
import com.internet.shop.service.interfaces.ProductService;

public class Main {
    private static final Injector injector = Injector.getInstance("com.internet.shop");
    private static ProductService productService =
            (ProductService) injector.getInstance(ProductService.class);

    public static void main(String[] args) {
        System.out.println("create");
        Product iphone = new Product("Iphone 7", 599.99);
        iphone = productService.create(iphone);
        System.out.println(iphone.toString());

        System.out.println("update");
        iphone.setName("Iphone 7 plus");
        iphone.setPrice(699.99);
        productService.update(iphone);
        System.out.println(productService.get(iphone.getId()));

        System.out.println("get all");
        productService.getAll().forEach(System.out::println);

        System.out.println("delete");
        System.out.println(productService.delete(iphone.getId()));
        productService.getAll().forEach(System.out::println);
    }
}
