package com.jwd.dao.entity.enums;

public enum ServiceType {
    ELECTRICAL("ELECTRICAL"),
    GAS("GAS"),
    ROOFING("ROOFING"),
    PAINTING("PAINTING"),
    PLUMBING("PLUMBING");

    private String name;

    ServiceType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
