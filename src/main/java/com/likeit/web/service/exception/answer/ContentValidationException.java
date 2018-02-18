package com.likeit.web.service.exception.answer;

import com.likeit.web.service.exception.ServiceException;

public class ContentValidationException extends ServiceException {

    public ContentValidationException() {
        super();
    }

    public ContentValidationException(String message) {
        super(message);
    }

    public ContentValidationException(Exception e) {
        super(e);
    }

    public ContentValidationException(String message, Exception e) {
        super(message, e);
    }

}