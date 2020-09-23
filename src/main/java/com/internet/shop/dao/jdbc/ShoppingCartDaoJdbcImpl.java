package com.internet.shop.dao.jdbc;

import com.internet.shop.dao.interfaces.ShoppingCartDao;
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
        String query = "SELECT sp.id_shopping_cart, sp.id_user, sp.deleted cart_deleted, "
                + "p.id_product, p.name product_name, p.price, p.deleted product_deleted\n"
                + "FROM shopping_carts sp\n"
                + "LEFT JOIN shopping_carts_products USING (id_shopping_cart)\n"
                + "LEFT JOIN products p USING (id_product)\n"
                + "WHERE sp.id_user = ? AND sp.deleted = FALSE;";
        return getShoppingCartByQuery(query, userId);
    }

    @Override
    public ShoppingCart create(ShoppingCart cart) {
        String query = "INSERT INTO shopping_carts (id_user) VALUES (?);";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement
                    = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, cart.getUserId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                cart.setId(resultSet.getLong(1));
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
        String query = "SELECT sc.id_shopping_cart, sc.id_user, sc.deleted cart_deleted, "
                + "p.id_product, p.name product_name, p.price, p.deleted product_deleted\n"
                + "FROM shopping_carts sc\n"
                + "JOIN shopping_carts_products scp USING (id_shopping_cart)\n"
                + "JOIN products p USING (id_product)\n"
                + "WHERE sc.id_shopping_cart = ? AND sc.deleted = FALSE AND p.deleted = FALSE;";
        return getShoppingCartByQuery(query, id);
    }

    @Override
    public List<ShoppingCart> getAll() {
        String query = "SELECT sc.id_shopping_cart, sc.id_user, sc.deleted cart_deleted, "
                + "p.id_product, p.name product_name, p.price, p.deleted product_deleted\n"
                + "FROM shopping_carts sc\n"
                + "JOIN shopping_carts_products scp USING (id_shopping_cart)\n"
                + "JOIN products p USING (id_product)\n"
                + "WHERE sc.deleted = FALSE AND p.deleted = FALSE\n"
                + "ORDER BY id_shopping_cart;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
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
        String query = "UPDATE shopping_carts SET id_user = ?, deleted = ? "
                + "WHERE id_shopping_cart = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, cart.getUserId());
            statement.setBoolean(2, cart.isDeleted());
            statement.setLong(3, cart.getId());
            statement.executeUpdate();
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
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            statement.executeUpdate();
            deleteShoppingCartsProducts(id, connection);
            return true;
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to delete shopping cart with id " + id, e);
        }
    }

    private ShoppingCart getShoppingCartFromResultSet(ResultSet resultSet) throws SQLException {
        Long cartId = resultSet.getLong("id_shopping_cart");
        Long userId = resultSet.getLong("id_user");
        boolean cartDeleted = resultSet.getBoolean("cart_deleted");
        List<Product> products = new ArrayList<>();
        if (resultSet.getLong("id_product") != 0) {
            do {
                Long productId = resultSet.getLong("id_product");
                String name = resultSet.getString("product_name");
                double price = resultSet.getDouble("price");
                boolean productDeleted = resultSet.getBoolean("product_deleted");
                products.add(new Product(productId, name, price, productDeleted));
            } while (resultSet.next() && cartId == resultSet.getLong("id_shopping_cart"));
        }
        return new ShoppingCart(cartId, userId, products, cartDeleted);
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

    private void deleteShoppingCartsProducts(Long shoppingCartId, Connection connection)
            throws SQLException {
        String query = "DELETE FROM shopping_carts_products WHERE id_shopping_cart = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, shoppingCartId);
            statement.executeUpdate();
        }
    }

    public Optional<ShoppingCart> getShoppingCartByQuery(String query, Long id) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
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
