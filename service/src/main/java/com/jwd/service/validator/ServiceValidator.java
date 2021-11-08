package com.jwd.service.validator;

import com.jwd.dao.entity.enums.ServiceType;
import com.jwd.service.exception.ServiceException;

public class ServiceValidator {
    public void validate(ServiceType serviceType) throws ServiceException {
        if(serviceType == null) {
            throw new ServiceException("Service type is null.");
        }
    }
}
