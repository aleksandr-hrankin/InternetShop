package db;

import model.Order;
import model.Product;
import model.ShoppingCart;
import model.User;

import java.util.ArrayList;
import java.util.List;

public class Storage {
    private static List<Order> orders = new ArrayList<>();
    private static List<Product> products = new ArrayList<>();
    private static List<ShoppingCart> shoppingCarts = new ArrayList<>();
    private static List<User> users = new ArrayList<>();
}
