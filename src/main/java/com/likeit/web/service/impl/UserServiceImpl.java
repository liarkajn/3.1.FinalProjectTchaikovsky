package main.java.com.likeit.web.service.impl;

import main.java.com.likeit.web.dao.DAOFactory;
import main.java.com.likeit.web.dao.UserDAO;
import main.java.com.likeit.web.dao.exception.DAOException;
import main.java.com.likeit.web.domain.User;
import main.java.com.likeit.web.service.UserService;
import main.java.com.likeit.web.service.exception.ServiceException;
import main.java.com.likeit.web.service.validation.UserValidation;

public class UserServiceImpl implements UserService {

    public User signIn(String login, String password) throws ServiceException {

        UserValidation validation = new UserValidation();
        validation.signInValidation(login, password);
        DAOFactory daoFactory = DAOFactory.getInstance();
        UserDAO userDAO = daoFactory.getUserDAO();
        User user;
        try {
            user = userDAO.getUser(login, password);
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
        return user;

    }

    public User signUp(String login, String password, String repeatedPassword, String email) throws ServiceException {
        UserValidation validation = new UserValidation();
        validation.signUpValidation(login, password, repeatedPassword, email);
        DAOFactory daoFactory = DAOFactory.getInstance();
        UserDAO userDAO = daoFactory.getUserDAO();
        User user;
        try {
            userDAO.saveUser(login, password, email);
            user = userDAO.getUser(login, password);
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
        return user;

    }

}
