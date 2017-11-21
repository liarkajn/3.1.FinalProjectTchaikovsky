package main.java.com.likeit.web.service.validation;

import main.java.com.likeit.web.service.exception.ServiceException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static main.java.com.likeit.web.service.exception.Exceptions.*;

public class UserValidation {

    private final static String LOGIN_REGEX = "^[a-zA-Z][a-zA-Z0-9._-]+$";
    private final static String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).+$";
    private final static String EMAIL_REGEX = "^([a-zA-Z0-9]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$";

    public boolean signInValidation(String login, String password) throws ServiceException {
        checkLogin(login);
        checkPassword(password);
        return true;
    }

    public boolean signUpValidation(String login, String password, String repeatedPassword, String email) throws ServiceException {
        checkLogin(login);
        checkPassword(password, repeatedPassword);
        checkEmail(email);
        return true;
    }

    private void checkLogin(String login) throws ServiceException {
        Pattern pattern = Pattern.compile(LOGIN_REGEX);
        Matcher matcher = pattern.matcher(login);
        if (!matcher.matches()) {
            throw new ServiceException(USER_LOGIN_WRONG_FORMAT.getMessage());
        }
    }

    private void checkPassword(String password) throws ServiceException {
        Pattern pattern = Pattern.compile(PASSWORD_REGEX);
        Matcher matcher = pattern.matcher(password);
        if (!matcher.matches()) {
            throw new ServiceException(USER_PASSWORD_WRONG_FORMAT.getMessage());
        }
        int length = password.length();
        if (length < 10 || length > 30) {
            throw  new ServiceException(USER_PASSWORD_LENGTH_FORMAT.getMessage());
        }
    }

    private void checkPassword(String password, String repeatedPassword) throws ServiceException {
        checkPassword(password);
        if (!password.equals(repeatedPassword)) {
            throw new ServiceException(USER_PASSWORD_DONT_MATCH_WITH_REPEATED.getMessage());
        }
    }

    private void checkEmail(String email) throws ServiceException {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            throw new ServiceException(USER_EMAIL_WRONG_FORMAT.getMessage());
        }
    }

}
