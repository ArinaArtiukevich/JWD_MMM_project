package com.jwd.dao.exception;

public class DaoException extends Exception {
    public DaoException(String message) {
        super(message);
    }

    public DaoException(Exception exception) {
        super(exception);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
