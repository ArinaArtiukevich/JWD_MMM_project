package com.jwd.controller.validator;

import com.jwd.controller.exception.ControllerException;
import com.jwd.dao.entity.enums.Gender;
import com.jwd.dao.entity.enums.ServiceStatus;
import com.jwd.dao.entity.enums.ServiceType;
import com.jwd.dao.entity.enums.UserRole;

import java.util.Date;

import static java.util.Objects.isNull;

public class ControllerValidator {
    public void isValid(String string) throws ControllerException {
        if (isNull(string) || string.isEmpty()) {
            throw new ControllerException("Parameter is null or empty.");
        }
    }

    public void isValid(Object object) throws ControllerException {
        if (isNull(object)) {
            throw new ControllerException("Object is null.");
        }
    }

    public void isValid(ServiceStatus serviceStatus) throws ControllerException {
        if (isNull(serviceStatus)) {
            throw new ControllerException("ServiceStatus is null.");
        }
    }

    public void isValid(Date date) throws ControllerException {
        if (isNull(date)) {
            throw new ControllerException("Date is null.");
        }
    }

    public void isValid(ServiceType serviceType) throws ControllerException {
        if (isNull(serviceType)) {
            throw new ControllerException("ServiceType is null or empty.");
        }
    }

    public void isValid(UserRole userRole) throws ControllerException {
        if (isNull(userRole)) {
            throw new ControllerException("UserRole is null.");
        }
    }

    public void isValid(Gender gender) throws ControllerException {
        if (isNull(gender)) {
            throw new ControllerException("Gender is null.");
        }
    }
}
