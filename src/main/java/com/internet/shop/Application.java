package com.internet.shop;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Product;
import com.internet.shop.model.ShoppingCart;
import com.internet.shop.model.User;
import com.internet.shop.service.interfaces.OrderService;
import com.internet.shop.service.interfaces.ProductService;
import com.internet.shop.service.interfaces.ShoppingCartService;
import com.internet.shop.service.interfaces.UserService;
import java.util.List;

public class Application {
    private static final Product IPHONE = new Product("iPhone", 1000);
    private static final Product XBOX = new Product("xBox", 2000);
    private static final Product PS_4 = new Product("ps4", 1000);
    private static final User BOB = new User("Bob", "bob_the_best", "12345");
    private static final User ALICE = new User("Alice", "alice", "pass123");
    private static final User JOHN = new User("JOHN", "john", "veryhardpass");

    private static Injector injector = Injector.getInstance("com.internet.shop");

    public static void main(String[] args) {
        testProductService();
        testUserService();
        testShoppingCartService();
        testOrderService();
    }

    private static void testProductService() {
        System.out.println("\n***** ***** ***** ***** Test Product CRUD ***** ***** ***** *****");
        ProductService productService = (ProductService) injector.getInstance(ProductService.class);

        productService.create(IPHONE);
        productService.create(XBOX);
        productService.create(PS_4);
        List<Product> products = productService.getAll();

        System.out.println("Before changes");
        products.forEach(System.out::println);
        productService.delete(IPHONE.getId());
        XBOX.setName("xbox360");
        XBOX.setPrice(2500);
        productService.update(XBOX);

        System.out.println("\nAfter changes");
        productService.getAll().forEach(System.out::println);
    }

    private static void testUserService() {
        System.out.println("\n***** ***** ***** ***** Test User CRUD ***** ***** ***** *****");
        UserService userService = (UserService) injector.getInstance(UserService.class);

        userService.create(BOB);
        userService.create(ALICE);
        userService.create(JOHN);
        List<User> users = userService.getAll();

        System.out.println("Users before changes");
        users.forEach(System.out::println);
        userService.delete(BOB.getId());
        ALICE.setLogin("alisa_kisa");
        userService.update(ALICE);

        System.out.println("\nUsers after changes");
        userService.getAll().forEach(System.out::println);
    }

    private static void testShoppingCartService() {
        System.out.println("\n***** ***** ***** ***** Test ShoppingCart ***** ***** ***** *****");
        ShoppingCartService shoppingCartService =
                (ShoppingCartService) injector.getInstance(ShoppingCartService.class);

        ShoppingCart cartBob = new ShoppingCart(BOB.getId());
        shoppingCartService.create(cartBob);

        shoppingCartService.addProduct(cartBob, IPHONE);
        shoppingCartService.addProduct(cartBob, XBOX);
        shoppingCartService.addProduct(cartBob, PS_4);

        ShoppingCart shoppingCart = shoppingCartService.getByUserId(BOB.getId());
        System.out.println("All Bob's products" + shoppingCart.toString());

        shoppingCartService.deleteProduct(cartBob, IPHONE);
        System.out.println("\nRemoved iphone" + shoppingCart.toString());
    }

    private static void testOrderService() {
        System.out.println("\n***** ***** ***** ***** Test OrderService ***** ***** ***** *****");
        OrderService orderService = (OrderService) injector.getInstance(OrderService.class);
        ShoppingCartService shoppingCartService =
                (ShoppingCartService) injector.getInstance(ShoppingCartService.class);

        ShoppingCart cartBob = shoppingCartService.getByUserId(BOB.getId());
        orderService.completeOrder(cartBob);
        System.out.println("Bob's order");
        orderService.getUserOrders(BOB.getId()).forEach(System.out::println);
        System.out.println("\nBob's shopping cart size: " + cartBob.getProducts().size());
    }
}
