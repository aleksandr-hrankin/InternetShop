package com.internet.shop;

import com.internet.shop.dao.interfaces.ProductDao;
import com.internet.shop.dao.jdbc.ProductDaoJdbcImpl;
import com.internet.shop.model.Product;

public class Main {
    public static void main(String[] args) {
        ProductDao productDao = new ProductDaoJdbcImpl();
        System.out.println("create");
        Product iphone = new Product("Iphone 7", 599.99);
        iphone = productDao.create(iphone);
        System.out.println(iphone.toString());

        System.out.println("update");
        iphone.setName("Iphone 7 plus");
        iphone.setPrice(699.99);
        productDao.update(iphone);
        System.out.println(productDao.get(iphone.getId()).get());

        System.out.println("get all");
        productDao.getAll().forEach(System.out::println);

        System.out.println("delete");
        System.out.println(productDao.delete(iphone.getId()));
        productDao.getAll().forEach(System.out::println);
    }
}
