package com.internet.shop.service.impl;

import com.internet.shop.dao.ShoppingCartDao;
import com.internet.shop.lib.Inject;
import com.internet.shop.lib.Service;
import com.internet.shop.model.Product;
import com.internet.shop.model.ShoppingCart;
import com.internet.shop.service.ShoppingCartService;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Inject
    private ShoppingCartDao shoppingCartDao;

    @Override
    public ShoppingCart create(ShoppingCart shoppingCart) {
        shoppingCartDao.create(shoppingCart);
        return shoppingCart;
    }

    @Override
    public ShoppingCart addProduct(ShoppingCart shoppingCart, Product product) {
        shoppingCartDao.get(shoppingCart.getId()).get()
                .getProducts()
                .add(product);
        return shoppingCart;
    }

    @Override
    public boolean deleteProduct(ShoppingCart shoppingCart, Product product) {
        return shoppingCartDao.get(shoppingCart.getId()).get()
                .getProducts()
                .removeIf(p -> p.getId().equals(product.getId()));
    }

    @Override
    public void clear(ShoppingCart shoppingCart) {
        shoppingCartDao.get(shoppingCart.getId()).get()
                .getProducts()
                .clear();
    }

    @Override
    public ShoppingCart getByUserId(Long userId) {
        return shoppingCartDao.getAll().stream()
                .filter(cart -> cart.getUserId().equals(userId))
                .findFirst().get();
    }

    @Override
    public boolean delete(ShoppingCart shoppingCart) {
        return shoppingCartDao.delete(shoppingCart.getId());
    }
}
