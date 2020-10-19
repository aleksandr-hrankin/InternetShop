package com.internet.shop.dao.impl;

import com.internet.shop.dao.ShoppingCartDao;
import com.internet.shop.exception.DataProcessingException;
import com.internet.shop.lib.Dao;
import com.internet.shop.model.Product;
import com.internet.shop.model.ShoppingCart;
import com.internet.shop.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class ShoppingCartDaoJdbcImpl implements ShoppingCartDao {
    @Override
    public Optional<ShoppingCart> getByUserId(Long userId) {
        String query = "SELECT sp.id_shopping_cart, sp.id_user, "
                + "p.id_product, p.name product_name, p.price\n"
                + "FROM shopping_carts sp\n"
                + "LEFT JOIN shopping_carts_products USING (id_shopping_cart)\n"
                + "LEFT JOIN products p USING (id_product)\n"
                + "WHERE sp.id_user = ? AND sp.deleted = FALSE;";
        return getShoppingCartByQuery(query, userId);
    }

    @Override
    public ShoppingCart create(ShoppingCart cart) {
        String query = "INSERT INTO shopping_carts (id_user) VALUES (?);";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement
                        = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, cart.getUserId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                cart.setId(resultSet.getLong(1));
                statement.close();
                addShoppingCartsProducts(cart, connection);
            }
            return cart;
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to save shopping cart to database with id "
                    + cart.getId(), e);
        }
    }

    @Override
    public Optional<ShoppingCart> get(Long id) {
        String query = "SELECT sc.id_shopping_cart, sc.id_user, "
                + "p.id_product, p.name product_name, p.price\n"
                + "FROM shopping_carts sc\n"
                + "JOIN shopping_carts_products scp USING (id_shopping_cart)\n"
                + "JOIN products p USING (id_product)\n"
                + "WHERE sc.id_shopping_cart = ? AND sc.deleted = FALSE;";
        return getShoppingCartByQuery(query, id);
    }

    @Override
    public List<ShoppingCart> getAll() {
        String query = "SELECT sc.id_shopping_cart, sc.id_user, "
                + "p.id_product, p.name product_name, p.price\n"
                + "FROM shopping_carts sc\n"
                + "JOIN shopping_carts_products scp USING (id_shopping_cart)\n"
                + "JOIN products p USING (id_product)\n"
                + "WHERE sc.deleted = FALSE\n"
                + "ORDER BY sc.id_shopping_cart;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query,
                         ResultSet.TYPE_SCROLL_INSENSITIVE,
                             ResultSet.CONCUR_READ_ONLY)) {
            ResultSet resultSet = statement.executeQuery();
            List<ShoppingCart> shoppingCarts = new ArrayList<>();
            while (resultSet.next()) {
                ShoppingCart shoppingCart = getShoppingCartFromResultSet(resultSet);
                shoppingCarts.add(shoppingCart);
                resultSet.previous();
            }
            return shoppingCarts;
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to get all shopping carts", e);
        }
    }

    @Override
    public ShoppingCart update(ShoppingCart cart) {
        String query = "UPDATE shopping_carts SET id_user = ? "
                + "WHERE id_shopping_cart = ? AND deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, cart.getUserId());
            statement.setLong(2, cart.getId());
            statement.executeUpdate();
            statement.close();
            deleteShoppingCartsProducts(cart.getId(), connection);
            addShoppingCartsProducts(cart, connection);
            return cart;
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to update shopping cart with id "
                    + cart.getId(), e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE shopping_carts SET deleted = TRUE WHERE id_shopping_cart = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            if (statement.executeUpdate() < 1) {
                return false;
            }
            statement.close();
            return deleteShoppingCartsProducts(id, connection);
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to delete shopping cart with id " + id, e);
        }
    }

    private ShoppingCart getShoppingCartFromResultSet(ResultSet resultSet) throws SQLException {
        Long cartId = resultSet.getLong("id_shopping_cart");
        Long userId = resultSet.getLong("id_user");
        List<Product> products = new ArrayList<>();
        if (resultSet.getObject("id_product", Long.class) != null) {
            do {
                Long productId = resultSet.getLong("id_product");
                String name = resultSet.getString("product_name");
                double price = resultSet.getDouble("price");
                products.add(new Product(productId, name, price));
            } while (resultSet.next() && cartId == resultSet.getLong("id_shopping_cart"));
        }
        return new ShoppingCart(cartId, userId, products);
    }

    private void addShoppingCartsProducts(ShoppingCart cart, Connection connection)
            throws SQLException {
        String query = "INSERT INTO shopping_carts_products (id_shopping_cart, id_product) "
                + "VALUES (?, ?);";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, cart.getId());
            for (Product product : cart.getProducts()) {
                statement.setLong(2, product.getId());
                statement.executeUpdate();
            }
        }
    }

    private boolean deleteShoppingCartsProducts(Long shoppingCartId, Connection connection)
            throws SQLException {
        String query = "DELETE FROM shopping_carts_products WHERE id_shopping_cart = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, shoppingCartId);
            return statement.executeUpdate() > 0;
        }
    }

    public Optional<ShoppingCart> getShoppingCartByQuery(String query, Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            ShoppingCart shoppingCart = null;
            if (resultSet.next()) {
                shoppingCart = getShoppingCartFromResultSet(resultSet);
            }
            return Optional.ofNullable(shoppingCart);
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to get shopping cart", e);
        }
    }
}
