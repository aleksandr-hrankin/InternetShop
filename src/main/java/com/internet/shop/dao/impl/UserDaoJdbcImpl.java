package com.internet.shop.dao.impl;

import com.internet.shop.dao.UserDao;
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Dao
public class UserDaoJdbcImpl implements UserDao {
    @Override
    public Optional<User> findByLogin(String login) {
        String query = "SELECT u.id_user, u.name, u.login, u.password, "
                + "u.salt, r.id_role, r.name role_name\n"
                + "FROM users u\n"
                + "JOIN users_roles USING (id_user)\n"
                + "JOIN roles r USING (id_role)\n"
                + "WHERE u.login = ? AND deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            User user = null;
            if (resultSet.next()) {
                user = getUserFromResultSet(resultSet);
            }
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to get user by login: " + login, e);
        }
    }

    @Override
    public User create(User user) {
        String query = "INSERT INTO users (name, login, password, salt) VALUES (?, ?, ?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement
                         = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getLogin());
            statement.setString(3, user.getPassword());
            statement.setBytes(4, user.getSalt());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getLong(1));
                statement.close();
                addUsersRoles(user, connection);
            }
            return user;
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to save user to database with id "
                    + user.getId(), e);
        }
    }

    @Override
    public Optional<User> get(Long id) {
        String query = "SELECT u.id_user, u.name, u.login, u.password, "
                + "u.salt, r.id_role, r.name role_name\n"
                + "FROM users u\n"
                + "JOIN users_roles ur USING(id_user)\n"
                + "JOIN roles r USING(id_role)\n"
                + "WHERE u.id_user = ? AND u.deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            User user = null;
            if (resultSet.next()) {
                user = getUserFromResultSet(resultSet);
            }
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to get user with id " + id, e);
        }
    }

    @Override
    public List<User> getAll() {
        String query = "SELECT u.id_user, u.name, u.login, u.password, "
                + "u.salt, r.id_role, r.name role_name\n"
                + "FROM users u\n"
                + "JOIN users_roles ur USING(id_user)\n"
                + "JOIN roles r USING(id_role)\n"
                + "WHERE u.deleted = FALSE\n"
                + "ORDER BY u.id_user;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query,
                         ResultSet.TYPE_SCROLL_INSENSITIVE,
                             ResultSet.CONCUR_READ_ONLY)) {
            ResultSet resultSet = statement.executeQuery();
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                User user = getUserFromResultSet(resultSet);
                users.add(user);
                resultSet.previous();
            }
            return users;
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to get all users", e);
        }
    }

    @Override
    public User update(User user) {
        String query = "UPDATE users SET name = ?, login = ?, password = ?, salt = ? "
                + "WHERE id_user = ? AND deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getLogin());
            statement.setString(3, user.getPassword());
            statement.setBytes(4, user.getSalt());
            statement.setLong(5, user.getId());
            statement.executeUpdate();
            statement.close();
            deleteUsersRoles(user.getId(), connection);
            addUsersRoles(user, connection);
            return user;
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to update user with id " + user.getId(), e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE users SET deleted = TRUE WHERE id_user = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to delete user with id " + id, e);
        }
    }

    private User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        Long userId = resultSet.getLong("id_user");
        String name = resultSet.getString("name");
        String login = resultSet.getString("login");
        String password = resultSet.getString("password");
        byte[] salt = resultSet.getBytes("salt");
        Set<Role> roles = new HashSet<>();
        do {
            Long roleId = resultSet.getLong("id_role");
            String roleName = resultSet.getString("role_name");
            roles.add(Role.of(roleId, roleName));
        } while (resultSet.next() && userId == resultSet.getLong("id_user"));
        return new User(userId, name, login, password, salt, roles);
    }

    private void addUsersRoles(User user, Connection connection) throws SQLException {
        String query = "INSERT INTO users_roles (id_user, id_role) VALUES (?, ?);";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, user.getId());
            Map<Role.RoleName, Long> roles = getRolesFromDb();
            for (Role role : user.getRoles()) {
                statement.setLong(2, roles.get(role.getRoleName()));
                statement.executeUpdate();
            }
        }
    }

    private void deleteUsersRoles(Long id, Connection connection) throws SQLException {
        String query = "DELETE FROM users_roles WHERE id_user = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }

    private Map<Role.RoleName, Long> getRolesFromDb() {
        String query = "SELECT * FROM ROLES;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            Map<Role.RoleName, Long> roles = new HashMap<>();
            while (resultSet.next()) {
                Long roleId = resultSet.getLong("id_role");
                String roleName = resultSet.getString("name");
                roles.put(Role.RoleName.valueOf(roleName), roleId);
            }
            return roles;
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to get roles from database", e);
        }
    }
}
