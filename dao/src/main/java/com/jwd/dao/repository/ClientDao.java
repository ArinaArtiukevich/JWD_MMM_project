package com.jwd.dao.repository;

import com.jwd.dao.entity.User;
import com.jwd.dao.entity.Registration;
import com.jwd.dao.exception.DaoException;

public interface ClientDao {
    boolean addUser(Registration registration) throws DaoException;

    boolean deleteUserById(Integer id) throws DaoException;
    Long findIdByLogin(String login) throws DaoException;

    boolean isLoginExist(String login) throws DaoException;

    User getUserByLogin(String login) throws DaoException;

    String findNameByLogin(String login) throws DaoException;


}
