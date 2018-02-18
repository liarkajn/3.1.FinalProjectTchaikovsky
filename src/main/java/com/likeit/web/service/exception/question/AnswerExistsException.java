package com.likeit.web.service.exception.question;

import com.likeit.web.service.exception.ServiceException;

public class AnswerExistsException extends ServiceException {

    public AnswerExistsException() {
        super();
    }

    public AnswerExistsException(String message) {
        super(message);
    }

    public AnswerExistsException(Exception e) {
        super(e);
    }

    public AnswerExistsException(String message, Exception e) {
        super(message, e);
    }

}
