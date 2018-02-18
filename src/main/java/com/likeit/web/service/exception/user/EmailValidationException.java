package com.likeit.web.service.exception.user;

import com.likeit.web.service.exception.ServiceException;

public class EmailValidationException extends ServiceException {

    public EmailValidationException() {
        super();
    }

    public EmailValidationException(String message) {
        super(message);
    }

    public EmailValidationException(Exception e) {
        super(e);
    }

    public EmailValidationException(String message, Exception e) {
        super(message, e);
    }

}
