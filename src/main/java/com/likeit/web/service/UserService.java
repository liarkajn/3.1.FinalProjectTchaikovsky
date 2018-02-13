package com.likeit.web.service;

import com.likeit.web.domain.User;
import com.likeit.web.service.exception.ServiceException;

import java.util.List;

public interface UserService {

    User signUp(String login, String password, String repeatedPassword, String email) throws ServiceException;
    User signUp(String login, String email, String password, String repeatedPassword, String gender) throws ServiceException;
    User signIn(String login, String password) throws ServiceException;
    User adminSignIn(String login, String password) throws ServiceException;
    User findUser(int id) throws ServiceException;
    List<User> findUsers() throws ServiceException;
    void editUser(User user) throws ServiceException;
    void banUser(int id, boolean banned) throws ServiceException;

}
