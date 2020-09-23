package com.internet.shop.controller.injector;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Product;
import com.internet.shop.model.Role;
import com.internet.shop.model.ShoppingCart;
import com.internet.shop.model.User;
import com.internet.shop.service.interfaces.ProductService;
import com.internet.shop.service.interfaces.ShoppingCartService;
import com.internet.shop.service.interfaces.UserService;
import java.io.IOException;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InjectDataController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("com.internet.shop");
    private ProductService productService =
            (ProductService) injector.getInstance(ProductService.class);
    private ShoppingCartService shoppingCartService =
            (ShoppingCartService) injector.getInstance(ShoppingCartService.class);
    private UserService userService = (UserService) injector.getInstance(UserService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
//        productService.create(new Product("iPhone X", 700.00));
//        productService.create(new Product("iPhone 7", 500.00));
//        productService.create(new Product("xBox 360", 480.00));
//        productService.create(new Product("Sony Playstation 4", 520.00));
//        productService.create(new Product("Samsung A50", 300.00));
//
//        User userBob = new User("Bob", "admin", "1");
//        userBob.setRoles(Set.of(Role.of("ADMIN")));
//        User userAlice = new User("Alice", "user", "1");
//        userService.create(userBob);
//        userAlice.setRoles(Set.of(Role.of("USER")));
//        userService.create(userAlice);
//
//        shoppingCartService.create(new ShoppingCart(userBob.getId()));
//        shoppingCartService.create(new ShoppingCart(userAlice.getId()));

        req.getRequestDispatcher("/WEB-INF/view/injector/InjectData.jsp").forward(req, resp);
    }
}
