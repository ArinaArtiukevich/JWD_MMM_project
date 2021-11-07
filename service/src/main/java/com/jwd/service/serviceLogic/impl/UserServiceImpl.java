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
import com.jwd.service.validator.RegistrationValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
    private final UserDao userDao = new UserDaoImpl(new ConnectionPoolImpl(new DataBaseConfig()));

    @Override
    public boolean register(Registration registration) throws ServiceException {
        logger.info("Start register(Registration registration).");
        //TODO ENCODED PASSWORD

        UserDaoImpl clientDao = new UserDaoImpl(new ConnectionPoolImpl(new DataBaseConfig()));
        boolean testClient = false;
        boolean testRegistration = false;
        try {
            testRegistration = RegistrationValidator.checkData(registration);
            testClient = clientDao.addUser(registration);

        } catch (ServiceException | DaoException e) {
            logger.error("Invalid input parameters.");
            throw new ServiceException(e);
        }
        return testRegistration && testClient;

    }

    @Override
    public boolean checkLoginAndPassword(String login, String password) {
        logger.info("Start checkLoginAndPassword(String login, String password).");
        boolean result;
        LoginDao loginDao = new LoginDaoImpl(new ConnectionPoolImpl(new DataBaseConfig()));
        result = loginDao.isLoginAndPasswordExist(login, password);
        if (result) {
            logger.info("Correct login and password.");
        }  else {
            logger.info("Incorrect login or password.");
        }
        return result;
    }

    @Override
    public String getUserNameByLogin(String login) throws ServiceException {
        logger.info("Start getClientNameByLogin(String login). Login = " + login);
        String name;
        try {
            name = userDao.findNameByLogin(login);
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
            idClient = loginDao.findIdByLogin(login);
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
            userRole = userDao.findRoleByID(idUser);
        } catch (DaoException e) {
            logger.error("User role with idUser = " + idUser + " was not found.");
            throw new ServiceException(e);
        }
        logger.info("User role with idUser = " + idUser + " was found. Role = " + userRole.getName());
        return userRole;
    }
}
