package main.java.com.likeit.web.service.impl;

import main.java.com.likeit.web.dao.DAOFactory;
import main.java.com.likeit.web.dao.QuestionDAO;
import main.java.com.likeit.web.dao.exception.DAOException;
import main.java.com.likeit.web.domain.Question;
import main.java.com.likeit.web.service.QuestionService;
import main.java.com.likeit.web.service.exception.ServiceException;

import java.util.List;

import static main.java.com.likeit.web.service.exception.Exceptions.UNABLE_FIND_QUESTION;
import static main.java.com.likeit.web.service.exception.Exceptions.UNABLE_TO_CREATE_QUESTION;

public class QuestionServiceImpl implements QuestionService {

    @Override
    public void createQuestion(String topic, String content, String authorLogin) throws ServiceException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        QuestionDAO questionDAO = daoFactory.getQuestionDAO();
        try {
            questionDAO.saveQuestion(topic, content, authorLogin);
        } catch (DAOException ex) {
            throw new ServiceException(UNABLE_TO_CREATE_QUESTION.getMessage(), ex);
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
            throw new ServiceException(UNABLE_FIND_QUESTION.getMessage(), ex);
        }
        return questions;
    }
}
