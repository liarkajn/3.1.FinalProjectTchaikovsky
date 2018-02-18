package com.likeit.web.service.impl;

import com.likeit.web.dao.DAOFactory;
import com.likeit.web.dao.QuestionDAO;
import com.likeit.web.dao.exception.DAOException;
import com.likeit.web.domain.Question;
import com.likeit.web.domain.User;
import com.likeit.web.service.AnswerService;
import com.likeit.web.service.QuestionService;
import com.likeit.web.service.ServiceFactory;
import com.likeit.web.service.UserService;
import com.likeit.web.service.exception.ServiceException;
import com.likeit.web.service.exception.question.AnswerExistsException;
import com.likeit.web.service.exception.user.UserNotAdminException;
import com.likeit.web.service.impl.validation.QuestionValidation;
import com.likeit.web.service.impl.validation.UserValidation;

import java.util.List;

public class QuestionServiceImpl implements QuestionService {

    private DAOFactory daoFactory = DAOFactory.getInstance();
    private QuestionDAO questionDAO = daoFactory.getQuestionDAO();
    private UserValidation userValidation = new UserValidation();
    private QuestionValidation questionValidation = new QuestionValidation();

    @Override
    public void createQuestion(String topic, String content, int authorId) throws ServiceException {
        UserService userService = ServiceFactory.getInstance().getUserService();
        User user = userService.findUser(authorId);
        userValidation.validate(user);
        userValidation.isActive(user);
        questionValidation.validateQuestionCreation(topic, content);
        try {
            questionDAO.createQuestion(topic, content, authorId);
        } catch (DAOException ex) {
            throw new ServiceException("Problems with database. Unable to create question with topic = " +
                    topic + " content = " + content + "author id = " + authorId, ex);
        }
    }

    @Override
    public Question findQuestion(int id) throws ServiceException {
        Question question;
        try {
            question = questionDAO.readQuestion(id);
        } catch (DAOException ex) {
            throw new ServiceException("Problems with database. Unable find question with id " + id, ex);
        }
        questionValidation.validateQuestion(question);
        return question;
    }

    @Override
    public List<Question> findAllQuestions(int limit, int page) throws ServiceException {
        List<Question> questions;
        try {
            questions = questionDAO.readQuestions(limit, limit * (page - 1));
        } catch (DAOException ex) {
            throw new ServiceException("Problems with database. Unable search questions with limit = " +
                    + limit + " page = " + page, ex);
        }
        return questions;
    }

    @Override
    public List<Question> findQuestionsByTopic(int limit, int page, String searchString) throws ServiceException {
        List<Question> questions;
        try {
            questions = questionDAO.readQuestionsBySearchString(limit, limit * (page - 1), searchString);
        } catch (DAOException ex) {
            throw new ServiceException("Problems with database. Unable search questions with limit = " +
                    + limit + " page = " + page + " search string = " + searchString, ex);
        }
        return questions;
    }

    @Override
    public void editQuestion(int authorId, int questionId, String topic, String content) throws ServiceException {
        UserService userService = ServiceFactory.getInstance().getUserService();
        AnswerService answerService = ServiceFactory.getInstance().getAnswerService();
        User user = userService.findUser(authorId);
        userValidation.validate(user);
        userValidation.isActive(user);
        if (answerService.findAnswersByQuestion(questionId).size() > 0) {
            throw new AnswerExistsException("Unable edit question with answers");
        }
        Question question = findQuestion(questionId);
        questionValidation.validateAuthor(question, user);
        editQuestion(questionId, topic, content);
    }

    @Override
    public void adminEditQuestion(int adminId, int questionId, String topic, String content) throws ServiceException {
        UserService userService = ServiceFactory.getInstance().getUserService();
        User admin = userService.findUser(adminId);
        if (!userValidation.isAdmin(admin)) {
            throw new UserNotAdminException("User with id=" + adminId + " trying edit question from administration panel");
        }
        editQuestion(questionId, topic, content);
    }

    private void editQuestion(int questionId, String topic, String content) throws ServiceException {
        Question question;
        try {
            question = findQuestion(questionId);
            questionValidation.validateQuestionCreation(topic, content);
            question.setTopic(topic);
            question.setContent(content);
            questionDAO.updateQuestion(question);
        } catch (DAOException e) {
            throw new ServiceException("Problems with database. Unable update question with id: " + questionId, e);
        }
    }

    @Override
    public int getQuestionsCount() throws ServiceException {
        int count;
        try {
            count = questionDAO.readQuestionsCount();
        } catch (DAOException e) {
            throw new ServiceException("Problems with database. Unable get questions count", e);
        }
        return count;
    }

    @Override
    public int getQuestionsCount(String searchString) throws ServiceException {
        int count;
        try {
            count = questionDAO.readQuestionsCount(searchString);
        } catch (DAOException e) {
            throw new ServiceException("Problems with database. Unable get questions count by search string : " + searchString, e);
        }
        return count;
    }

    @Override
    public void deleteQuestion(int questionId, int authorId) throws ServiceException {
        UserService userService = ServiceFactory.getInstance().getUserService();
        try {
            User user = userService.findUser(authorId);
            userValidation.isActive(user);
            Question question = findQuestion(questionId);
            questionValidation.validateAuthor(question, user);
            questionDAO.deleteQuestion(questionId);
        } catch (DAOException e) {
            throw new ServiceException("Problems with database. Unable delete question with id : " + questionId, e);
        }
    }

    @Override
    public void adminDeleteQuestion(int questionId, int adminId) throws ServiceException {
        UserService userService = ServiceFactory.getInstance().getUserService();
        try {
            User admin = userService.findUser(adminId);
            if (!userValidation.isAdmin(admin)) {
                throw new UserNotAdminException("User with id=" + adminId + " trying delete question from administration panel");
            }
            findQuestion(questionId);
            questionDAO.deleteQuestion(questionId);
        } catch (DAOException e) {
            throw new ServiceException("Problems with database. Unable delete question with id : " + questionId, e);
        }
    }

}
