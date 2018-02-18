package com.likeit.web.service.exception.user;

import com.likeit.web.service.exception.ServiceException;

public class PasswordValidationException extends ServiceException {

    public PasswordValidationException() {
        super();
    }

    public PasswordValidationException(String message) {
        super(message);
    }

    public PasswordValidationException(Exception e) {
        super(e);
    }

    public PasswordValidationException(String message, Exception e) {
        super(message, e);
    }

}
