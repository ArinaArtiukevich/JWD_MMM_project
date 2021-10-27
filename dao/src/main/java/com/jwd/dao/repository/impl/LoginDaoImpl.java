package com.jwd.dao.repository.impl;

import com.jwd.dao.connection.DataBaseConfig;
import com.jwd.dao.repository.LoginDao;
import com.jwd.dao.entity.Login;
import com.jwd.dao.exception.DaoException;
import com.jwd.dao.resources.DataBaseBundle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LoginDaoImpl implements LoginDao {
    private static final Logger logger = LogManager.getLogger(LoginDaoImpl.class);
    private final DataBaseConfig dataBaseConfig;

    public LoginDaoImpl() {
        dataBaseConfig = new DataBaseConfig();
    }

    public boolean add(Login login) throws DaoException {
        logger.info("Start add(Login login). Id = " + login.getIdClient());
        PreparedStatement statement = null;
        Connection connection = null;
        boolean isAdded = false;
        try {
            connection = dataBaseConfig.getConnection();
            statement = connection.prepareStatement(DataBaseBundle.getProperty("user_dtos.insert.login"));
            statement.setLong(1, login.getIdClient());
            statement.setString(2, login.getLogin());
            statement.setString(3, login.getPassword());
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                logger.info("A login was added into user_dtos.");
                isAdded = true;
            }
        }
        catch(SQLException e) {
            throw new DaoException("A login was not added into user_dtos.");
        }
        finally {
            dataBaseConfig.close(connection, statement);
        }
        return isAdded;
    }
    public boolean deleteLoginById(Integer id) throws DaoException {
        logger.info("Start deleteLoginById(Integer id). Id = " + id);
        boolean isDeleted = false;
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = dataBaseConfig.getConnection();
            statement = connection.prepareStatement(DataBaseBundle.getProperty("user_dtos.delete.login.by.id"));
            statement.setInt(1, id);

            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                logger.info("Login was deleted.");
                isDeleted = true;
            }
        }
        catch(SQLException e) {
            throw new DaoException("Deleting login failed.", e);
        }
        finally {
            dataBaseConfig.close(connection, statement);
        }
        return isDeleted;

    }

    public Long findIdByLogin(String login) throws DaoException {
        logger.info("Start findIdByLogin(String login). Login = " + login);
        Long id = 0L;
        boolean isExist = false;
        PreparedStatement statement = null;
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            connection = dataBaseConfig.getConnection();
            statement = connection.prepareStatement(DataBaseBundle.getProperty("user_dtos.find.id.by.login"));
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
            logger.error(e);
        }
        finally {
            dataBaseConfig.close(connection, statement, resultSet);
        }
        return id;
    }

    public Boolean isLoginAndPasswordExist(String login, String password) {
        logger.info("Start isExistInList(String login, String password). Login = " + login + ", password = " + password);
        boolean isExist = false;
        PreparedStatement statement = null;
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            connection = dataBaseConfig.getConnection();
            statement = connection.prepareStatement(DataBaseBundle.getProperty("user_dtos.find.by.login.and.password"));
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
        }
        catch(SQLException | DaoException e) {
            logger.error(e);
        }
        finally {
            dataBaseConfig.close(connection, statement, resultSet);
        }
        return isExist;
    }


}