package com.likeit.web.service.exception.question;

import com.likeit.web.service.exception.ServiceException;

public class NoSuchQuestionException extends ServiceException {

    public NoSuchQuestionException() {
        super();
    }

    public NoSuchQuestionException(String message) {
        super(message);
    }

    public NoSuchQuestionException(Exception e) {
        super(e);
    }

    public NoSuchQuestionException(String message, Exception e) {
        super(message, e);
    }

}
