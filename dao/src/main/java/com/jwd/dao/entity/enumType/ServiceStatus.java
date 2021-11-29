package com.jwd.dao.entity.enumType;

public enum ServiceStatus {
    FREE("free"),
    IN_PROCESS("in_process"),
    DONE( "done"),
    APPROVED( "approved");

    private final String name;

    ServiceStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
