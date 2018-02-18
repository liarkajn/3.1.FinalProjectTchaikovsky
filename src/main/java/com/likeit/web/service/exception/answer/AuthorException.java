package com.likeit.web.service.exception.answer;

import com.likeit.web.service.exception.ServiceException;

public class AuthorException extends ServiceException {

    public AuthorException() {
        super();
    }

    public AuthorException(String message) {
        super(message);
    }

    public AuthorException(Exception e) {
        super(e);
    }

    public AuthorException(String message, Exception e) {
        super(message, e);
    }

}
