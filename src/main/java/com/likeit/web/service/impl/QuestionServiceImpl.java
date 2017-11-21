package main.java.com.likeit.web.service.impl;

import main.java.com.likeit.web.dao.DAOFactory;
import main.java.com.likeit.web.dao.QuestionDAO;
import main.java.com.likeit.web.dao.exception.DAOException;
import main.java.com.likeit.web.domain.Question;
import main.java.com.likeit.web.service.QuestionService;
import main.java.com.likeit.web.service.exception.ServiceException;

import java.util.LinkedList;
import java.util.List;

public class QuestionServiceImpl implements QuestionService {

    @Override
    public void createQuestion(String topic, String content, String authorLogin) throws ServiceException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        QuestionDAO questionDAO = daoFactory.getQuestionDAO();
        try {
            questionDAO.saveQuestion(topic, content, authorLogin);
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    @Override
    public List<Question> getAll() throws ServiceException {
        List<Question> questions;
        DAOFactory daoFactory = DAOFactory.getInstance();
        QuestionDAO questionDAO = daoFactory.getQuestionDAO();
        try {
            questions = questionDAO.getQuestions();
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
        return questions;
    }
}
