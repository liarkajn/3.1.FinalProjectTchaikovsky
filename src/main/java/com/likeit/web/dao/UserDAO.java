package main.java.com.likeit.web.dao;

import main.java.com.likeit.web.dao.exception.DAOException;
import main.java.com.likeit.web.domain.User;

import java.util.List;

public interface UserDAO {

    void createUser(String login, String password, String email) throws DAOException;
    int getId(String login) throws DAOException;
    User readUser(String login, String password) throws DAOException;
    User readUser(int id) throws DAOException;
    List<User> readUsers(String searchString) throws DAOException;
    void updateUser(User user) throws DAOException;
    void deleteUser(int id) throws DAOException;

}
