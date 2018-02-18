package com.likeit.web.service.exception.user;

import com.likeit.web.service.exception.ServiceException;

public class LoginExistingException extends ServiceException {

    public LoginExistingException() {
        super();
    }

    public LoginExistingException(String message) {
        super(message);
    }

    public LoginExistingException(Exception e) {
        super(e);
    }

    public LoginExistingException(String message, Exception e) {
        super(message, e);
    }

}
