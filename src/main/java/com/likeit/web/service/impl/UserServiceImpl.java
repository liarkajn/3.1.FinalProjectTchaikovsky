package com.likeit.web.service.impl;

import com.likeit.web.dao.DAOFactory;
import com.likeit.web.dao.UserDAO;
import com.likeit.web.dao.exception.DAOException;
import com.likeit.web.domain.User;
import com.likeit.web.service.UserService;
import com.likeit.web.service.exception.ServiceException;
import com.likeit.web.service.impl.validation.CredentialsValidation;
import com.likeit.web.service.impl.validation.UserValidation;

import java.util.List;

public class UserServiceImpl implements UserService {

    private CredentialsValidation credentialsValidation = new CredentialsValidation();
    private UserValidation userValidation = new UserValidation();

    @Override
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

    @Override
    public User adminSignIn(String login, String password) throws ServiceException {
        User user = signIn(login, password);
        if (!userValidation.isAdmin(user)) {
            throw new ServiceException("This user isn't administrator");
        }
        return user;
    }

    @Override
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

    @Override
    public User signUp(String login, String email, String password, String repeatedPassword, String gender) throws ServiceException {
        credentialsValidation.signUpValidation(login, password, repeatedPassword, email);
        DAOFactory daoFactory = DAOFactory.getInstance();
        UserDAO userDAO = daoFactory.getUserDAO();
        User user;
        try {
            userDAO.createUser(login, email, password, gender);
            user = userDAO.readUser(login, password);
        } catch (DAOException ex) {
            throw new ServiceException("Unable to sign up. Please, try later", ex);
        }
        return user;
    }

    @Override
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

    @Override
    public List<User> findUsers() throws ServiceException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        UserDAO userDAO = daoFactory.getUserDAO();
        List<User> users;
        try {
            users = userDAO.readUsers();
        } catch (DAOException ex) {
            throw new ServiceException("Unable to find users", ex);
        }
        return users;
    }

    @Override
    public void editUser(User user) throws ServiceException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        UserDAO userDAO = daoFactory.getUserDAO();
        try {
            userDAO.updateUser(user);
        } catch (DAOException ex) {
            throw new ServiceException("Unable to update user with id=" + user.getId(), ex);
        }
    }

    @Override
    public void banUser(int id, boolean banned) throws ServiceException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        UserDAO userDAO = daoFactory.getUserDAO();
        try {
            userDAO.updateUser(id, banned);
        } catch (DAOException ex) {
            throw new ServiceException("Unable to ban user with id=" + id, ex);
        }
    }

}
