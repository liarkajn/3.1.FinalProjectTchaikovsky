package com.likeit.web.service.exception.user;

import com.likeit.web.service.exception.ServiceException;

public class ProfileOwnerException extends ServiceException {

    public ProfileOwnerException() {
        super();
    }

    public ProfileOwnerException(String message) {
        super(message);
    }

    public ProfileOwnerException(Exception e) {
        super(e);
    }

    public ProfileOwnerException(String message, Exception e) {
        super(message, e);
    }

}
