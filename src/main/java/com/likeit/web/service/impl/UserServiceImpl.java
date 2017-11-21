package main.java.com.likeit.web.service.impl;

import main.java.com.likeit.web.dao.DAOFactory;
import main.java.com.likeit.web.dao.UserDAO;
import main.java.com.likeit.web.dao.exception.DAOException;
import main.java.com.likeit.web.domain.User;
import main.java.com.likeit.web.service.UserService;
import main.java.com.likeit.web.service.exception.ServiceException;
import main.java.com.likeit.web.service.validation.CredentialsValidation;
import main.java.com.likeit.web.service.validation.UserValidation;

public class UserServiceImpl implements UserService {

    private CredentialsValidation credentialsValidation = new CredentialsValidation();
    private UserValidation userValidation = new UserValidation();

    public User signIn(String login, String password) throws ServiceException {

        credentialsValidation.signInValidation(login, password);
        DAOFactory daoFactory = DAOFactory.getInstance();
        UserDAO userDAO = daoFactory.getUserDAO();
        User user;
        try {
            user = userDAO.getUser(login, password);
            userValidation.validate(user);
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
        return user;

    }

    public User signUp(String login, String password, String repeatedPassword, String email) throws ServiceException {
        credentialsValidation.signUpValidation(login, password, repeatedPassword, email);
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
