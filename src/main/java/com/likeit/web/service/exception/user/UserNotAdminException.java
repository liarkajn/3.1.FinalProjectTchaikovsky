package com.likeit.web.service.exception.user;

import com.likeit.web.service.exception.ServiceException;

public class UserNotAdminException extends ServiceException {

    public UserNotAdminException() {
        super();
    }

    public UserNotAdminException(String message) {
        super(message);
    }

    public UserNotAdminException(Exception e) {
        super(e);
    }

    public UserNotAdminException(String message, Exception e) {
        super(message, e);
    }

}
