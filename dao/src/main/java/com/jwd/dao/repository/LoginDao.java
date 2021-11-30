package com.jwd.dao.repository;

import com.jwd.dao.entity.UserDTO;
import com.jwd.dao.exception.DaoException;

public interface LoginDao {
    boolean add(UserDTO login) throws DaoException;

    Long findIdByLogin(String login) throws DaoException;

    String findPasswordById(Long idUser) throws DaoException;

    String findPasswordByLogin(String login) throws DaoException;

}
