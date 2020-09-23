package com.internet.shop.dao.jdbc;

import com.internet.shop.dao.interfaces.OrderDao;
import com.internet.shop.exception.DataProcessingException;
import com.internet.shop.lib.Dao;
import com.internet.shop.model.Order;
import com.internet.shop.model.Product;
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
public class OrderDaoJdbcImpl implements OrderDao {
    @Override
    public List<Order> getUserOrders(Long userId) {
        String query = "SELECT o.id_order, o.id_user, o.deleted order_deleted, "
                + "p.id_product, p.name product_name, p.price, p.deleted product_deleted  \n"
                + "FROM orders o\n"
                + "JOIN orders_products op USING (id_order)\n"
                + "JOIN products p USING (id_product)\n"
                + "WHERE o.id_user = ? AND o.deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            List<Order> orders = new ArrayList<>();
            while (resultSet.next()) {
                orders.add(getOrderFromResultSet(resultSet));
                resultSet.previous();
            }
            return orders;
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to get user orders by id" + userId, e);
        }
    }

    @Override
    public Order create(Order order) {
        String query = "INSERT INTO orders (id_user) VALUES (?);";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement
                    = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, order.getUserId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                order.setId(resultSet.getLong(1));
                addOrdersProducts(order, connection);
            }
            return order;
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to save order to database with id"
                    + order.getId(), e);
        }
    }

    @Override
    public Optional<Order> get(Long id) {
        String query = "SELECT o.id_order, o.id_user, o.deleted order_deleted, "
                + "p.id_product, p.name product_name, p.price, p.deleted product_deleted\n"
                + "FROM orders o \n"
                + "JOIN orders_products USING (id_order)\n"
                + "JOIN products p USING (id_product)\n"
                + "WHERE o.id_order = ?, o.deleted = FALSE AND p.deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            Order order = null;
            if (resultSet.next()) {
                order = getOrderFromResultSet(resultSet);
            }
            return Optional.ofNullable(order);
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to get order by id " + id, e);
        }
    }

    @Override
    public List<Order> getAll() {
        String query = "SELECT o.id_order, o.id_user, o.deleted order_deleted, "
                + "p.id_product, p.name product_name, p.price, p.deleted product_deleted\n"
                + "FROM orders o \n"
                + "JOIN orders_products USING (id_order)\n"
                + "JOIN products p USING (id_product)\n"
                + "WHERE o.deleted = FALSE AND p.deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet resultSet = statement.executeQuery();
            List<Order> orders = new ArrayList<>();
            while (resultSet.next()) {
                Order order = getOrderFromResultSet(resultSet);
                orders.add(order);
                resultSet.previous();
            }
            return orders;
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to gel all orders", e);
        }
    }

    @Override
    public Order update(Order order) {
        String query = "UPDATE orders SET id_user = ?, deleted = ? WHERE id_order = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, order.getUserId());
            statement.setBoolean(2, order.isDeleted());
            statement.setLong(3, order.getId());
            statement.executeUpdate();
            deleteOrdersProducts(order.getId(), connection);
            addOrdersProducts(order, connection);
            return order;
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to update order with id " + order.getId(), e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE orders SET deleted = TRUE WHERE id_order = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            statement.executeUpdate();
            deleteOrdersProducts(id, connection);
            return true;
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to delete order with id " + id, e);
        }
    }

    private Order getOrderFromResultSet(ResultSet resultSet) throws SQLException {
        Long orderId = resultSet.getLong("id_order");
        Long userId = resultSet.getLong("id_user");
        boolean orderDeleted = resultSet.getBoolean("order_deleted");
        List<Product> products = new ArrayList<>();
        do {
            Long productId = resultSet.getLong("id_product");
            String name = resultSet.getString("product_name");
            double price = resultSet.getDouble("price");
            boolean productDeleted = resultSet.getBoolean("product_deleted");
            products.add(new Product(productId, name, price, productDeleted));
        } while (resultSet.next() && orderId == resultSet.getLong("id_order"));
        return new Order(orderId, userId, products, orderDeleted);
    }

    private void addOrdersProducts(Order order, Connection connection) throws SQLException {
        String query = "INSERT INTO orders_products (id_order, id_product) VALUES (?, ?);";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, order.getId());
            for (Product product : order.getProducts()) {
                statement.setLong(2, product.getId());
                statement.executeUpdate();
            }
        }
    }

    private void deleteOrdersProducts(Long orderId, Connection connection) throws SQLException {
        String query = "DELETE FROM orders_products WHERE id_order = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, orderId);
            statement.executeUpdate();
        }
    }
}
