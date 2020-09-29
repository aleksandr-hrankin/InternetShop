package com.internet.shop.model;

import java.util.Set;
import lombok.Data;

@Data
public class User {
    private Long id;
    private String name;
    private String login;
    private String password;
    private byte[] salt;
    private Set<Role> roles;

    public User() {
    }

    public User(Long id, String name, String login,
                String password, byte[] salt, Set<Role> roles) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
        this.salt = salt;
        this.roles = roles;
    }
}
