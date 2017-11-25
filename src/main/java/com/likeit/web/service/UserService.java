package main.java.com.likeit.web.service;

import main.java.com.likeit.web.domain.User;
import main.java.com.likeit.web.service.exception.ServiceException;

public interface UserService {

    User signUp(String login, String password, String repeatedPassword, String email) throws ServiceException;
    User signIn(String login, String password) throws ServiceException;

}
