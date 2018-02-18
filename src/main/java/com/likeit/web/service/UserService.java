package com.likeit.web.service;

import com.likeit.web.domain.Gender;
import com.likeit.web.domain.User;
import com.likeit.web.service.exception.ServiceException;

import java.util.List;

public interface UserService {

    User registration(String login, String email, String password, String repeatedPassword, Gender gender) throws ServiceException;
    User authorization(String login, String password) throws ServiceException;
    User adminAuthorization(String login, String password) throws ServiceException;
    User findUser(int id) throws ServiceException;
    List<User> findUsers(int limit, int page) throws ServiceException;
    int getUsersCount() throws ServiceException;
    void editUser(int userId, User target) throws ServiceException;
    void adminEditUser(int adminId, User target) throws ServiceException;
    void banUser(int adminId, int id, boolean banned) throws ServiceException;

}
