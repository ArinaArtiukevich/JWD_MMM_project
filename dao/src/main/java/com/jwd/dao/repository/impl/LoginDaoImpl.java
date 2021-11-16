package com.jwd.dao.repository.impl;

import com.jwd.dao.connection.ConnectionPool;
import com.jwd.dao.connection.impl.ConnectionPoolImpl;
import com.jwd.dao.repository.AbstractDao;
import com.jwd.dao.repository.LoginDao;
import com.jwd.dao.entity.UserDTO;
import com.jwd.dao.exception.DaoException;
import com.jwd.dao.config.DataBaseConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class LoginDaoImpl extends AbstractDao implements LoginDao {

    private static final Logger logger = LogManager.getLogger(LoginDaoImpl.class);

    public LoginDaoImpl(final ConnectionPool connectionPool) {
        super(connectionPool);
    }

    @Override
    public boolean add(UserDTO login) throws DaoException {
        logger.info("Start add(Login login). Id = " + login.getIdUser());
        PreparedStatement statement = null;
        Connection connection = null;
        boolean isAdded = false;
        try {
            connection = getConnection(false);
            statement = connection.prepareStatement(DataBaseConfig.getQuery("user_dtos.insert.login"));
            statement.setLong(1, login.getIdUser());
            statement.setString(2, login.getLogin());
            statement.setString(3, login.getPassword());
            int affectedRows = statement.executeUpdate();
            connection.commit();
            if (affectedRows > 0) {
                logger.info("A login was added into user_dtos.");
                isAdded = true;
            } else {
                throw new DaoException("A login was not added into user_dtos.");
            }
        }
        catch(SQLException e) {
            throw new DaoException("A login was not added into user_dtos.");
        }
        finally {
            close(statement);
            retrieve(connection);
        }
        return isAdded;
    }

    @Override
    public boolean updateUserDTO(UserDTO userDTO) throws DaoException {
        logger.info("Start add(Login login). Id = " + userDTO.getIdUser());
        PreparedStatement statement = null;
        Connection connection = null;
        boolean isAdded = false;
        try {
            connection = getConnection(false);
            statement = connection.prepareStatement(DataBaseConfig.getQuery("user_dtos.update.with.password.by.id"));
            statement.setString(1, userDTO.getPassword());
            statement.setLong(2, userDTO.getIdUser());
            int affectedRows = statement.executeUpdate();
            connection.commit();
            if (affectedRows > 0) {
                logger.info("A login was added into user_dtos.");
                isAdded = true;
            } else {
                throw new DaoException("A login was not added into user_dtos.");
            }
        }
        catch(SQLException e) {
            throw new DaoException("A login was not added into user_dtos.");
        }
        finally {
            close(statement);
            retrieve(connection);
        }
        return isAdded;
    }

    @Override
    public boolean deleteLoginById(Integer id) throws DaoException {
        logger.info("Start deleteLoginById(Integer id). Id = " + id);
        boolean isDeleted = false;
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = getConnection(false);
            statement = connection.prepareStatement(DataBaseConfig.getQuery("user_dtos.delete.login.by.id"));
            statement.setInt(1, id);
            int affectedRows = statement.executeUpdate();
            connection.commit();
            if (affectedRows > 0) {
                logger.info("Login was deleted.");
                isDeleted = true;
            } else {
                throw new DaoException("A login was not deleted into user_dtos.");
            }

        }
        catch(SQLException e) {
            throw new DaoException("Deleting login failed.", e);
        }
        finally {
            close(statement);
            retrieve(connection);
        }
        return isDeleted;
    }

    @Override
    public Long findIdByLogin(String login) throws DaoException {
        logger.info("Start findIdByLogin(String login). Login = " + login);
        Long id = 0L;
        boolean isExist = false;
        PreparedStatement statement = null;
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection(true);
            statement = connection.prepareStatement(DataBaseConfig.getQuery("user_dtos.find.id.by.login"));
            statement.setString(1, login);
            resultSet = statement.executeQuery();
            while(resultSet.next()) {
                id = resultSet.getLong(1);
                isExist = true;
                logger.info("Login with login:" + login + " was found.");
            }
            if (!isExist) {
                throw new DaoException("Could not find user_dto with login = " + login);
            }
        }
        catch(SQLException e) {
            logger.error("User was not found.");
            throw new DaoException("User was not found.");
        }
        finally {
            close(resultSet);
            close(statement);
            retrieve(connection);
        }
        return id;
    }

    @Override
    public Boolean isLoginAndPasswordExist(String login, String password) throws DaoException {
        logger.info("Start isExistInList(String login, String password). Login = " + login + ", password = " + password);
        boolean isExist = false;
        PreparedStatement statement = null;
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection(true);
            statement = connection.prepareStatement(DataBaseConfig.getQuery("user_dtos.find.by.login.and.password"));
            statement.setString(1, login);
            statement.setString(2, password);
            resultSet = statement.executeQuery();
            while(resultSet.next()) {
                isExist = true;
                logger.info("User with login:" + login + " was found.");
            }
            if (!isExist) {
                throw new DaoException("Could not find user_dto with login = " + login);
            }
        } catch(SQLException e) {
            logger.error("User was not found.");
            throw new DaoException("User was not found.");
        }
        finally {
            close(resultSet);
            close(statement);
            retrieve(connection);
        }
        return isExist;
    }

    @Override
    public String findPasswordById(Long idUser) throws DaoException {
        logger.info("Start findPasswordByLogin(String login). idUser = " + idUser);
        String password = null;
        boolean isExist = false;
        PreparedStatement statement = null;
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection(true);
            statement = connection.prepareStatement(DataBaseConfig.getQuery("user_dtos.find.password.by.id"));
            statement.setLong(1, idUser);
            resultSet = statement.executeQuery();
            while(resultSet.next()) {
                password = resultSet.getString(1);
                isExist = true;
                logger.info("Login with idUser:" + idUser + " was found.");
            }
            if (!isExist) {
                throw new DaoException("Could not find user_dto with idUser = " + idUser);
            }
        }
        catch(SQLException e) {
            logger.error("Password was not found.");
            throw new DaoException("Password was not found.");
        }
        finally {
            close(resultSet);
            close(statement);
            retrieve(connection);
        }
        return password;
    }

}