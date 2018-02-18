package com.likeit.web.service.exception.question;

import com.likeit.web.service.exception.ServiceException;

public class TopicValidationException extends ServiceException {

    public TopicValidationException() {
        super();
    }

    public TopicValidationException(String message) {
        super(message);
    }

    public TopicValidationException(Exception e) {
        super(e);
    }

    public TopicValidationException(String message, Exception e) {
        super(message, e);
    }

}
