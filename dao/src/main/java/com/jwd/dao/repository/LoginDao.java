package com.jwd.dao.repository;

import com.jwd.dao.entity.UserDTO;
import com.jwd.dao.exception.DaoException;

public interface LoginDao {
    boolean add(UserDTO login) throws DaoException;

    boolean updateUserDTO(UserDTO userDTO) throws DaoException;

    boolean deleteLoginById(Integer id) throws DaoException;

    Long findIdByLogin(String login) throws DaoException;

    String findPasswordById(Long idUser) throws DaoException;

    Boolean isLoginAndPasswordExist(String login, String password) throws DaoException;

}
