package com.jwd.service.serviceLogic;

import com.jwd.dao.entity.User;
import com.jwd.dao.entity.enums.UserRole;
import com.jwd.dao.repository.UserDao;
import com.jwd.dao.repository.LoginDao;
import com.jwd.dao.repository.impl.LoginDaoImpl;
import com.jwd.dao.repository.impl.UserDaoImpl;
import com.jwd.dao.exception.DaoException;
import com.jwd.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserService {

    private static final Logger logger = LogManager.getLogger(UserService.class);

    public UserService() {
    }

    public static boolean checkLoginAndPassword(String login, String password) {
        logger.info("Start checkLoginAndPassword(String login, String password).");
        boolean result;
        LoginDao loginDao = new LoginDaoImpl();
        result = loginDao.isLoginAndPasswordExist(login, password);
        if (result) {
            logger.info("Correct login and password.");
        }  else {
            logger.info("Incorrect login or password.");
        }
        return result;
    }

    public static String getUserNameByLogin(String login) throws ServiceException {
        logger.info("Start getClientNameByLogin(String login). Login = " + login);
        String name;
        UserDao userDao = new UserDaoImpl();
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

    public static Long getIdUserByLogin(String login) throws ServiceException {
        logger.info("Start getIdClientByLogin(String login). Login = " + login);
        Long idClient;
        try {
            LoginDao loginDao = new LoginDaoImpl();
            idClient = loginDao.findIdByLogin(login);
        } catch (DaoException e) {
            logger.error("Client id with login = " + login + " was not found.");
            throw new ServiceException(e);
        }
        logger.info("Client id with login = " + login + " was found. Id = " + idClient);
        return idClient;
    }

    public static User getUserById(Long idUser) throws ServiceException {
        logger.info("Start User getIdUserById(Long idUser). idUser = " + idUser);
        User user  = new User();
        try {
            UserDao userDao = new UserDaoImpl();
            user = userDao.getUserById(idUser);
        } catch (DaoException e) {
            logger.error("Client with idUser = " + idUser + " was not found.");
            throw new ServiceException(e);
        }
        logger.info("Client with idUser = " + idUser + " was found. Id = " + idUser);
        return user;
    }

    public static UserRole getRoleByID(Long idUser) throws ServiceException {
        logger.info("Start UserRole getRoleByID(Long idUser). idUser = " + idUser);
        UserRole userRole;
        try {
            UserDao userDao = new UserDaoImpl();
            userRole = userDao.findRoleByID(idUser);
        } catch (DaoException e) {
            logger.error("User role with idUser = " + idUser + " was not found.");
            throw new ServiceException(e);
        }
        logger.info("User role with idUser = " + idUser + " was found. Role = " + userRole.getName());
        return userRole;
    }
}
