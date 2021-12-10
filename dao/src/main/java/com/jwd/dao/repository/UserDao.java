package com.jwd.dao.repository;

import com.jwd.dao.entity.User;
import com.jwd.dao.entity.Registration;
import com.jwd.dao.entity.enumType.UserRole;
import com.jwd.dao.exception.DaoException;

public interface UserDao {
    /**
     * adds new user
     *
     * @param registration is registration info about user
     * @return true when user was added
     * @throws DaoException
     */
    boolean addUser(Registration registration) throws DaoException;

    /**
     * updates user without password
     *
     * @param idUser       is id user
     * @param registration is info about user
     * @return true when user was updated
     * @throws DaoException
     */
    boolean updateUserWithoutPassword(Long idUser, Registration registration) throws DaoException;

    /**
     * updates user with password
     *
     * @param idUser       is id user
     * @param registration is info about user
     * @return true when user was updated
     * @throws DaoException
     */
    boolean updateUserWithPassword(Long idUser, Registration registration) throws DaoException;

    /**
     * gets user by login
     *
     * @param login
     * @return user
     * @throws DaoException
     */
    User getUserByLogin(String login) throws DaoException;

    /**
     * finds user name by login
     *
     * @param login
     * @return user name
     * @throws DaoException
     */
    String findNameByLogin(String login) throws DaoException;

    /**
     * gets user by user id user
     *
     * @param idUser
     * @return user
     * @throws DaoException
     */
    User getUserById(Long idUser) throws DaoException;

    /**
     * finds user role by id user
     *
     * @param idUser
     * @return user role
     * @throws DaoException
     */
    UserRole findRoleByID(Long idUser) throws DaoException;

    /**
     * deletes user by login
     *
     * @param login
     * @return true when user deleted
     * @throws DaoException
     */
    boolean deleteUserByLogin(String login) throws DaoException;

    /**
     * finds hashed password by login
     *
     * @param login
     * @return hashed password
     * @throws DaoException
     */
    String findPasswordByLogin(String login) throws DaoException;

}
