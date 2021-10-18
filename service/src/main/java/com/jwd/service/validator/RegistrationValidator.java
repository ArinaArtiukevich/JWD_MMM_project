package com.jwd.service.validator;

import com.jwd.dao.entity.Registration;
import com.jwd.dao.repository.impl.UserDaoImpl;
import com.jwd.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RegistrationValidator {

    private static final Logger logger = LogManager.getLogger(RegistrationValidator.class);

    public static boolean checkData(Registration registration) throws ServiceException {
        logger.info("Start checkData(Registration registration)." );
        boolean result = (!isEmptyFields(registration) &&
                checkPassword(registration) &&
                checkLogin(registration) &&
                checkCity(registration) &&
                checkEmail(registration));
        if (result) {
            logger.info("Client data is ready to be register" );
        } else {
            logger.info("Client data is not ready to be register" );
        }
        return result;
    }


    private static boolean isEmptyFields(Registration registration) throws ServiceException {
        logger.info("Start isEmptyFields(Registration registration)." );
        boolean result = true;
        if((registration.getLogin() != null) &&
                (registration.getFirstName() != null) &&
                (registration.getLastName() != null) &&
                (registration.getPassword() != null) &&
                (registration.getConfirmPassword() != null) &&
                (registration.getEmail() != null) &&
                (registration.getCity() != null)
        ) {
            if((!registration.getLogin().isEmpty()) &&
                    (!registration.getFirstName().isEmpty()) &&
                    (!registration.getLastName().isEmpty()) &&
                    (!registration.getPassword().isEmpty()) &&
                    (!registration.getConfirmPassword().isEmpty()) &&
                    (!registration.getCity().isEmpty()) &&
                    (!registration.getEmail().isEmpty())
            ) {
                result = false;
            } else {
                logger.info("Registration parameters have empty field." );
                throw new ServiceException("Registration parameters have empty field.");
            }
        }
        logger.info("Registration parameters have not empty field." );
        return result;

    }

    private static boolean checkCity(Registration registration){
        logger.info("Start checkCity(Registration registration)." );
        boolean result = false;
        //TODO !!!

        result =  true;
        logger.info("Input city is correct." );
        return result;
    }

    private static boolean checkEmail(Registration registration){
        logger.info("Start checkEmail(Registration registration)." );
        boolean result = false;
        //TODO !!!

        result =  true;
        logger.info("Input email is correct." );
        return result;
    }

    private static boolean checkLogin(Registration registration) throws ServiceException {
        logger.info("Start checkLogin(Registration registration)." );
        boolean result = true;
        UserDaoImpl clientDAO = new UserDaoImpl();

        if (clientDAO.isLoginExist(registration.getLogin())){
            result = false;
            logger.error("Login = " + registration.getLogin() + " has already been taken" );
            throw new ServiceException("Login has already been taken");
        }
        logger.info("Input login is available." );
        return result;
    }

    private static boolean checkPassword(Registration registration) throws ServiceException {
        logger.info("Start checkPassword(Registration registration)." );
        boolean result = false;
        //TODO max/ min length
        int maxLength = 100;
        int minLength = 1;
        Integer length = registration.getPassword().length();
        if((length >= minLength) && (length <= maxLength)) {
            logger.info("Input password is available." );
            result = registration.getConfirmPassword().equals(registration.getPassword());
        } else {
            logger.error("Input password is not available." );
            throw new ServiceException("Invalid password");
        }
        if(registration.getPassword().equals(registration.getConfirmPassword())) {
            logger.info("Input password equals confirmPassword." );
            result = registration.getConfirmPassword().equals(registration.getPassword());
        } else {
            logger.error("Input password does not equal confirmPassword." );
            throw new ServiceException("confirmPassword does not match password");
        }
        return result;
    }
}
