package main.java.com.likeit.web.service.impl;

import main.java.com.likeit.web.dao.AnswerDAO;
import main.java.com.likeit.web.dao.DAOFactory;
import main.java.com.likeit.web.dao.exception.DAOException;
import main.java.com.likeit.web.domain.Answer;
import main.java.com.likeit.web.service.AnswerService;
import main.java.com.likeit.web.service.exception.ServiceException;
import main.java.com.likeit.web.service.impl.validation.AnswerValidation;

import java.util.List;

public class AnswerServiceImpl implements AnswerService {

    private AnswerValidation answerValidation = new AnswerValidation();

    @Override
    public void saveAnswer(int authorId, int questionId, String content) throws ServiceException {
        answerValidation.validateAnswerCreation(authorId, questionId, content);
        AnswerDAO answerDAO = DAOFactory.getInstance().getAnswerDAO();
        try {
            answerDAO.createAnswer(authorId, questionId, content);
        } catch (DAOException e) {
            throw new ServiceException("Unable to save answer", e);
        }
    }

    @Override
    public Answer findAnswer(int id) throws ServiceException {
        AnswerDAO answerDAO = DAOFactory.getInstance().getAnswerDAO();
        Answer answer;
        try {
            answer = answerDAO.readAnswerById(id);
        } catch (DAOException e) {
            throw new ServiceException("Unable find answer with id : " + id, e);
        }
        return answer;
    }

    @Override
    public List<Answer> findAnswersByQuestion(int questionId) throws ServiceException {
        List<Answer> answers;
        AnswerDAO answerDAO = DAOFactory.getInstance().getAnswerDAO();
        try {
            answers = answerDAO.readAnswersByQuestionId(questionId);
        } catch (DAOException e) {
            throw new ServiceException("Unable find answers to this question", e);
        }
        return answers;
    }

    @Override
    public List<Answer> findAnswersByAuthor(int authorId) throws ServiceException {
        List<Answer> answers;
        AnswerDAO answerDAO = DAOFactory.getInstance().getAnswerDAO();
        try {
            answers = answerDAO.readAnswersByAuthorId(authorId);
        } catch (DAOException e) {
            throw new ServiceException("Unable find answers to this user", e);
        }
        return answers;
    }

    @Override
    public Answer editAnswer(int authorId, int answerId, String content) throws ServiceException {
        answerValidation.validateAnswerEditing(authorId, answerId, content);
        AnswerDAO answerDAO = DAOFactory.getInstance().getAnswerDAO();
        Answer answer = new Answer();
        answer.setId(answerId);
        answer.setContent(content);
        try {
            answerDAO.updateAnswer(answer);
        } catch (DAOException e) {
            throw new ServiceException("Unable update answer with id : " + answerId, e);
        }
        return answer;
    }

}
