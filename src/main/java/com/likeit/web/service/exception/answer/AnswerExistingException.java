package com.likeit.web.service.exception.answer;

public class AnswerExistingException extends SecurityException {

    public AnswerExistingException() {
        super();
    }

    public AnswerExistingException(String message) {
        super(message);
    }

    public AnswerExistingException(Exception e) {
        super(e);
    }

    public AnswerExistingException(String message, Exception e) {
        super(message, e);
    }

}
