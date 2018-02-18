package com.likeit.web.service.exception.user;

import com.likeit.web.service.exception.ServiceException;

public class InvalidPasswordException extends ServiceException {

    public InvalidPasswordException() {
        super();
    }

    public InvalidPasswordException(String message) {
        super(message);
    }

    public InvalidPasswordException(Exception e) {
        super(e);
    }

    public InvalidPasswordException(String message, Exception e) {
        super(message, e);
    }

}
