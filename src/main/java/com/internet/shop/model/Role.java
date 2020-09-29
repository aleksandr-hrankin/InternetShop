package com.internet.shop.model;

import lombok.Data;

@Data
public class Role {
    private Long id;
    private RoleName roleName;

    public Role() {
    }

    public Role(RoleName roleName) {
        this.roleName = roleName;
    }

    private Role(Long id, RoleName roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    public static Role of(String roleName) {
        return new Role(RoleName.valueOf(roleName));
    }

    public static Role of(Long id, String roleName) {
        return new Role(id, RoleName.valueOf(roleName));
    }

    public enum RoleName {
        ADMIN,
        USER
    }
}
