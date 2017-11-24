package main.java.com.likeit.web.dao;

import main.java.com.likeit.web.dao.impl.SQLQuestionDAO;
import main.java.com.likeit.web.dao.impl.SQLUserDAO;

public class DAOFactory {

    private final static DAOFactory instance = new DAOFactory();

    private QuestionDAO questionDAO = new SQLQuestionDAO();
    private UserDAO userDAO = new SQLUserDAO();

    private DAOFactory() { }

    public static DAOFactory getInstance() {
        return instance;
    }

    public QuestionDAO getQuestionDAO() {
        return questionDAO;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

}
