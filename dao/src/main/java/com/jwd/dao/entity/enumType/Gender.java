package com.jwd.dao.entity.enums;

public enum Gender {
    MALE("male"),
    FEMALE("female"),
    UNDEFINED("undefined")
    ;
    String name;

    Gender(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
