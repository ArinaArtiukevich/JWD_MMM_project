package com.jwd.dao.repository.impl;

import com.jwd.dao.connection.ConnectionPool;
import com.jwd.dao.entity.Registration;
import com.jwd.dao.entity.User;
import com.jwd.dao.entity.enums.Gender;
import com.jwd.dao.entity.enums.ServiceStatus;
import com.jwd.dao.entity.enums.UserRole;
import com.jwd.dao.repository.UserDao;
import com.jwd.dao.entity.Login;
import com.jwd.dao.exception.DaoException;
import com.jwd.dao.repository.LoginDao;
import com.jwd.dao.resources.DataBaseBundle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserDaoImpl implements UserDao {
    private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);

    @Override
    public boolean addUser(Registration registration) throws DaoException {
        logger.info("Start addUser(Registration registration). Login = " + registration.getLogin());
        boolean isAdded = false;
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(DataBaseBundle.getProperty("registration.insert.user"));
            statement.setString(1, registration.getFirstName());
            statement.setString(2, registration.getLastName());
            statement.setString(3, registration.getEmail());
            statement.setString(4, registration.getCity());
            statement.setString(5, registration.getLogin());
            statement.setString(6, registration.getGender().toString());
            statement.setString(7, registration.getUserRole().toString());
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                logger.info("A client was added.");
                isAdded = true;
                Long idClient = findIdByLogin(registration.getLogin());
                Login userLogin = new Login(idClient, registration.getLogin(), registration.getPassword());
                LoginDao loginDao = new LoginDaoImpl();
                loginDao.add(userLogin);
            }
        }
        catch(SQLException e) {
            throw new DaoException("Registration failed.", e);
        }
        finally {
            ConnectionPool.getInstance().releaseConnection(connection);
            try {
                statement.close();
            }
            catch(SQLException e) {
                logger.error(e);
            }
        }
        return isAdded;
    }

    @Override
    public Long findIdByLogin(String login) throws DaoException {
        PreparedStatement statement = null;
        Connection connection = null;
        ResultSet resultSet;
        Long idClient = 0L;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(DataBaseBundle.getProperty("registration.select.idUser"));
            statement.setString(1, login);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                idClient = resultSet.getLong(1);
                if (idClient <= 0) {
                    throw new DaoException("There is no such client in DB.");
                }
            }
        }
        catch(SQLException e) {
            logger.error(e);
        }
        finally {
            ConnectionPool.getInstance().releaseConnection(connection);;
            try {
                statement.close();
            }
            catch(SQLException e) {
                logger.error(e);
            }
        }
        return idClient;
    }

    @Override
    public boolean deleteUserById(Integer id) throws DaoException {
        logger.info("Start deleteUserById(Integer id). Id = " + id);
        boolean isDeleted = false;
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(DataBaseBundle.getProperty("registration.delete.client.by.id"));
            statement.setInt(1, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                logger.info("A client was deleted.");
                isDeleted = true;
                LoginDao loginDao = new LoginDaoImpl();
                loginDao.deleteLoginById(id);
            }
        }
        catch(SQLException e) {
            throw new DaoException("Deleting a client failed.", e);
        }
        finally {
            ConnectionPool.getInstance().releaseConnection(connection);
            try {
                statement.close();
            }
            catch(SQLException e) {
                logger.error(e);
            }
        }
        return isDeleted;
    }

    @Override
    public boolean isLoginExist(String login) {
        logger.info("Start hasLogin(String login). Login = " + login);
        boolean isExist = false;
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(DataBaseBundle.getProperty("users.find.by.login"));
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                isExist = true;
            }
        }
        catch(SQLException e) {
            logger.error(e);
        }
        finally {
            ConnectionPool.getInstance().releaseConnection(connection);
            try {
                statement.close();
            }
            catch(SQLException e) {
                logger.error(e);
            }
        }
        return isExist;
    }

    @Override
    public User getUserByLogin(String login) throws DaoException {
        logger.info("Start getClientByLogin(String login). Login = " + login);
        User user = new User();
        PreparedStatement statement = null;
        Connection connection = null;
        Boolean isExist = false;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(DataBaseBundle.getProperty("users.find.by.login"));
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                Long foundId = resultSet.getLong(1);
                String firstName = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                String email = resultSet.getString(4);
                String city = resultSet.getString(5);
                Gender gender = Gender.valueOf(resultSet.getString(7).toUpperCase());
                UserRole userRole = UserRole.valueOf(resultSet.getString(8).toUpperCase());
                user = new User(foundId, firstName, lastName, email, city, login, gender, userRole);
                isExist = true;
            }
            if (!isExist) {
                throw new DaoException("Could not find client with login = " + login);
            }
        }
        catch(SQLException e) {
            logger.error(e);
        }
        finally {
            ConnectionPool.getInstance().releaseConnection(connection);
            try {
                statement.close();
            }
            catch(SQLException e) {
                logger.error(e);
            }
        }
        return user;
    }

    @Override
    public String findNameByLogin(String login) throws DaoException {
        logger.info("Start getClientByLogin(String login). Login = " + login);
        String firstName = null;
        PreparedStatement statement = null;
        Connection connection = null;
        Boolean isExist = false;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(DataBaseBundle.getProperty("users.find.first_name.by.login"));
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                firstName = resultSet.getString(2);
                isExist = true;
            }
            if (!isExist) {
                throw new DaoException("Could not find client with login = " + login);
            }
        }
        catch(SQLException e) {
            logger.error(e);
        }
        finally {
            ConnectionPool.getInstance().releaseConnection(connection);
            try {
                statement.close();
            }
            catch(SQLException e) {
                logger.error(e);
            }
        }
        return firstName;
    }

    @Override
    public User getUserById(Long idUser) throws DaoException {
        logger.info("Start getUserById(Long idUser). idUser = " + idUser);
        User user = new User();
        PreparedStatement statement = null;
        Connection connection = null;
        Boolean isExist = false;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(DataBaseBundle.getProperty("users.find.by.idUser"));
            statement.setLong(1, idUser);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                String firstName = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                String email = resultSet.getString(4);
                String city = resultSet.getString(5);
                String login = resultSet.getString(6);
                Gender gender = Gender.valueOf(resultSet.getString(7).toUpperCase());
                UserRole userRole = UserRole.valueOf(resultSet.getString(8).toUpperCase());
                user = new User(idUser, firstName, lastName, email, city, login, gender, userRole);
                isExist = true;
            }
            if (!isExist) {
                throw new DaoException("Could not find client with idUser = " + idUser);
            }
        }
        catch(SQLException e) {
            logger.error(e);
        }
        finally {
            ConnectionPool.getInstance().releaseConnection(connection);
            try {
                statement.close();
            }
            catch(SQLException e) {
                logger.error(e);
            }
        }
        return user;
    }
    @Override
    public UserRole findRoleByID(Long idUser) throws DaoException {
        logger.info("Start UserRole findRoleByID(Long idUser). idUser = " + idUser);
        UserRole userRole = null;
        PreparedStatement statement = null;
        Connection connection = null;
        Boolean isExist = false;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(DataBaseBundle.getProperty("users.find.role.by.id"));
            statement.setLong(1, idUser);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                userRole = UserRole.valueOf(resultSet.getString(1));
                isExist = true;
            }
            if (!isExist) {
                throw new DaoException("Could not find user with id = " + idUser);
            }
        }
        catch(SQLException e) {
            logger.error(e);
        }
        finally {
            ConnectionPool.getInstance().releaseConnection(connection);
            try {
                statement.close();
            }
            catch(SQLException e) {
                logger.error(e);
            }
        }
        return userRole;
    }
}