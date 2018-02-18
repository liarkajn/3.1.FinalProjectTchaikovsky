package com.likeit.web.service.exception.user;

import com.likeit.web.service.exception.ServiceException;

public class LoginValidationException extends ServiceException {

    public LoginValidationException() {
        super();
    }

    public LoginValidationException(String message) {
        super(message);
    }

    public LoginValidationException(Exception e) {
        super(e);
    }

    public LoginValidationException(String message, Exception e) {
        super(message, e);
    }

}
