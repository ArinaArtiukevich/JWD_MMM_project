package com.jwd.service.validator;

import com.jwd.dao.entity.Order;
import com.jwd.dao.entity.enums.ServiceStatus;
import com.jwd.dao.entity.enums.ServiceType;
import com.jwd.dao.entity.enums.UserRole;
import com.jwd.dao.exception.DaoException;
import com.jwd.service.exception.ServiceException;

import java.util.Date;

import static java.util.Objects.isNull;

public class ServiceValidator {
    public void validate(ServiceType serviceType) throws ServiceException {
        if(serviceType == null) {
            throw new ServiceException("Service type is null.");
        }
    }

    public void validate(UserRole userRole) throws ServiceException {
        if(userRole == null) {
            throw new ServiceException("UserRole type is null.");
        }
    }

    public void validate(ServiceStatus serviceStatus) throws ServiceException {
        if(serviceStatus == null) {
            throw new ServiceException("Service type is null.");
        }
    }

    public void validate(Long id) throws ServiceException {
        if(isNull(id) || id <= 0) {
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
        if(isNull(string) || string.isEmpty()) {
            throw new ServiceException("Invalid parameter was not found.");
        }
    }
}
