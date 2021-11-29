package com.jwd.controller.entity;

import com.jwd.controller.entity.enumType.AnswerType;

import java.util.Objects;

public class CommandAnswer {
    String path;
    AnswerType answerType;

    public CommandAnswer() {
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public AnswerType getAnswerType() {
        return answerType;
    }

    public void setAnswerType(AnswerType answerType) {
        this.answerType = answerType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommandAnswer answer = (CommandAnswer) o;
        return Objects.equals(path, answer.path) &&
                answerType == answer.answerType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, answerType);
    }
}
