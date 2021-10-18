package com.jwd.dao.entity.enums;

public enum ServiceStatus {
    FREE("FREE"),
    IN_PROCESS("IN_PROCESS"),
    DONE( "DONE");

    private final String name;

    ServiceStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
