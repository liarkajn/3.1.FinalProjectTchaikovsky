package main.java.com.likeit.web.service.exception;

public enum Exceptions {

    USER_LOGIN_WRONG_FORMAT("User login has wrong format"),
    USER_EMAIL_WRONG_FORMAT("User email has wrong format"),
    USER_PASSWORD_LENGTH_FORMAT("Password length must be from 8 to 30 characters"),
    USER_PASSWORD_DONT_MATCH_WITH_REPEATED("Password and repeated password aren't similar"),
    USER_PASSWORD_WRONG_FORMAT("User password has wrong format. The password must have 1 capital letter, 1 digit, 1 small letter"),
    NO_SUCH_USER("Login or password is wrong"),
    UNABLE_TO_LOGIN("Unable to login. Please, try later"),
    UNABLE_TO_SIGNUP("Unable to sign up. Please, try later"),
    UNABLE_TO_CREATE_QUESTION("Unable to create question. Please try later."),
    UNABLE_FIND_QUESTION("Unable to find questions. Please try later"),
    UNABLE_FIND_USER("Unable to find user.");

    private String message;

    Exceptions(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
