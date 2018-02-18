package com.likeit.web.service.exception.user;

import com.likeit.web.service.exception.ServiceException;

public class BannedException extends ServiceException {

    public BannedException() {
        super();
    }

    public BannedException(String message) {
        super(message);
    }

    public BannedException(Exception e) {
        super(e);
    }

    public BannedException(String message, Exception e) {
        super(message, e);
    }

}
