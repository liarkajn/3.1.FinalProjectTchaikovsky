package main.java.com.likeit.web.service.exception;

public enum Exceptions {

    USER_LOGIN_WRONG_FORMAT("User login has wrong format"),
    USER_EMAIL_WRONG_FORMAT("User email has wrong format"),
    USER_PASSWORD_LENGTH_FORMAT("Password length must be from 10 to 30 characters"),
    USER_PASSWORD_DONT_MATCH_WITH_REPEATED("Password and repeated password aren't similar"),
    USER_PASSWORD_WRONG_FORMAT("User password has wrong format");


    private String message;

    Exceptions(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
