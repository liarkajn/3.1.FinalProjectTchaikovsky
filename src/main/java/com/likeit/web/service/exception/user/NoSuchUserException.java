package com.likeit.web.service.exception.user;

import com.likeit.web.service.exception.ServiceException;

public class NoSuchUserException extends ServiceException {

    public NoSuchUserException() {
        super();
    }

    public NoSuchUserException(String message) {
        super(message);
    }

    public NoSuchUserException(Exception e) {
        super(e);
    }

    public NoSuchUserException(String message, Exception e) {
        super(message, e);
    }

}
