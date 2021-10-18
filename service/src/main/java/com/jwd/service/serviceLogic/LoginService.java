package com.jwd.service.serviceLogic;

import com.jwd.dao.repository.impl.LoginDaoImpl;
import com.jwd.dao.repository.impl.UserDaoImpl;
import com.jwd.dao.exception.DaoException;
import com.jwd.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoginService {

    private static final Logger logger = LogManager.getLogger(LoginService.class);

    public LoginService() {
    }

    public static boolean checkLoginAndPassword(String login, String password) {
        logger.info("Start checkLoginAndPassword(String login, String password).");
        boolean result;
        LoginDaoImpl loginDaoImpl = new LoginDaoImpl();
        result = loginDaoImpl.isLoginAndPasswordExist(login, password);
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
        UserDaoImpl clientDao = new UserDaoImpl();
        try {
            name = clientDao.findNameByLogin(login);
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
            LoginDaoImpl loginDaoImpl = new LoginDaoImpl();
            idClient = loginDaoImpl.findIdByLogin(login);
        } catch (DaoException e) {
            logger.error("Client id with login = " + login + " was not found.");
            throw new ServiceException(e);
        }
        logger.info("Client id with login = " + login + " was found. Id = " + idClient);
        return idClient;
    }

}
