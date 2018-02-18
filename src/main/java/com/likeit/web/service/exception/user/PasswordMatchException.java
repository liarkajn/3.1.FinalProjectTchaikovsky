package com.likeit.web.service.exception.user;

import com.likeit.web.service.exception.ServiceException;

public class PasswordMatchException extends ServiceException {

    public PasswordMatchException() {
        super();
    }

    public PasswordMatchException(String message) {
        super(message);
    }

    public PasswordMatchException(Exception e) {
        super(e);
    }

    public PasswordMatchException(String message, Exception e) {
        super(message, e);
    }

}
