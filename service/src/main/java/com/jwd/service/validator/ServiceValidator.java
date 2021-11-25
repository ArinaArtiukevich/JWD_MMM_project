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
import com.jwd.service.util.ParameterAttribute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

import static com.jwd.service.util.ParameterAttribute.*;
import static java.util.Objects.isNull;

public class ServiceValidator {
    private static final Logger logger = LogManager.getLogger(ServiceValidator.class);
    private final UserDaoImpl userDao = new UserDaoImpl(new ConnectionPoolImpl(new DataBaseConfig()));
    private final List<String> availableSortByParameters = Arrays.asList("order_creation_date", "address", "service_type",
            "service_status", "description");
    private final List<String> availableDirectionParameters = Arrays.asList("ASC", "DESC");


    public void validate(Page<Order> orderPageRequest) throws ServiceException {
        validateIsNullPage(orderPageRequest);

        // TODO validate other parameters (pageNumber, limit)
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
        validateIsNullOrder(orderToBeAdded);
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
        boolean result = (checkCity(userInfo.getCity()) &&
                checkEmail(userInfo.getEmail()));
        if (result) {
            logger.info("User data is ready to be updated" );
        } else {
            logger.info("Invalid user data." );
        }
        return result;
    }

    public boolean validateUserWithoutPassword(Registration userInfo) throws ServiceException {
        logger.info("Start validateUserWithoutPassword(Registration registration)." );
        validateEmptyFieldsWithoutPassword(userInfo);
        boolean result = ( checkCity(userInfo.getCity()) &&
                checkEmail(userInfo.getEmail()));
        if (result) {
            logger.info("Invalid user data." );
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

    public boolean validateRegistrationData(Registration registration) throws ServiceException {
        logger.info("Start checkData(Registration registration)." );
        isNullRegistrationData(registration);
        validateEmptyFields(registration);
        boolean result = ( checkLogin(registration.getLogin()) &&
                checkCity(registration.getCity()) &&
                checkEmail(registration.getEmail()));
        if (result) {
            logger.info("User data is ready to be register" );
        } else {
            logger.info("Invalid user data." );
            throw new ServiceException("Invalid user data.");
        }
        return result;
    }

    private void isNullRegistrationData(Registration registration) throws ServiceException {
        if (isNull(registration)) {
            throw new ServiceException("User data is null.");
        }
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
        return city.matches(PATTERN_CITY);
    }

    private boolean checkEmail(String email) {
        logger.info("Start checkEmail(String email)." );
        return email.matches(PATTERN_EMAIL);
    }

    private boolean checkLogin(String login) throws ServiceException {
        logger.info("Start checkLogin(Registration registration)." );
        boolean result = false;
        if (userDao.isLoginExist(login)){
            logger.error("Login = " + login + " has already been taken" );
            throw new ServiceException("Login has already been taken");
        } else if (login.matches(PATTERN_LOGIN)) {
            result = true;
        }
        logger.info("Input login is available." );
        return result;
    }
// todo delete
//    private boolean checkPassword(String password, String confirmPassword) throws ServiceException {
//        logger.info("Start checkPassword(Registration registration)." );
//        boolean result = false;
//        //TODO now min is 1
//        int maxLength = PASSWORD_MAX_LENGTH;
//        int minLength = PASSWORD_MIN_LENGTH;
//        int length = password.length();
//        if ((length >= minLength) && (length <= maxLength)) {
//            result = confirmPassword.equals(password);
//        } else {
//            logger.error("Input password is not available." );
//            throw new ServiceException("Invalid password");
//        }
//        return result;
//    }

    private void validateIsNullPage(Page<Order> orderPageRequest) throws ServiceException {
        if (isNull(orderPageRequest)) {
            throw new ServiceException("OrderPageRequest is null.");
        }
    }

    private void validateIsNullOrder(Order order) throws ServiceException {
        if (isNull(order)) {
            throw new ServiceException("Order is null.");
        }
    }
}
