package com.likeit.web.controller.handler.command.impl.exception.user;

public class BannedException extends Exception {

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
