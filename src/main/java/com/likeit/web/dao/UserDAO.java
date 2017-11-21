package main.java.com.likeit.web.dao;

import main.java.com.likeit.web.dao.exception.DAOException;
import main.java.com.likeit.web.domain.User;

public interface UserDAO {

    int getId(String login) throws DAOException;
    User getUser(String login, String password) throws DAOException;
    User getUser(int id) throws DAOException;
    void saveUser(String login, String password, String email) throws DAOException;

}
