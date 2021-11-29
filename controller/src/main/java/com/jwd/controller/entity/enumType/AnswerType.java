package com.jwd.controller.entity.enumType;

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