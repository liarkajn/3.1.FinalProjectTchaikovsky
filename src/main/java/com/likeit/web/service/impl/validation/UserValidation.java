package com.likeit.web.service.impl.validation;

import com.likeit.web.domain.User;
import com.likeit.web.service.exception.ServiceException;

public class UserValidation {

    public void validate(User user) throws ServiceException {
        if (user == null) {
            throw new ServiceException("No such user");
        }
    }

    public boolean isAdmin(User user) {
        return user.getRole() == 0 || user.getRole() == 1;
    }

}
