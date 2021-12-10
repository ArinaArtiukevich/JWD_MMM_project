package com.jwd.service.serviceLogic;

import com.jwd.dao.entity.Registration;
import com.jwd.dao.entity.User;
import com.jwd.dao.entity.enumType.UserRole;
import com.jwd.service.exception.ServiceException;

public interface UserService {
    /**
     * registers new user
     *
     * @param registration is registration info about user
     * @return true when user was registered
     * @throws ServiceException
     */
    boolean register(Registration registration) throws ServiceException;

    /**
     * updates user without password
     *
     * @param idUser
     * @param userInfo is info about user
     * @return true when user was updated
     * @throws ServiceException
     */
    boolean updateUserWithoutPassword(Long idUser, Registration userInfo) throws ServiceException;

    /**
     * updates user with password
     *
     * @param idUser
     * @param userInfo is info about user
     * @return true when user was updated
     * @throws ServiceException
     */
    boolean updateUserWithPassword(Long idUser, Registration userInfo) throws ServiceException;

    /**
     * checks if login and password exists
     *
     * @param login
     * @param password
     * @return true if user with such login and password was found
     * @throws ServiceException
     */
    boolean isLoginAndPasswordExist(String login, String password) throws ServiceException;

    /**
     * gets user by id
     *
     * @param idUser
     * @return user with required id
     * @throws ServiceException
     */
    User getUserById(Long idUser) throws ServiceException;

    /**
     * gets user by login
     *
     * @param login
     * @return user with required login
     * @throws ServiceException
     */
    User getUserByLogin(String login) throws ServiceException;
}
