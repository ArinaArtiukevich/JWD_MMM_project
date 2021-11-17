package com.jwd.service.validator;

import com.jwd.dao.config.DataBaseConfig;
import com.jwd.dao.connection.impl.ConnectionPoolImpl;
import com.jwd.dao.entity.Order;
import com.jwd.dao.entity.Page;
import com.jwd.dao.entity.Registration;
import com.jwd.dao.entity.enums.Gender;
import com.jwd.dao.entity.enums.ServiceStatus;
import com.jwd.dao.entity.enums.ServiceType;
import com.jwd.dao.entity.enums.UserRole;
import com.jwd.dao.repository.impl.UserDaoImpl;
import com.jwd.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

import static java.util.Objects.isNull;

public class ServiceValidator {
    private static final Logger logger = LogManager.getLogger(ServiceValidator.class);
    private final UserDaoImpl userDao = new UserDaoImpl(new ConnectionPoolImpl(new DataBaseConfig()));
    private final List<String> availableSortByParameters = Arrays.asList("order_creation_date", "address", "service_type",
            "service_status", "description");
    private final List<String> availableDirectionParameters = Arrays.asList("ASC", "DESC");

    public void validate(Page<Order> orderPageRequest) throws ServiceException {
        // TODO validate other parameters
        if ((isNull(orderPageRequest.getSortBy()) || orderPageRequest.getSortBy().isEmpty()) &&
                !availableSortByParameters.contains(orderPageRequest.getSortBy())) {
            throw new ServiceException("Sort by parameter is not available.");
        } if ((isNull(orderPageRequest.getDirection()) || orderPageRequest.getDirection().isEmpty()) &&
                !availableDirectionParameters.contains(orderPageRequest.getDirection())) {
            throw new ServiceException("Direction parameter is not available.");
        }
    }

    public void validate(ServiceType serviceType) throws ServiceException {
        if (serviceType == null) {
            throw new ServiceException("Service type is null.");
        }
    }

    public void validate(UserRole userRole) throws ServiceException {
        if(userRole == null) {
            throw new ServiceException("UserRole type is null.");
        }
    }

    public void validate(Gender gender) throws ServiceException {
        if (gender == null) {
            throw new ServiceException("Gender is null.");
        }
    }

    public void validate(ServiceStatus serviceStatus) throws ServiceException {
        if (serviceStatus == null) {
            throw new ServiceException("Service status is null.");
        }
    }

    public void validate(Long id) throws ServiceException {
        if (isNull(id) || id <= 0) {
            throw new ServiceException("Id was not found.");
        }
    }
    public void validate(Order orderToBeAdded) throws ServiceException {
        validate(orderToBeAdded.getDescription());
        validate(orderToBeAdded.getAddress());
        validate(orderToBeAdded.getServiceType());
        validate(orderToBeAdded.getStatus());
        validate(orderToBeAdded.getOrderCreationDate());
    }

    public void validate(Date date) throws ServiceException {
        if (isNull(date)) {
            throw new ServiceException("Date is null.");
        }
    }

    public void validate(String string) throws ServiceException {
        if (isNull(string) || string.isEmpty()) {
            throw new ServiceException("Invalid parameter.");
        }
    }

    public boolean validateUserWithPassword(Registration userInfo) throws ServiceException {
        logger.info("Start validateUserWithPassword(Registration registration)." );
        validateEmptyFieldsWithPassword(userInfo);
        boolean result = ( checkCity(userInfo.getCity()) &&
                checkEmail(userInfo.getEmail())) &&
                checkPassword(userInfo.getPassword(), userInfo.getConfirmPassword());
        if (result) {
            logger.info("User data is ready to be updated" );
        } else {
            logger.info("User data is not ready to be updated" );
        }
        return result;
    }

    public boolean validateUserWithoutPassword(Registration userInfo) throws ServiceException {
        logger.info("Start validateUserWithoutPassword(Registration registration)." );
        validateEmptyFieldsWithoutPassword(userInfo);
        boolean result = ( checkCity(userInfo.getCity()) &&
                checkEmail(userInfo.getEmail()));
        if (result) {
            logger.info("User data is ready to be updated" );
        } else {
            logger.info("User data is not ready to be updated" );
        }
        return result;
    }

    private void validateEmptyFieldsWithPassword(Registration userInfo) throws ServiceException {
        logger.info("Start validateEmptyFieldsWithoutPassword(Registration registration)." );
        validateEmptyFieldsWithoutPassword(userInfo);
        validate(userInfo.getPassword());
        validate(userInfo.getConfirmPassword());
    }

    private void validateEmptyFieldsWithoutPassword(Registration userInfo) throws ServiceException {
        logger.info("Start validateEmptyFieldsWithoutPassword(Registration registration)." );
        validate(userInfo.getFirstName());
        validate(userInfo.getLastName());
        validate(userInfo.getEmail());
        validate(userInfo.getCity());
    }

    public boolean validateData(Registration registration) throws ServiceException {
        logger.info("Start checkData(Registration registration)." );
        validateEmptyFields(registration);
        boolean result = (checkPassword(registration.getPassword(), registration.getConfirmPassword()) &&
                checkLogin(registration.getLogin()) &&
                checkCity(registration.getCity()) &&
                checkEmail(registration.getEmail()));
        if (result) {
            logger.info("User data is ready to be register" );
        } else {
            logger.info("User data is not ready to be register" );
        }
        return result;
    }


    private void validateEmptyFields(Registration registration) throws ServiceException {
        logger.info("Start validateEmptyFields(Registration registration)." );
        boolean result = true;
        validate(registration.getLogin());
        validate(registration.getFirstName());
        validate(registration.getLastName());
        validate(registration.getPassword());
        validate(registration.getConfirmPassword());
        validate(registration.getEmail());
        validate(registration.getCity());
        validate(registration.getUserRole());
        validate(registration.getGender());
    }

    private boolean checkCity(String city) {
        logger.info("Start checkCity(String city)." );
        boolean result = false;
        //TODO !!!

        result =  true;
        logger.info("Input city is correct." );
        return result;
    }

    private boolean checkEmail(String email) {
        logger.info("Start checkEmail(String email)." );
        boolean result = false;
        //TODO !!!

        result =  true;
        logger.info("Input email is correct." );
        return result;
    }

    private boolean checkLogin(String login) throws ServiceException {
        logger.info("Start checkLogin(Registration registration)." );
        boolean result = true;
        if (userDao.isLoginExist(login)){
            result = false;
            logger.error("Login = " + login + " has already been taken" );
            throw new ServiceException("Login has already been taken");
        }
        logger.info("Input login is available." );
        return result;
    }

    private boolean checkPassword(String password, String confirmPassword) throws ServiceException {
        logger.info("Start checkPassword(Registration registration)." );
        boolean result = false;
        //TODO max/ min length
        int maxLength = 100;
        int minLength = 1;
        Integer length = password.length();
        if ((length >= minLength) && (length <= maxLength)) {
            result = confirmPassword.equals(password);
        } else {
            logger.error("Input password is not available." );
            throw new ServiceException("Invalid password");
        }
        return result;
    }
}
