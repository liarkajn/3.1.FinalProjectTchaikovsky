package main.java.com.likeit.web.service.impl.validation;

import main.java.com.likeit.web.domain.User;
import main.java.com.likeit.web.service.exception.ServiceException;

public class UserValidation {

    public void validate(User user) throws ServiceException {
        if (user == null) {
            throw new ServiceException("No such user");
        }
    }

}
