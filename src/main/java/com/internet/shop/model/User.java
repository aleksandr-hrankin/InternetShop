package com.internet.shop.model;

import java.util.Set;
import lombok.Data;

@Data
public class User {
    private Long id;
    private String name;
    private String login;
    private String password;
    private Set<Role> roles;
    private boolean deleted;

    public User() {
    }

    public User(String login, String password, Set<Role> roles) {
        this.login = login;
        this.password = password;
        this.roles = roles;
    }

    public User(Long id, String name, String login, String password, Set<Role> roles, boolean deleted) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
        this.roles = roles;
        this.deleted = deleted;
    }
}
