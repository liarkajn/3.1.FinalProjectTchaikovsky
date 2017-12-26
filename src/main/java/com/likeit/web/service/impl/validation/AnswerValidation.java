package main.java.com.likeit.web.service.impl.validation;

import main.java.com.likeit.web.dao.AnswerDAO;
import main.java.com.likeit.web.dao.DAOFactory;
import main.java.com.likeit.web.dao.QuestionDAO;
import main.java.com.likeit.web.dao.UserDAO;
import main.java.com.likeit.web.dao.exception.DAOException;
import main.java.com.likeit.web.domain.Answer;
import main.java.com.likeit.web.domain.Question;
import main.java.com.likeit.web.domain.User;
import main.java.com.likeit.web.service.exception.ServiceException;

public class AnswerValidation {

    private final UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
    private final QuestionDAO questionDAO = DAOFactory.getInstance().getQuestionDAO();
    private final AnswerDAO answerDAO = DAOFactory.getInstance().getAnswerDAO();

    public boolean validateAnswerCreation(int authorId, int questionId, String content) throws ServiceException {
        User author;
        Question question;
        try {
            author = userDAO.readUser(authorId);
        } catch (DAOException e) {
            throw new ServiceException("Unable find user with id : " + authorId);
        }
        try {
            question = questionDAO.readQuestion(questionId);
        } catch (DAOException e) {
            throw new ServiceException("Unable find question with id : " + questionId);
        }
        if (author.getId() == question.getAuthor().getId()) {
            throw new ServiceException("Unable to answer for yours question");
        }
        validateContent(content);
        return true;
    }

    public boolean validateAnswerEditing(int authorId, int answerId, String content) throws ServiceException {
        User author;
        Answer answer;
        try {
            author = userDAO.readUser(authorId);
        } catch (DAOException e) {
            throw new ServiceException("Unable find user with id : " + authorId);
        }
        try {
            answer = answerDAO.readAnswerById(answerId);
        } catch (DAOException e) {
            throw new ServiceException("Unable find answer with id : " + answerId);
        }
        if (author.getId() != answer.getAuthor().getId()) {
            throw new ServiceException("Unable edit not your answer");
        }
        validateContent(content);
        return true;
    }

    private boolean validateContent(String content) throws ServiceException {
        content = content.trim();
        if (content.isEmpty()) {
            throw new ServiceException("Answer doesn't have a content");
        }
        return true;
    }

}
