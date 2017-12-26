package main.java.com.likeit.web.service.impl.validation;

import main.java.com.likeit.web.dao.AnswerDAO;
import main.java.com.likeit.web.dao.DAOFactory;
import main.java.com.likeit.web.dao.UserDAO;
import main.java.com.likeit.web.dao.exception.DAOException;
import main.java.com.likeit.web.domain.Answer;
import main.java.com.likeit.web.domain.User;
import main.java.com.likeit.web.service.exception.ServiceException;

public class VotingValidation {

    private UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
    private AnswerDAO answerDAO = DAOFactory.getInstance().getAnswerDAO();

    public boolean validateCreation(int authorId, int answerId, int mark) throws ServiceException {
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
            throw new ServiceException("Unable find answer with id : " + authorId);
        }
        if (answer.getAuthor().getId() == author.getId()) {
            throw new ServiceException("Unable vote to your answer");
        }
        if (mark > 5 && mark < 0) {
            throw new ServiceException("Incorrect mark value");
        }
        return true;
    }


}
