package com.jwd.dao.repository;

import com.jwd.dao.entity.User;
import com.jwd.dao.entity.Registration;
import com.jwd.dao.entity.enums.ServiceStatus;
import com.jwd.dao.entity.enums.UserRole;
import com.jwd.dao.exception.DaoException;

public interface UserDao {
    boolean addUser(Registration registration) throws DaoException;

    boolean updateUserWithoutPassword(Long idUser, Registration registration) throws DaoException;

  //  boolean updateUserWithPassword(Long idUser, Registration registration) throws DaoException;

    Long findIdByLogin(String login) throws DaoException;

    boolean isLoginExist(String login) throws DaoException;

    User getUserByLogin(String login) throws DaoException;

    String findNameByLogin(String login) throws DaoException;

    User getUserById(Long idUser) throws DaoException;

    UserRole findRoleByID(Long idUser) throws DaoException;

}
