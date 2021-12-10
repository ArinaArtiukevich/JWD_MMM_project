package com.jwd.dao.repository.impl;

import com.jwd.dao.connection.ConnectionPool;
import com.jwd.dao.connection.impl.ConnectionPoolImpl;
import com.jwd.dao.entity.Registration;
import com.jwd.dao.entity.User;
import com.jwd.dao.entity.enumType.Gender;
import com.jwd.dao.entity.enumType.UserRole;
import com.jwd.dao.repository.AbstractDao;
import com.jwd.dao.repository.UserDao;
import com.jwd.dao.exception.DaoException;
import com.jwd.dao.config.DataBaseConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserDaoImpl extends AbstractDao implements UserDao {
    private static final Logger LOGGER = LogManager.getLogger(UserDaoImpl.class);

    public UserDaoImpl(ConnectionPool connectionPool) {
        super(connectionPool);
    }

    @Override
    public boolean addUser(Registration registration) throws DaoException {
        LOGGER.info("Start addUser(Registration registration). Login = " + registration.getLogin());
        boolean isAdded = false;
        PreparedStatement statement = null;
        Connection connection = null;
        ResultSet resultSet = null;
        boolean isExist = false;
        try {
            connection = getConnection(false);
            statement = connection.prepareStatement(DataBaseConfig.getQuery("users.find.by.login"));
            statement.setString(1, registration.getLogin());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                isExist = true;
            }
            if (!isExist) {
                statement = connection.prepareStatement(DataBaseConfig.getQuery("registration.insert.user"));
                statement.setString(1, registration.getFirstName());
                statement.setString(2, registration.getLastName());
                statement.setString(3, registration.getEmail());
                statement.setString(4, registration.getCity());
                statement.setString(5, registration.getLogin());
                statement.setString(6, registration.getGender().toString());
                statement.setString(7, registration.getUserRole().toString());
                int affectedRowsUser = statement.executeUpdate();
                statement = connection.prepareStatement(DataBaseConfig.getQuery("user_logins.insert.login"));
                statement.setString(1, registration.getLogin());
                statement.setString(2, registration.getPassword());
                int affectedRowsUserLogin = statement.executeUpdate();
                if (affectedRowsUser == 1 && affectedRowsUserLogin == 1) {
                    LOGGER.info("A login was added into user_logins.");
                    isAdded = true;
                } else {
                    throw new DaoException("User registration failed.");
                }
                connection.commit();
            } else {
                throw new DaoException("User with such login exists.");
            }
        } catch (SQLException e) {
            throw new DaoException("Registration failed.", e);
        } finally {
            close(resultSet);
            close(statement);
            retrieve(connection);
        }
        return isAdded;
    }

    @Override
    public boolean updateUserWithoutPassword(Long idUser, Registration userInfo) throws DaoException {
        LOGGER.info("Start updateUserWithoutPassword(Long idUser, Registration userInfo). Login = " + userInfo.getLogin());
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
            if (affectedRows > 0) {
                LOGGER.info("User was updated.");
                isUpdated = true;
            } else {
                LOGGER.error("User was not updated.");
                throw new DaoException("User was not updated.");
            }
            connection.commit();
        } catch (SQLException e) {
            throw new DaoException("User was not updated.", e);
        } finally {
            close(statement);
            retrieve(connection);
        }
        return isUpdated;
    }

    @Override
    public boolean updateUserWithPassword(Long idUser, Registration userInfo) throws DaoException {
        LOGGER.info("Start updateUserWithPassword(Long idUser, Registration userInfo). Login = " + userInfo.getLogin());
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
            int affectedRowsUsers = statement.executeUpdate();

            statement = connection.prepareStatement(DataBaseConfig.getQuery("user_logins.update.with.password.by.id"));
            statement.setString(1, userInfo.getPassword());
            statement.setLong(2, idUser);
            int affectedRowsUserLogin = statement.executeUpdate();
            if (affectedRowsUsers == 1 && affectedRowsUserLogin == 1) {
                LOGGER.info("User was updated.");
                isUpdated = true;
            } else {
                LOGGER.error("User was not updated.");
                throw new DaoException("User was not updated.");
            }
            connection.commit();
        } catch (SQLException e) {
            throw new DaoException("User was not updated.", e);
        } finally {
            close(statement);
            retrieve(connection);
        }
        return isUpdated;
    }

    @Override
    public User getUserByLogin(String login) throws DaoException {
        LOGGER.info("Start getClientByLogin(String login). Login = " + login);
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
            while (resultSet.next()) {
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
                throw new DaoException("Could not find user by login = " + login);
            }
        } catch (SQLException e) {
            LOGGER.error("Could not find user by login." + login);
            throw new DaoException("Could not find client with login.");
        } finally {
            close(resultSet);
            close(statement);
            retrieve(connection);
        }
        return user;
    }

    @Override
    public String findNameByLogin(String login) throws DaoException {
        LOGGER.info("Start getClientByLogin(String login). Login = " + login);
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
            while (resultSet.next()) {
                firstName = resultSet.getString(2);
                isExist = true;
            }
            if (!isExist) {
                throw new DaoException("Could not find client with login = " + login);
            }
        } catch (SQLException e) {
            LOGGER.error("Could not find name by login.");
            throw new DaoException("Could not find name by login.");
        } finally {
            close(resultSet);
            close(statement);
            retrieve(connection);
        }
        return firstName;
    }

    @Override
    public User getUserById(Long idUser) throws DaoException {
        LOGGER.info("Start getUserById(Long idUser). idUser = " + idUser);
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
            while (resultSet.next()) {
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
        } catch (SQLException e) {
            LOGGER.error("Could not find user by login.");
            throw new DaoException("Could not find user by login.");
        } finally {
            close(resultSet);
            close(statement);
            retrieve(connection);
        }
        return user;
    }

    @Override
    public UserRole findRoleByID(Long idUser) throws DaoException {
        LOGGER.info("Start UserRole findRoleByID(Long idUser). idUser = " + idUser);
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
            while (resultSet.next()) {
                userRole = UserRole.valueOf(resultSet.getString(1));
                isExist = true;
            }
            if (!isExist) {
                throw new DaoException("Could not find user with id = " + idUser);
            }
        } catch (SQLException e) {
            LOGGER.error("Could not find user role by login.");
            throw new DaoException("Could not find user role by login.");
        } finally {
            close(resultSet);
            close(statement);
            retrieve(connection);
        }
        return userRole;
    }

    @Override
    public boolean deleteUserByLogin(String login) throws DaoException {
        LOGGER.info("Start deleteUserByLogin(String login).");
        boolean isDeleted = false;
        PreparedStatement statement = null;
        Connection connection = null;
        ResultSet resultSet = null;
        boolean isExist = false;
        try {
            connection = getConnection(false);
            statement = connection.prepareStatement(DataBaseConfig.getQuery("users.find.by.login"));
            statement.setString(1, login);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                isExist = true;
            }
            if (isExist) {
                statement = connection.prepareStatement(DataBaseConfig.getQuery("registration.delete.user.by.login"));
                statement.setString(1, login);
                int affectedRowsUser = statement.executeUpdate();
                statement = connection.prepareStatement(DataBaseConfig.getQuery("user_logins.delete.by.login"));
                statement.setString(1, login);
                int affectedRowsUserLogin = statement.executeUpdate();
                if (affectedRowsUser == 1 && affectedRowsUserLogin == 1) {
                    LOGGER.info("User was deleted.");
                    isDeleted = true;
                } else {
                    throw new DaoException("User was not deleted.");
                }
                connection.commit();
            } else {
                throw new DaoException("User with such login does not exist.");
            }
        } catch (SQLException e) {
            throw new DaoException("User was deleted.");
        } finally {
            close(resultSet);
            close(statement);
            retrieve(connection);
        }
        return isDeleted;
    }

    @Override
    public String findPasswordByLogin(String login) throws DaoException {
        LOGGER.info("Start isExistInList(String login, String password). Login = " + login);
        String password = null;
        boolean isLoginExist = false;
        PreparedStatement statement = null;
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection(true);
            statement = connection.prepareStatement(DataBaseConfig.getQuery("user_logins.find.password.by.login"));
            statement.setString(1, login);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                password = resultSet.getString(1);
                isLoginExist = true;
                LOGGER.info("User with login:" + login + " was found.");
            }
            if (!isLoginExist) {
                throw new DaoException("Could not find user with login = " + login);
            }
        } catch (SQLException e) {
            LOGGER.error("User was not found.");
            throw new DaoException("User was not found.");
        } finally {
            close(resultSet);
            close(statement);
            retrieve(connection);
        }
        return password;
    }
}