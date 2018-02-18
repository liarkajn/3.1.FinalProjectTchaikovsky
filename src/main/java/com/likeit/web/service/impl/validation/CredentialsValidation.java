package com.likeit.web.service.impl.validation;

import com.likeit.web.service.exception.ServiceException;
import com.likeit.web.service.exception.user.EmailValidationException;
import com.likeit.web.service.exception.user.LoginValidationException;
import com.likeit.web.service.exception.user.PasswordMatchException;
import com.likeit.web.service.exception.user.PasswordValidationException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CredentialsValidation {

    private final static String LOGIN_REGEX = "^[A-Za-z][A-Za-z0-9_]{4,16}$";
    private final static String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,18}$";
    private final static String EMAIL_REGEX = "^([a-zA-Z0-9]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$";
    private Pattern loginPattern = Pattern.compile(LOGIN_REGEX);
    private Pattern passwordPattern = Pattern.compile(PASSWORD_REGEX);
    private Pattern emailPattern = Pattern.compile(EMAIL_REGEX);

    public boolean authorizationValidation(String login, String password) throws ServiceException {
        checkLogin(login);
        checkPassword(password);
        return true;
    }

    public boolean registrationValidation(String login, String password, String repeatedPassword, String email) throws ServiceException {
        checkLogin(login);
        checkPassword(password, repeatedPassword);
        checkEmail(email);
        return true;
    }

    private void checkLogin(String login) throws ServiceException {
        Matcher matcher = loginPattern.matcher(login);
        if (!matcher.matches()) {
            throw new LoginValidationException("User login has wrong format");
        }
    }

    private void checkPassword(String password) throws ServiceException {
        Matcher matcher = passwordPattern.matcher(password);
        if (!matcher.matches()) {
            throw new PasswordValidationException("User password has wrong format");
        }
    }

    private void checkPassword(String password, String repeatedPassword) throws ServiceException {
        checkPassword(password);
        if (!password.equals(repeatedPassword)) {
            throw new PasswordMatchException("Password and repeated password aren't similar");
        }
    }

    private void checkEmail(String email) throws ServiceException {
        Matcher matcher = emailPattern.matcher(email);
        if (!matcher.matches()) {
            throw new EmailValidationException("Email has wrong format");
        }
    }

}
