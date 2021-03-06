package com.likeit.web.dao;

import com.likeit.web.dao.impl.SQLAnswerDAO;
import com.likeit.web.dao.impl.SQLQuestionDAO;
import com.likeit.web.dao.impl.SQLUserDAO;
import com.likeit.web.dao.impl.SQLVotingDAO;

public class DAOFactory {

    private final static DAOFactory instance = new DAOFactory();

    private AnswerDAO answerDAO = new SQLAnswerDAO();
    private QuestionDAO questionDAO = new SQLQuestionDAO();
    private UserDAO userDAO = new SQLUserDAO();
    private VotingDAO votingDAO = new SQLVotingDAO();

    private DAOFactory() { }

    public static DAOFactory getInstance() {
        return instance;
    }

    public AnswerDAO getAnswerDAO() {
        return answerDAO;
    }

    public QuestionDAO getQuestionDAO() {
        return questionDAO;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public VotingDAO getVotingDAO() {
        return votingDAO;
    }

}
