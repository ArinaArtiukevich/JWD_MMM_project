package com.jwd.service.serviceLogic.impl;

import com.jwd.dao.config.DataBaseConfig;
import com.jwd.dao.connection.impl.ConnectionPoolImpl;
import com.jwd.dao.entity.Registration;
import com.jwd.dao.entity.User;
import com.jwd.dao.entity.enums.UserRole;
import com.jwd.dao.repository.UserDao;
import com.jwd.dao.repository.LoginDao;
import com.jwd.dao.repository.impl.LoginDaoImpl;
import com.jwd.dao.repository.impl.UserDaoImpl;
import com.jwd.dao.exception.DaoException;
import com.jwd.service.exception.ServiceException;
import com.jwd.service.serviceLogic.UserService;
import com.jwd.service.validator.ServiceValidator;
import com.jwd.service.validator.UserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
    private final UserDao userDao = new UserDaoImpl(new ConnectionPoolImpl(new DataBaseConfig()));
    UserValidator userValidator = new UserValidator();
    ServiceValidator validator = new ServiceValidator();


    @Override
    public boolean register(Registration registration) throws ServiceException {
        logger.info("Start register(Registration registration).");
        UserDaoImpl clientDao = new UserDaoImpl(new ConnectionPoolImpl(new DataBaseConfig()));
        boolean testClient = false;
        boolean testRegistration = false;
        try {
            testRegistration = userValidator.validateData(registration);
            testClient = clientDao.addUser(registration);

        } catch (ServiceException | DaoException e) {
            logger.error("Invalid input parameters.");
            throw new ServiceException(e);
        }
        return testRegistration && testClient;

    }

    @Override
    public boolean checkLoginAndPassword(String login, String password) throws ServiceException {
        logger.info("Start checkLoginAndPassword(String login, String password).");
        boolean result = false;
        LoginDao loginDao = new LoginDaoImpl(new ConnectionPoolImpl(new DataBaseConfig()));
        validator.validate(login);
        validator.validate(password);
        try {
            result = loginDao.isLoginAndPasswordExist(login, password);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public String getUserNameByLogin(String login) throws ServiceException {
        logger.info("Start getClientNameByLogin(String login). Login = " + login);
        String name;
        try {
            name = userDao.findNameByLogin(login);
            validator.validate(name);
        }
        catch (DaoException e) {
            logger.error("Client name with login = " + login + " was not found.");
            throw new ServiceException(e);
        }
        logger.info("Client name with login = " + login + " was found. Name = " + name);
        return name;
    }

    @Override
    public Long getIdUserByLogin(String login) throws ServiceException {
        logger.info("Start getIdClientByLogin(String login). Login = " + login);
        Long idClient;
        try {
            LoginDao loginDao = new LoginDaoImpl(new ConnectionPoolImpl(new DataBaseConfig()));
            validator.validate(login);
            idClient = loginDao.findIdByLogin(login);
            validator.validate(idClient);
        } catch (DaoException e) {
            logger.error("Client id with login = " + login + " was not found.");
            throw new ServiceException(e);
        }
        logger.info("Client id with login = " + login + " was found. Id = " + idClient);
        return idClient;
    }

    @Override
    public User getUserById(Long idUser) throws ServiceException {
        logger.info("Start User getIdUserById(Long idUser). idUser = " + idUser);
        User user  = new User();
        try {
            validator.validate(idUser);
            user = userDao.getUserById(idUser);
        } catch (DaoException e) {
            logger.error("Client with idUser = " + idUser + " was not found.");
            throw new ServiceException(e);
        }
        logger.info("Client with idUser = " + idUser + " was found. Id = " + idUser);
        return user;
    }

    @Override
    public UserRole getRoleByID(Long idUser) throws ServiceException {
        logger.info("Start UserRole getRoleByID(Long idUser). idUser = " + idUser);
        UserRole userRole;
        try {
            validator.validate(idUser);
            userRole = userDao.findRoleByID(idUser);
            validator.validate(userRole);
        } catch (DaoException e) {
            logger.error("User role with idUser = " + idUser + " was not found.");
            throw new ServiceException(e);
        }
        logger.info("User role with idUser = " + idUser + " was found. Role = " + userRole.getName());
        return userRole;
    }
}
