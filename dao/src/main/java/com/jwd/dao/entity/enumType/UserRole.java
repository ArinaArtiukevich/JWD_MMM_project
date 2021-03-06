package com.jwd.dao.entity.enumType;

public enum UserRole {
    CLIENT("client"),
    WORKER("worker");

    private String name;

    UserRole(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
