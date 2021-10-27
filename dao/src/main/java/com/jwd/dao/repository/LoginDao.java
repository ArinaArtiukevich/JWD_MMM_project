package com.jwd.dao.repository;

import com.jwd.dao.entity.Login;
import com.jwd.dao.entity.User;
import com.jwd.dao.entity.enums.UserRole;
import com.jwd.dao.exception.DaoException;

public interface LoginDao {
    boolean add(Login login) throws DaoException;

    boolean deleteLoginById(Integer id) throws DaoException;

    Long findIdByLogin(String login) throws DaoException;

    Boolean isLoginAndPasswordExist(String login, String password);

}
