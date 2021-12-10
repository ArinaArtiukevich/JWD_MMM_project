package com.jwd.service.serviceLogic.impl;

import com.jwd.dao.entity.Registration;
import com.jwd.dao.entity.User;
import com.jwd.dao.entity.UserDTO;
import com.jwd.dao.entity.enumType.UserRole;
import com.jwd.dao.exception.DaoException;
import com.jwd.dao.repository.UserDao;
import com.jwd.service.exception.ServiceException;
import com.jwd.service.serviceLogic.UserService;
import com.jwd.service.validator.ServiceValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);
    private final UserDao userDao;
    private final ServiceValidator validator;

    public UserServiceImpl(UserDao userDao, ServiceValidator validator) {
        this.userDao = userDao;
        this.validator = validator;
    }

    @Override
    public boolean register(Registration registration) throws ServiceException {
        LOGGER.info("Start register(Registration registration).");
        boolean isRegistered = false;
        try {
            if (validator.validateRegistrationData(registration)) {
                isRegistered = userDao.addUser(registration);
            }
        } catch (DaoException e) {
            LOGGER.error("Invalid input parameters.");
            throw new ServiceException(e);
        }
        return isRegistered;
    }

    @Override
    public boolean updateUserWithoutPassword(Long idUser, Registration userInfo) throws ServiceException {
        LOGGER.info("Start updateUserWithoutPassword(Long idUser, Registration userInfo).");
        boolean isUpdated = false;
        try {
            if (validator.validateUserWithoutPassword(userInfo)) {
                validator.validate(idUser);
                isUpdated = userDao.updateUserWithoutPassword(idUser, userInfo);
            }
        } catch (DaoException e) {
            LOGGER.error("Invalid input parameters.");
            throw new ServiceException(e);
        }
        return isUpdated;
    }

    @Override
    public boolean updateUserWithPassword(Long idUser, Registration userInfo) throws ServiceException {
        LOGGER.info("Start updateUserWithPassword(Long idUser, Registration userInfo).");
        boolean isUpdated = false;
        try {
            if (validator.validateUserWithPassword(userInfo)) {
                validator.validate(idUser);
                isUpdated = userDao.updateUserWithPassword(idUser, userInfo);
            }
        } catch (DaoException e) {
            LOGGER.error("Invalid input parameters.");
            throw new ServiceException(e);
        }
        return isUpdated;
    }

    @Override
    public boolean isLoginAndPasswordExist(String login, String password) throws ServiceException {
        LOGGER.info("Start checkLoginAndPassword(String login, String password).");
        boolean result = false;
        try {
            validator.validate(login);
            validator.validate(password);
            String userPassword = userDao.findPasswordByLogin(login);
            if (BCrypt.checkpw(password, userPassword)) {
                result = true;
                LOGGER.info("User exists.");
            }
        } catch (DaoException | IllegalArgumentException e) {
            throw new ServiceException(e);
        }
        return result;
    }


    @Override
    public User getUserById(Long idUser) throws ServiceException {
        LOGGER.info("Start User getIdUserById(Long idUser). idUser = " + idUser);
        User user = new User();
        try {
            validator.validate(idUser);
            user = userDao.getUserById(idUser);
        } catch (DaoException e) {
            LOGGER.error("User with idUser = " + idUser + " was not found.");
            throw new ServiceException(e);
        }
        LOGGER.info("User with idUser = " + idUser + " was found.");
        return user;
    }

    @Override
    public User getUserByLogin(String login) throws ServiceException {
        LOGGER.info("Start User getUserByLogin(String login). Login = " + login);
        User user = new User();
        try {
            validator.validate(login);
            user = userDao.getUserByLogin(login);
        } catch (DaoException e) {
            LOGGER.error("User with login = " + login + " was not found.");
            throw new ServiceException(e);
        }
        LOGGER.info("User with login = " + login + " was found.");
        return user;
    }

}
