package com.jwd.service.validator;

import com.jwd.dao.entity.Order;
import com.jwd.dao.entity.Page;
import com.jwd.dao.entity.Registration;
import com.jwd.dao.entity.enums.Gender;
import com.jwd.dao.entity.enums.UserRole;
import com.jwd.service.exception.ServiceException;
import org.junit.Test;

public class ServiceValidatorUnitTest {

    private ServiceValidator validator = new ServiceValidator();

    @Test
    public void testValidate_validString() throws ServiceException {
        final String test = "test";

        validator.validate(test);
    }

    @Test(expected = ServiceException.class)
    public void testValidate_invalidString() throws ServiceException {
        final String test = "";

        validator.validate(test);
    }

    @Test
    public void testValidate_validPage() throws ServiceException {
        final Page<Order> orderPageRequest = new Page<>();

        validator.validate(orderPageRequest);
    }

    @Test(expected = ServiceException.class)
    public void testValidate_invalidPage() throws ServiceException {
        final Page<Order> orderPageRequest = null;

        validator.validate(orderPageRequest);
    }

    @Test(expected = ServiceException.class)
    public void testValidateRegistrationData_invalid_null() throws ServiceException {
        final Registration registration = null;

        validator.validateRegistrationData(registration);
    }

    @Test(expected = ServiceException.class)
    public void testValidateRegistrationData_invalid_nullLogin() throws ServiceException {
        final Registration registration = new Registration( "firstName", "lastName", "email@ro.ru", "city", null, "password", "password", Gender.FEMALE,  UserRole.CLIENT);

        validator.validateRegistrationData(registration);
    }

    @Test
    public void testValidateRegistrationData_valid() throws ServiceException {
        final Registration registration = new Registration( "firstName", "lastName", "email@ro.ru", "city", "login", "password", "password", Gender.FEMALE,  UserRole.CLIENT);

        validator.validateRegistrationData(registration);
    }
}