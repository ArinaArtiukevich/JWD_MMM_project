package com.jwd.service.serviceLogic.impl;

import com.jwd.dao.entity.Registration;
import com.jwd.dao.entity.User;
import com.jwd.dao.entity.UserDTO;
import com.jwd.dao.entity.enums.UserRole;
import com.jwd.dao.exception.DaoException;
import com.jwd.dao.factory.DaoFactory;
import com.jwd.dao.repository.LoginDao;
import com.jwd.dao.repository.UserDao;
import com.jwd.service.exception.ServiceException;
import com.jwd.service.serviceLogic.UserService;
import com.jwd.service.validator.ServiceValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
    private final UserDao userDao = DaoFactory.getInstance().getUserDao();
    private final LoginDao loginDao = DaoFactory.getInstance().getLoginDao();
    private final ServiceValidator validator = new ServiceValidator();

    @Override
    public boolean register(Registration registration) throws ServiceException {
        logger.info("Start register(Registration registration).");
        boolean isRegistered = false;
        try {
            if (validator.validateData(registration)) {
                registration.setPassword(BCrypt.hashpw(registration.getPassword(), BCrypt.gensalt()));
                registration.setConfirmPassword(BCrypt.hashpw(registration.getConfirmPassword(), BCrypt.gensalt()));
                isRegistered = userDao.addUser(registration);
            }
        } catch (DaoException e) {
            logger.error("Invalid input parameters.");
            throw new ServiceException(e);
        }
        return isRegistered;
    }

    @Override
    public boolean updateUserWithoutPassword(Long idUser, Registration userInfo) throws ServiceException{
        logger.info("Start updateUserWithoutPassword(Long idUser, Registration userInfo).");
        boolean isUpdated = false;
        try {
            if (validator.validateUserWithoutPassword(userInfo)) {
                validator.validate(idUser);
                isUpdated = userDao.updateUserWithoutPassword(idUser, userInfo);
            }

        } catch (DaoException e) {
            logger.error("Invalid input parameters.");
            throw new ServiceException(e);
        }
        return isUpdated;
    }

    @Override
    public boolean updateUserWithPassword(Long idUser, Registration userInfo) throws ServiceException{
        logger.info("Start updateUserWithPassword(Long idUser, Registration userInfo).");
        boolean isUpdated = false;
        try {
            if (validator.validateUserWithPassword(userInfo)) {
                validator.validate(idUser);
                userInfo.setPassword(BCrypt.hashpw(userInfo.getPassword(), BCrypt.gensalt()));
                userInfo.setConfirmPassword(BCrypt.hashpw(userInfo.getConfirmPassword(), BCrypt.gensalt()));
                isUpdated = userDao.updateUserWithoutPassword(idUser, userInfo);
                if (isUpdated) {
                    UserDTO userDto = new UserDTO(idUser, userInfo.getLogin(), userInfo.getPassword());
                    isUpdated = loginDao.updateUserDTO(userDto);
                }
            }
        } catch (DaoException e) {
            logger.error("Invalid input parameters.");
            throw new ServiceException(e);
        }
        return isUpdated;
    }

    @Override
    public boolean isLoginAndPasswordExist(String login, String password) throws ServiceException {
        logger.info("Start checkLoginAndPassword(String login, String password).");
        boolean result = false;
        try {
            validator.validate(login);
            validator.validate(password);
            String userPassword = loginDao.findPasswordByLogin(login);
            validator.validate(userPassword);

            if (BCrypt.checkpw(password, userPassword)) {
                result = true;
                logger.info("User exists.");
            }
        } catch (DaoException | IllegalArgumentException e) {
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

    public String getPassword(Long idUser) throws ServiceException {
        logger.info("Start String getPassword(String login) throws ServiceException. idUser = " + idUser);
        String password;
        try {
            validator.validate(idUser);
            password = loginDao.findPasswordById(idUser);
            validator.validate(password);
        } catch (DaoException e) {
            logger.error("Password was not found.");
            throw new ServiceException(e);
        }

        return password;
    }
}
