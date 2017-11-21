package main.java.com.likeit.web.service.impl;

import main.java.com.likeit.web.dao.DAOFactory;
import main.java.com.likeit.web.dao.UserDAO;
import main.java.com.likeit.web.dao.exception.DAOException;
import main.java.com.likeit.web.domain.User;
import main.java.com.likeit.web.service.UserService;
import main.java.com.likeit.web.service.exception.ServiceException;

public class UserServiceImpl implements UserService {

    public User signIn(String login, String password) throws ServiceException {

        if (!login.isEmpty() && !password.isEmpty()) {
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
        return new User();
    }

    public User signUp(String login, String password, String repeatedPassword, String email) throws ServiceException {

        if (password.isEmpty() || !password.equals(repeatedPassword)) {
            return new User();
        }
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
