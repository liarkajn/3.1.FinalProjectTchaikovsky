package com.likeit.web.dao;

import com.likeit.web.dao.exception.DAOException;
import com.likeit.web.domain.Gender;
import com.likeit.web.domain.User;

import java.util.List;

public interface UserDAO {

    void createUser(String login, String email, String password, Gender gender) throws DAOException;
    int getId(String login) throws DAOException;
    User readUser(int id) throws DAOException;
    User readUser(String login, String password) throws DAOException;
    User readUserByLogin(String login) throws DAOException;
    User readUserByEmail(String email) throws DAOException;
    List<User> readUsers(int limit, int offset) throws DAOException;
    int readUsersCount() throws DAOException;
    void updateUser(User user) throws DAOException;
    void updateUser(int id, boolean banned) throws DAOException;

}
