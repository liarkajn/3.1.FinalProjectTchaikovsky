package com.likeit.web.service.impl;

import com.likeit.web.dao.DAOFactory;
import com.likeit.web.dao.UserDAO;
import com.likeit.web.dao.exception.DAOException;
import com.likeit.web.domain.Gender;
import com.likeit.web.domain.User;
import com.likeit.web.service.UserService;
import com.likeit.web.service.exception.ServiceException;
import com.likeit.web.service.exception.user.EmailExistingException;
import com.likeit.web.service.exception.user.LoginExistingException;
import com.likeit.web.service.exception.user.UserNotAdminException;
import com.likeit.web.service.impl.validation.CredentialsValidation;
import com.likeit.web.service.impl.validation.UserValidation;

import java.util.List;

public class UserServiceImpl implements UserService {

    private UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
    private CredentialsValidation credentialsValidation = new CredentialsValidation();
    private UserValidation userValidation = new UserValidation();

    @Override
    public User authorization(String login, String password) throws ServiceException {
        credentialsValidation.authorizationValidation(login, password);
        User user = findUserByLoginAndPassword(login, password);
        userValidation.validate(user);
        userValidation.isActive(user);
        return user;

    }

    @Override
    public User adminAuthorization(String login, String password) throws ServiceException {
        credentialsValidation.authorizationValidation(login, password);
        User admin = findUserByLoginAndPassword(login, password);
        userValidation.validate(admin);
        if (!userValidation.isAdmin(admin)) {
            throw new UserNotAdminException("User with login " + login + "trying sign in to administration panel");
        }
        return admin;
    }

    @Override
    public User registration(String login, String email, String password, String repeatedPassword, Gender gender) throws ServiceException {
        credentialsValidation.registrationValidation(login, password, repeatedPassword, email);
        if (findUserByLogin(login) != null) {
            throw new LoginExistingException("Unable to sign up. User with such login exists");
        }
        if (findUserByEmail(email) != null) {
            throw new EmailExistingException("Unable to sign up. User with such email exists");
        }
        User user;
        try {
            userDAO.createUser(login, email, password, gender);
        } catch (DAOException ex) {
            throw new ServiceException("Unable to register new user. Problems with database", ex);
        }
        user = findUserByLoginAndPassword(login, password);
        return user;
    }

    @Override
    public User findUser(int id) throws ServiceException {
        User user = findUserById(id);
        userValidation.validate(user);
        return user;
    }

    private User findUserById(int id) throws ServiceException {
        User user;
        try {
            user = userDAO.readUser(id);
        } catch (DAOException ex) {
            throw new ServiceException("Problems with database. Unable to find user with id + " + id, ex);
        }
        return user;
    }

    private User findUserByLoginAndPassword(String login, String password) throws ServiceException {
        User user;
        try {
            user = userDAO.readUser(login, password);
        } catch (DAOException ex) {
            throw new ServiceException("Unable to find user by login and password. Problems with database", ex);
        }
        return user;
    }

    private User findUserByLogin(String login) throws ServiceException {
        User user;
        try {
            user = userDAO.readUserByLogin(login);
        } catch (DAOException ex) {
            throw new ServiceException("Unable to find user by login :" + login + ". Problems with database", ex);
        }
        return user;
    }

    private User findUserByEmail(String email) throws ServiceException {
        User user;
        try {
            user = userDAO.readUserByEmail(email);
        } catch (DAOException ex) {
            throw new ServiceException("Unable to find user by email :" + email + ". Problems with database", ex);
        }
        return user;
    }

    @Override
    public List<User> findUsers(int limit, int page) throws ServiceException {
        List<User> users;
        int offset = limit * (page - 1);
        try {
            users = userDAO.readUsers(limit, offset);
        } catch (DAOException ex) {
            throw new ServiceException("Problems with database. Unable to find users", ex);
        }
        return users;
    }

    @Override
    public int getUsersCount() throws ServiceException {
        int count;
        try {
            count = userDAO.readUsersCount();
        } catch (DAOException e) {
            throw new ServiceException("Problems with database. Unable get users count", e);
        }
        return count;
    }

    @Override
    public void editUser(int userId, User target) throws ServiceException {
        User profile = findUser(target.getId());
        userValidation.validate(profile);
        userValidation.isActive(profile);
        User user = findUser(userId);
        userValidation.ownerValidation(user, profile);
        editUser(target);
    }

    @Override
    public void adminEditUser(int adminId, User target) throws ServiceException {
        User profile = findUser(target.getId());
        userValidation.validate(profile);
        User admin = findUser(adminId);
        if (!userValidation.isAdmin(admin)) {
            throw new UserNotAdminException("User with id " + adminId + "trying edit user with administration panel");
        }
        editUser(target);
    }

    private void editUser(User user) throws ServiceException {
        try {
            userDAO.updateUser(user);
        } catch (DAOException ex) {
            throw new ServiceException("Problems with database. Unable update user with id=" + user.getId(), ex);
        }
    }

    @Override
    public void banUser(int adminId, int id, boolean banned) throws ServiceException {
        User profile = findUser(id);
        userValidation.validate(profile);
        User admin = findUser(adminId);
        if (!userValidation.isAdmin(admin)) {
            throw new UserNotAdminException("User with id " + adminId + "trying ban user with administration panel");
        }
        banUser(id, banned);
    }

    private void banUser(int id, boolean banned) throws ServiceException {
        try {
            userDAO.updateUser(id, banned);
        } catch (DAOException ex) {
            throw new ServiceException("Problems with database. Unable to ban user with id=" + id, ex);
        }
    }

}
