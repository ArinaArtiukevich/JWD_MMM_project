package com.jwd.service.serviceLogic;

import com.jwd.dao.entity.Registration;
import com.jwd.dao.entity.User;
import com.jwd.dao.entity.enums.UserRole;
import com.jwd.service.exception.ServiceException;

public interface UserService {
    boolean register(Registration registration) throws ServiceException;

    boolean updateUserWithoutPassword(Long idUser, Registration userInfo) throws ServiceException;

    boolean updateUserWithPassword(Long idUser, Registration userInfo) throws ServiceException;

    boolean isLoginAndPasswordExist(String login, String password) throws ServiceException;

    String getUserNameByLogin(String login) throws ServiceException;

    Long getIdUserByLogin(String login) throws ServiceException;

    User getUserById(Long idUser) throws ServiceException;

    UserRole getRoleByID(Long idUser) throws ServiceException;

    String getPassword(Long idUser) throws ServiceException;
}
