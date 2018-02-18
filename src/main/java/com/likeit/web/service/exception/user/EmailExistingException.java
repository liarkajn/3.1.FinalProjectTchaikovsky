package com.likeit.web.service.exception.user;

import com.likeit.web.service.exception.ServiceException;

public class EmailExistingException extends ServiceException {

    public EmailExistingException() {
        super();
    }

    public EmailExistingException(String message) {
        super(message);
    }

    public EmailExistingException(Exception e) {
        super(e);
    }

    public EmailExistingException(String message, Exception e) {
        super(message, e);
    }

}
