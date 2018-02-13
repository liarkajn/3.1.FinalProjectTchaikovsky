package com.likeit.web.dao;

import com.likeit.web.dao.exception.DAOException;
import com.likeit.web.domain.User;

import java.util.List;

public interface UserDAO {

    void createUser(String login, String password, String email) throws DAOException;
    void createUser(String login, String email, String password, String gender) throws DAOException;
    int getId(String login) throws DAOException;
    User readUser(String login, String password) throws DAOException;
    User readUser(int id) throws DAOException;
    List<User> readUsers() throws DAOException;
    List<User> readUsers(String searchString) throws DAOException;
    void updateUser(User user) throws DAOException;
    void updateUser(int id, boolean banned) throws DAOException;
    void deleteUser(int id) throws DAOException;

}
