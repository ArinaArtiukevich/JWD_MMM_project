package com.jwd.controller.entity.enums;

public enum AnswerType {
    FORWARD("forward"),
    REDIRECT("redirect");

    String name;

    AnswerType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}