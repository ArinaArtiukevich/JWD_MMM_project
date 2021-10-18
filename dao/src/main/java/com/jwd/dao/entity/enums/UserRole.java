package com.jwd.dao.entity.enums;

public enum UserRole {
    CLIENT("client"),
    EXECUTOR("executor");

    private String name;

    UserRole(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
}
