package com.likeit.web.service.impl.validation;

import com.likeit.web.dao.DAOFactory;
import com.likeit.web.dao.QuestionDAO;
import com.likeit.web.dao.UserDAO;
import com.likeit.web.dao.exception.DAOException;
import com.likeit.web.domain.Question;
import com.likeit.web.domain.User;
import com.likeit.web.service.exception.ServiceException;

public class QuestionValidation {

    private final UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
    private final QuestionDAO questionDAO = DAOFactory.getInstance().getQuestionDAO();

    public boolean validateQuestionEditing(int authorId, int questionId, String topic, String content) throws ServiceException {
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
        if (author.getId() != question.getAuthor().getId()) {
            throw new ServiceException("Unable edit not your question");
        }
        validateTopic(topic);
        validateContent(content);
        return true;
    }

    private boolean validateTopic(String topic) throws ServiceException {
        topic = topic.trim();
        if (topic.isEmpty()) {
            throw new ServiceException("Question doesn't have a topic");
        }
        return true;
    }

    private boolean validateContent(String content) throws ServiceException {
        content = content.trim();
        if (content.isEmpty()) {
            throw new ServiceException("Question doesn't have a content");
        }
        return true;
    }

}
