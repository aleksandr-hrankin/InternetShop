package com.internet.shop.dao.jdbc;

import com.internet.shop.dao.interfaces.UserDao;
import com.internet.shop.exception.DataProcessingException;
import com.internet.shop.lib.Dao;
import com.internet.shop.model.Role;
import com.internet.shop.model.User;
import com.internet.shop.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Dao
public class UserDaoJdbcImpl implements UserDao {
    @Override
    public Optional<User> findByLogin(String login) {
        String query = "SELECT u.id_user, u.name, u.login, u.password, u.deleted, r.name role_name\n" +
                "FROM users u\n" +
                "JOIN users_roles USING (id_user)\n" +
                "JOIN roles r USING (id_role)\n" +
                "WHERE u.login = ? AND deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            User user = null;
            if (resultSet.next()) {
                user = getUserFromResultSet(resultSet);
            }
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            throw new DataProcessingException("", e);
        }
    }

    @Override
    public User create(User user) {
        String query = "INSERT INTO users (name, login, password) VALUES (?, ?, ?);";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getName());
            statement.setString(2, user.getLogin());
            statement.setString(3, user.getPassword());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getLong(1));
                addUsersRoles(user, connection);
            }
            return user;
        } catch (SQLException e) {
            throw new DataProcessingException("", e);
        }
    }

    @Override
    public Optional<User> get(Long id) {
        String query = "SELECT u.id_user, u.name, u.login, u.password, u.deleted, r.name role_name\n" +
                "FROM users u\n" +
                "JOIN users_roles ur USING(id_user)\n" +
                "JOIN roles r USING(id_role)\n" +
                "WHERE u.id_user = ? AND deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            User user = null;
            if (resultSet.next()) {
                user = getUserFromResultSet(resultSet);
            }
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            throw new DataProcessingException("", e);
        }
    }

    @Override
    public List<User> getAll() {
        String query = "SELECT u.id_user, u.name, u.login, u.password, u.deleted, r.name role_name\n" +
                "FROM users u\n" +
                "JOIN users_roles ur USING(id_user)\n" +
                "JOIN roles r USING(id_role)\n" +
                "WHERE u.deleted = FALSE\n" +
                "ORDER BY u.id_user;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet resultSet = statement.executeQuery();
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                User user = getUserFromResultSet(resultSet);
                users.add(user);
                resultSet.previous();
            }
            return users;
        } catch (SQLException e) {
            throw new DataProcessingException("", e);
        }
    }

    @Override
    public User update(User user) {
        String query = "UPDATE users SET name = ?, login = ?, password = ? WHERE id_user = ? AND deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.getName());
            statement.setString(2, user.getLogin());
            statement.setString(3, user.getPassword());
            statement.setLong(4, user.getId());
            statement.executeUpdate();
            deleteUsersRoles(user.getId(), connection);
            addUsersRoles(user, connection);
            return user;
        } catch (SQLException e) {
            throw new DataProcessingException("", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE users SET deleted = TRUE WHERE id_user = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            statement.executeUpdate();
            deleteUsersRoles(id, connection);
            return true;
        } catch (SQLException e) {
            throw new DataProcessingException("", e);
        }
    }

    private User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id_user");
        String name = resultSet.getString("name");
        String login = resultSet.getString("login");
        String password = resultSet.getString("password");
        boolean deleted = resultSet.getBoolean("deleted");
        Set<Role> roles = new HashSet<>();
        do {
            roles.add(Role.of(resultSet.getString("role_name")));
        } while (resultSet.next() && id == resultSet.getLong("id_user"));
        return new User(id, name, login, password, roles, deleted);
    }

    private void addUsersRoles(User user, Connection connection) throws SQLException {
        String query = "INSERT INTO users_roles (id_user, id_role) VALUES (?, ?);";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, user.getId());
        for (Role role : user.getRoles()) {
            statement.setLong(2, role.getRoleName().ordinal() + 1);
            statement.executeUpdate();
        }
    }

    private void deleteUsersRoles(Long id, Connection connection) throws SQLException {
        String query = "DELETE FROM users_roles WHERE id_user = ?;";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, id);
        statement.executeUpdate();
    }
}
