package com.jwd.service.validator;

import com.jwd.dao.entity.Order;
import com.jwd.dao.entity.Page;
import com.jwd.dao.entity.Registration;
import com.jwd.dao.entity.enumType.Gender;
import com.jwd.dao.entity.enumType.ServiceStatus;
import com.jwd.dao.entity.enumType.ServiceType;
import com.jwd.dao.entity.enumType.UserRole;
import com.jwd.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

import static com.jwd.service.util.ParameterAttribute.*;
import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

public class ServiceValidator {
    private static final Logger LOGGER = LogManager.getLogger(ServiceValidator.class);

    public void validate(Page<Order> orderPageRequest) throws ServiceException {
        validateIsNullPage(orderPageRequest);
        if (orderPageRequest.getPageNumber() < 1) {
            throw new ServiceException("Page number is invalid.");
        }
        if (orderPageRequest.getLimit() < 1) {
            throw new ServiceException("Page limit is invalid.");
        }
        if ((isNull(orderPageRequest.getSortBy()) || orderPageRequest.getSortBy().isEmpty()) &&
                !availableSortByParameters.contains(orderPageRequest.getSortBy())) {
            throw new ServiceException("Sort by parameter is not available.");
        }
        if ((isNull(orderPageRequest.getDirection()) || orderPageRequest.getDirection().isEmpty()) &&
                !availableDirectionParameters.contains(orderPageRequest.getDirection())) {
            throw new ServiceException("Direction parameter is not available.");
        }
    }

    public void validate(ServiceType serviceType) throws ServiceException {
        if (isNull(serviceType)) {
            throw new ServiceException("Service type is null.");
        }
    }

    public void validate(UserRole userRole) throws ServiceException {
        if (isNull(userRole)) {
            throw new ServiceException("UserRole type is null.");
        }
    }

    public void validate(Gender gender) throws ServiceException {
        if (isNull(gender)) {
            throw new ServiceException("Gender is null.");
        }
    }

    public void validate(ServiceStatus serviceStatus) throws ServiceException {
        if (isNull(serviceStatus)) {
            throw new ServiceException("Service status is null.");
        }
    }

    public void validate(Long id) throws ServiceException {
        if (isNull(id) || id <= 0) {
            throw new ServiceException("Id was not found.");
        }
    }

    public void validateOrder(Order orderToBeAdded) throws ServiceException {
        validateIsNullOrder(orderToBeAdded);
        validateOrderString(orderToBeAdded.getDescription());
        validateOrderAddress(orderToBeAdded.getAddress());
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

    private void validateOrderString(String string) throws ServiceException {
        validate(string);
        if (!string.matches(PATTERN_ORDER_STRING)) {
            throw new ServiceException("Invalid order parameter.");
        }
    }

    private void validateOrderAddress(String string) throws ServiceException {
        validate(string);
        if (!string.matches(PATTERN_ORDER_ADDRESS)) {
            throw new ServiceException("Invalid order address parameter.");
        }
    }

    public void validateServiceTypeString(String serviceTypeString) throws ServiceException {
        validate(serviceTypeString);
        if (!availableServiceTypeString.contains(serviceTypeString)) {
            throw new ServiceException("Invalid service type.");
        }
    }

    public boolean validateUserWithPassword(Registration userInfo) throws ServiceException {
        LOGGER.info("Start validateUserWithPassword(Registration registration).");
        validateEmptyFieldsWithPassword(userInfo);
        boolean result = (checkValidString(userInfo.getFirstName()) &&
                checkValidString(userInfo.getLastName()) &&
                checkValidString(userInfo.getCity()) &&
                checkEmail(userInfo.getEmail()));
        if (result) {
            LOGGER.info("User data is ready to be updated");
        } else {
            LOGGER.info("Invalid user data.");
        }
        return result;
    }

    public boolean validateUserWithoutPassword(Registration userInfo) throws ServiceException {
        LOGGER.info("Start validateUserWithoutPassword(Registration registration).");
        validateEmptyFieldsWithoutPassword(userInfo);
        boolean result = (checkValidString(userInfo.getFirstName()) &&
                checkValidString(userInfo.getLastName()) &&
                checkValidString(userInfo.getCity()) &&
                checkEmail(userInfo.getEmail()));
        if (result) {
            LOGGER.info("Invalid user data.");
        } else {
            LOGGER.info("User data is not ready to be updated");
        }
        return result;
    }

    private void validateEmptyFieldsWithPassword(Registration userInfo) throws ServiceException {
        LOGGER.info("Start validateEmptyFieldsWithoutPassword(Registration registration).");
        validateEmptyFieldsWithoutPassword(userInfo);
        validate(userInfo.getPassword());
        validate(userInfo.getConfirmPassword());
    }

    private void validateEmptyFieldsWithoutPassword(Registration userInfo) throws ServiceException {
        LOGGER.info("Start validateEmptyFieldsWithoutPassword(Registration registration).");
        validate(userInfo.getFirstName());
        validate(userInfo.getLastName());
        validate(userInfo.getEmail());
        validate(userInfo.getCity());
    }

    public boolean validateRegistrationData(Registration registration) throws ServiceException {
        LOGGER.info("Start checkData(Registration registration).");
        isNullRegistrationData(registration);
        validateEmptyFields(registration);
        boolean result = (checkValidString(registration.getFirstName()) &&
                checkValidString(registration.getLastName()) &&
                checkLogin(registration.getLogin()) &&
                checkValidString(registration.getCity()) &&
                checkEmail(registration.getEmail()));
        if (result) {
            LOGGER.info("User data is ready to be register");
        } else {
            LOGGER.info("Invalid user data.");
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
        LOGGER.info("Start validateEmptyFields(Registration registration).");
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

    private boolean checkValidString(String string) {
        LOGGER.info("Start checkValidString(String string).");
        return string.matches(PATTERN_STRING);
    }

    private boolean checkEmail(String email) {
        LOGGER.info("Start checkEmail(String email).");
        return email.matches(PATTERN_EMAIL);
    }

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

    public boolean checkLogin(String login) {
        LOGGER.info("Start validateLogin(String login).");
        return login.matches(PATTERN_LOGIN);
    }

}
