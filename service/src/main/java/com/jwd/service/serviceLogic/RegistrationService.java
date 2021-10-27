package com.jwd.service.serviceLogic;

import com.jwd.dao.entity.Registration;
import com.jwd.dao.exception.DaoException;
import com.jwd.dao.repository.impl.UserDaoImpl;
import com.jwd.service.exception.ServiceException;
import com.jwd.service.validator.RegistrationValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RegistrationService {

    private static final Logger logger = LogManager.getLogger(RegistrationService.class);

    public static boolean register(Registration registration) throws ServiceException {

        //TODO ENCODED PASSWORD

        UserDaoImpl clientDao = new UserDaoImpl();
        boolean testClient = false;
        boolean testRegistration = false;
        try {
            testRegistration = RegistrationValidator.checkData(registration);
            testClient = clientDao.addUser(registration);

        } catch (ServiceException | DaoException e) {
            logger.error("Invalid input parameters.");
            throw new ServiceException(e);
        }
        return testRegistration && testClient;

    }
}
