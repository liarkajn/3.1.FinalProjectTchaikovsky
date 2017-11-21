package main.java.com.likeit.web.service.validation;

import main.java.com.likeit.web.domain.User;
import main.java.com.likeit.web.service.exception.ServiceException;

import static main.java.com.likeit.web.service.exception.Exceptions.NO_SUCH_USER;

public class UserValidation {

    public void validate(User user) throws ServiceException {
        if (user == null) {
            throw new ServiceException(NO_SUCH_USER.getMessage());
        }
    }

}
