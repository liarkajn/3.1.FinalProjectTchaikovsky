package main.java.com.likeit.web.service.impl;

import main.java.com.likeit.web.dao.DAOFactory;
import main.java.com.likeit.web.dao.UserDAO;
import main.java.com.likeit.web.dao.exception.DAOException;
import main.java.com.likeit.web.domain.User;
import main.java.com.likeit.web.service.UserService;
import main.java.com.likeit.web.service.exception.ServiceException;
import main.java.com.likeit.web.service.impl.validation.CredentialsValidation;
import main.java.com.likeit.web.service.impl.validation.UserValidation;

public class UserServiceImpl implements UserService {

    private CredentialsValidation credentialsValidation = new CredentialsValidation();
    private UserValidation userValidation = new UserValidation();

    public User signIn(String login, String password) throws ServiceException {

        credentialsValidation.signInValidation(login, password);
        DAOFactory daoFactory = DAOFactory.getInstance();
        UserDAO userDAO = daoFactory.getUserDAO();
        User user;
        try {
            user = userDAO.readUser(login, password);
            userValidation.validate(user);
        } catch (DAOException ex) {
            throw new ServiceException("Unable to login. Please, try later", ex);
        }
        return user;

    }

    public User signUp(String login, String password, String repeatedPassword, String email) throws ServiceException {
        credentialsValidation.signUpValidation(login, password, repeatedPassword, email);
        DAOFactory daoFactory = DAOFactory.getInstance();
        UserDAO userDAO = daoFactory.getUserDAO();
        User user;
        try {
            userDAO.createUser(login, password, email);
            user = userDAO.readUser(login, password);
        } catch (DAOException ex) {
            throw new ServiceException("Unable to sign up. Please, try later", ex);
        }
        return user;
    }

    public User findUser(int id) throws ServiceException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        UserDAO userDAO = daoFactory.getUserDAO();
        User user;
        try {
            user = userDAO.readUser(id);
            userValidation.validate(user);
        } catch (DAOException ex) {
            throw new ServiceException("Unable to find user with id + " + id, ex);
        }
        return user;
    }

}
