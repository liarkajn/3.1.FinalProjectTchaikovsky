package main.java.com.likeit.web.service.impl;

import main.java.com.likeit.web.dao.DAOFactory;
import main.java.com.likeit.web.dao.QuestionDAO;
import main.java.com.likeit.web.dao.exception.DAOException;
import main.java.com.likeit.web.domain.Question;
import main.java.com.likeit.web.service.QuestionService;
import main.java.com.likeit.web.service.exception.ServiceException;
import main.java.com.likeit.web.service.impl.validation.QuestionValidation;

import java.util.List;

public class QuestionServiceImpl implements QuestionService {

    private final QuestionValidation questionValidation = new QuestionValidation();

    @Override
    public void createQuestion(String topic, String content, int authorId) throws ServiceException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        QuestionDAO questionDAO = daoFactory.getQuestionDAO();
        try {
            questionDAO.createQuestion(topic, content, authorId);
        } catch (DAOException ex) {
            throw new ServiceException("Unable to create question. Please try later.", ex);
        }
    }

    @Override
    public Question findQuestion(int id) throws ServiceException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        QuestionDAO questionDAO = daoFactory.getQuestionDAO();
        Question result;
        try {
            result = questionDAO.readQuestion(id);
        } catch (DAOException ex) {
            throw new ServiceException("Unable find question with id " + id, ex);
        }
        return result;
    }

    @Override
    public List<Question> findAllQuestions() throws ServiceException {
        List<Question> questions;
        DAOFactory daoFactory = DAOFactory.getInstance();
        QuestionDAO questionDAO = daoFactory.getQuestionDAO();
        try {
            questions = questionDAO.readQuestions();
        } catch (DAOException ex) {
            throw new ServiceException("Unable find question list. Please, try later", ex);
        }
        return questions;
    }

    @Override
    public List<Question> findQuestionsByTopic(String searchString) throws ServiceException {
        List<Question> questions;
        DAOFactory daoFactory = DAOFactory.getInstance();
        QuestionDAO questionDAO = daoFactory.getQuestionDAO();
        try {
            questions = questionDAO.readQuestionsBySearchString(searchString);
        } catch (DAOException ex) {
            throw new ServiceException("Unable search questions. Please, try later", ex);
        }
        return questions;
    }

    @Override
    public void editQuestion(int authorId, int questionId, String topic, String content) throws ServiceException {
        questionValidation.validateQuestionEditing(authorId,questionId, topic, content);
        QuestionDAO questionDAO = DAOFactory.getInstance().getQuestionDAO();
        Question question = new Question();
        question.setId(questionId);
        question.setTopic(topic);
        question.setContent(content);
        try {
            questionDAO.updateQuestion(question);
        } catch (DAOException e) {
            throw new ServiceException("Unable update question by id", e);
        }
    }

}
