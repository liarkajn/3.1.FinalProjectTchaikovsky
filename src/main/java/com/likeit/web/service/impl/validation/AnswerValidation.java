package com.likeit.web.service.impl.validation;

import com.likeit.web.dao.AnswerDAO;
import com.likeit.web.dao.DAOFactory;
import com.likeit.web.dao.UserDAO;
import com.likeit.web.dao.exception.DAOException;
import com.likeit.web.domain.Answer;
import com.likeit.web.domain.Question;
import com.likeit.web.domain.User;
import com.likeit.web.service.exception.ServiceException;
import com.likeit.web.service.exception.answer.AuthorException;
import com.likeit.web.service.exception.answer.ContentValidationException;

public class AnswerValidation {

    private final static int REQUIRED_ANSWER_LENGTH = 50;
    private final UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
    private final AnswerDAO answerDAO = DAOFactory.getInstance().getAnswerDAO();

    public boolean validateAnswer(User author, Question question, String content) throws ServiceException {
        if (author.getId() == question.getAuthor().getId()) {
            throw new AuthorException("Unable to answer for yours question");
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
        if (content.isEmpty() || content.length() < REQUIRED_ANSWER_LENGTH) {
            throw new ContentValidationException("Answer is too short");
        }
        return true;
    }

}
