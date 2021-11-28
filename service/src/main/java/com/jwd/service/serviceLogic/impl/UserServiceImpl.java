package com.jwd.service.serviceLogic.impl;

import com.jwd.dao.entity.Registration;
import com.jwd.dao.entity.User;
import com.jwd.dao.entity.UserDTO;
import com.jwd.dao.entity.enums.UserRole;
import com.jwd.dao.exception.DaoException;
import com.jwd.dao.factory.DaoFactory;
import com.jwd.dao.repository.LoginDao;
import com.jwd.dao.repository.OrderDao;
import com.jwd.dao.repository.UserDao;
import com.jwd.service.exception.ServiceException;
import com.jwd.service.serviceLogic.UserService;
import com.jwd.service.validator.ServiceValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import static com.jwd.service.util.ParameterAttribute.PATTERN_LOGIN;

public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);
    private final UserDao userDao;
    private final LoginDao loginDao;
    private final ServiceValidator validator;

    public UserServiceImpl(UserDao userDao, LoginDao loginDao, ServiceValidator validator) {
        this.userDao = userDao;
        this.loginDao = loginDao;
        this.validator = validator;
    }

    @Override
    public boolean register(Registration registration) throws ServiceException {
        LOGGER.info("Start register(Registration registration).");
        boolean isRegistered = false;
        try {
            if (validator.validateRegistrationData(registration)) {
                if (!userDao.isLoginExist(registration.getLogin())) {
                    isRegistered = userDao.addUser(registration);
                    Long idClient = userDao.findIdByLogin(registration.getLogin());
                    UserDTO userLogin = new UserDTO(idClient, registration.getLogin(), registration.getPassword());
                    loginDao.add(userLogin);
                } else {
                    LOGGER.error("Login = " + registration.getLogin() + " has already been taken");
                    throw new ServiceException("Login has already been taken");
                }
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
                // isUpdated = userDao.updateUserWithPassword(idUser, userInfo);
                isUpdated = userDao.updateUserWithoutPassword(idUser, userInfo);
                if (isUpdated) {
                    UserDTO userDto = new UserDTO(idUser, userInfo.getLogin(), userInfo.getPassword());
                    isUpdated = loginDao.updateUserDTO(userDto); // todo in one commit
                }
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
            String userPassword = loginDao.findPasswordByLogin(login);
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
    public String getUserNameByLogin(String login) throws ServiceException {
        LOGGER.info("Start getClientNameByLogin(String login). Login = " + login);
        String name;
        try {
            validator.validate(login);
            name = userDao.findNameByLogin(login);
        } catch (DaoException e) {
            LOGGER.error("Client name with login = " + login + " was not found.");
            throw new ServiceException(e);
        }
        LOGGER.info("Client name with login = " + login + " was found. Name = " + name);
        return name;
    }

    @Override
    public Long getIdUserByLogin(String login) throws ServiceException {
        LOGGER.info("Start getIdClientByLogin(String login). Login = " + login);
        Long idClient;
        try {
            validator.validate(login);
            idClient = loginDao.findIdByLogin(login);
        } catch (DaoException e) {
            LOGGER.error("Client id with login = " + login + " was not found.");
            throw new ServiceException(e);
        }
        LOGGER.info("Client id with login = " + login + " was found. Id = " + idClient);
        return idClient;
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

    @Override
    public UserRole getRoleByID(Long idUser) throws ServiceException {
        LOGGER.info("Start UserRole getRoleByID(Long idUser). idUser = " + idUser);
        UserRole userRole;
        try {
            validator.validate(idUser);
            userRole = userDao.findRoleByID(idUser);
        } catch (DaoException e) {
            LOGGER.error("User role with idUser = " + idUser + " was not found.");
            throw new ServiceException(e);
        }
        LOGGER.info("User role with idUser = " + idUser + " was found. Role = " + userRole.getName());
        return userRole;
    }

    public String getPassword(Long idUser) throws ServiceException {
        LOGGER.info("Start String getPassword(String login) throws ServiceException. idUser = " + idUser);
        String password;
        try {
            validator.validate(idUser);
            password = loginDao.findPasswordById(idUser);
        } catch (DaoException e) {
            LOGGER.error("Password was not found.");
            throw new ServiceException(e);
        }

        return password;
    }
}
