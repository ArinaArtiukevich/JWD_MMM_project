package com.jwd.dao.repository.impl;

import com.jwd.dao.connection.ConnectionPool;
import com.jwd.dao.connection.impl.ConnectionPoolImpl;
import com.jwd.dao.entity.Registration;
import com.jwd.dao.entity.User;
import com.jwd.dao.entity.enums.Gender;
import com.jwd.dao.entity.enums.UserRole;
import com.jwd.dao.repository.AbstractDao;
import com.jwd.dao.repository.UserDao;
import com.jwd.dao.entity.UserDTO;
import com.jwd.dao.exception.DaoException;
import com.jwd.dao.repository.LoginDao;
import com.jwd.dao.config.DataBaseConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserDaoImpl extends AbstractDao implements UserDao {
    private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);

    public UserDaoImpl(ConnectionPool connectionPool) {
        super(connectionPool);
    }

    @Override
    public boolean addUser(Registration registration) throws DaoException {
        logger.info("Start addUser(Registration registration). Login = " + registration.getLogin());
        boolean isAdded = false;
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = getConnection(false);
            statement = connection.prepareStatement(DataBaseConfig.getQuery("registration.insert.user"));
            statement.setString(1, registration.getFirstName());
            statement.setString(2, registration.getLastName());
            statement.setString(3, registration.getEmail());
            statement.setString(4, registration.getCity());
            statement.setString(5, registration.getLogin());
            statement.setString(6, registration.getGender().toString());
            statement.setString(7, registration.getUserRole().toString());
            int affectedRows = statement.executeUpdate();
            connection.commit();
            if (affectedRows > 0) {
                logger.info("A client was added.");
                isAdded = true;
                Long idClient = findIdByLogin(registration.getLogin());
                UserDTO userLogin = new UserDTO(idClient, registration.getLogin(), registration.getPassword());
                LoginDao loginDao = new LoginDaoImpl(new ConnectionPoolImpl(new DataBaseConfig()));
                loginDao.add(userLogin);
            } else {
                logger.error("User was not added.");
                throw new DaoException("User was not added.");
            }
        }
        catch(SQLException e) {
            throw new DaoException("Registration failed.", e);
        }
        finally {
            close(statement);
            retrieve(connection);
        }
        return isAdded;
    }

    @Override
    public boolean updateUserWithoutPassword(Long idUser, Registration userInfo) throws DaoException {
        logger.info("Start updateUserWithoutPassword(Registration userInfo). Login = " + userInfo.getLogin());
        boolean isUpdated = false;
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = getConnection(false);
            statement = connection.prepareStatement(DataBaseConfig.getQuery("users.update.without.password.by.id"));
            statement.setString(1, userInfo.getFirstName());
            statement.setString(2, userInfo.getLastName());
            statement.setString(3, userInfo.getEmail());
            statement.setString(4, userInfo.getCity());
            statement.setLong(5, idUser);
            int affectedRows = statement.executeUpdate();
            connection.commit();
            if (affectedRows > 0) {
                logger.info("User was updated.");
                isUpdated = true;
            } else {
                logger.error("User was not updated.");
                throw new DaoException("User was not updated.");
            }
        }
        catch(SQLException e) {
            throw new DaoException("User was not updated.", e);
        }
        finally {
            close(statement);
            retrieve(connection);
        }
        return isUpdated;
    }

    @Override
    public Long findIdByLogin(String login) throws DaoException {
        PreparedStatement statement = null;
        Connection connection = null;
        ResultSet resultSet = null;
        Long idClient = 0L;
        try {
            connection = getConnection(true);
            statement = connection.prepareStatement(DataBaseConfig.getQuery("registration.select.idUser"));
            statement.setString(1, login);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                idClient = resultSet.getLong(1);
            }
        }
        catch(SQLException e) {
            logger.error(e);
        }
        finally {
            close(resultSet);
            close(statement);
            retrieve(connection);
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
            connection = getConnection(false);
            statement = connection.prepareStatement(DataBaseConfig.getQuery("registration.delete.client.by.id"));
            statement.setInt(1, id);
            int affectedRows = statement.executeUpdate();
            connection.commit();
            if (affectedRows > 0) {
                logger.info("A client was deleted.");
                isDeleted = true;
                LoginDao loginDao = new LoginDaoImpl(new ConnectionPoolImpl(new DataBaseConfig()));
                loginDao.deleteLoginById(id);
            } else {
                logger.error("User was not deleted.");
                throw new DaoException("User was not deleted.");
            }
        }
        catch(SQLException e) {
            throw new DaoException("Deleting a client failed.", e);
        }
        finally {
            close(statement);
            retrieve(connection);
        }
        return isDeleted;
    }

    @Override
    public boolean isLoginExist(String login) {
        logger.info("Start hasLogin(String login). Login = " + login);
        boolean isExist = false;
        PreparedStatement statement = null;
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection(true);
            statement = connection.prepareStatement(DataBaseConfig.getQuery("users.find.by.login"));
            statement.setString(1, login);
            resultSet = statement.executeQuery();
            while(resultSet.next()) {
                isExist = true;
            }
        }
        catch(SQLException | DaoException e) {
            logger.error(e);
        }
        finally {
            close(resultSet);
            close(statement);
            retrieve(connection);
        }
        return isExist;
    }

    @Override
    public User getUserByLogin(String login) throws DaoException {
        logger.info("Start getClientByLogin(String login). Login = " + login);
        User user = new User();
        PreparedStatement statement = null;
        Connection connection = null;
        ResultSet resultSet = null;
        Boolean isExist = false;
        try {
            connection = getConnection(true);
            statement = connection.prepareStatement(DataBaseConfig.getQuery("users.find.by.login"));
            statement.setString(1, login);
            resultSet = statement.executeQuery();
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
            close(resultSet);
            close(statement);
            retrieve(connection);
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
        ResultSet resultSet = null;
        try {
            connection = getConnection(true);
            statement = connection.prepareStatement(DataBaseConfig.getQuery("users.find.first_name.by.login"));
            statement.setString(1, login);
            resultSet = statement.executeQuery();
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
            close(resultSet);
            close(statement);
            retrieve(connection);
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
        ResultSet resultSet = null;
        try {
            connection = getConnection(true);
            statement = connection.prepareStatement(DataBaseConfig.getQuery("users.find.by.idUser"));
            statement.setLong(1, idUser);
            resultSet = statement.executeQuery();
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
            close(resultSet);
            close(statement);
            retrieve(connection);
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
        ResultSet resultSet = null;
        try {
            connection = getConnection(true);
            statement = connection.prepareStatement(DataBaseConfig.getQuery("users.find.role.by.id"));
            statement.setLong(1, idUser);
            resultSet = statement.executeQuery();
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
            close(resultSet);
            close(statement);
            retrieve(connection);
        }
        return userRole;
    }
}