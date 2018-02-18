package com.likeit.web.service.impl;

import com.likeit.web.dao.AnswerDAO;
import com.likeit.web.dao.DAOFactory;
import com.likeit.web.dao.exception.DAOException;
import com.likeit.web.domain.Answer;
import com.likeit.web.domain.Question;
import com.likeit.web.domain.User;
import com.likeit.web.service.AnswerService;
import com.likeit.web.service.QuestionService;
import com.likeit.web.service.ServiceFactory;
import com.likeit.web.service.UserService;
import com.likeit.web.service.exception.ServiceException;
import com.likeit.web.service.exception.answer.AnswerExistingException;
import com.likeit.web.service.impl.validation.AnswerValidation;
import com.likeit.web.service.impl.validation.UserValidation;

import java.util.List;

public class AnswerServiceImpl implements AnswerService {

    private AnswerDAO answerDAO = DAOFactory.getInstance().getAnswerDAO();
    private AnswerValidation answerValidation = new AnswerValidation();
    private UserValidation userValidation = new UserValidation();

    @Override
    public void saveAnswer(int authorId, int questionId, String content) throws ServiceException {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserService userService = serviceFactory.getUserService();
        QuestionService questionService = serviceFactory.getQuestionService();
        User author = userService.findUser(authorId);
        Question question = questionService.findQuestion(questionId);
        userValidation.isActive(author);
        answerValidation.validateAnswer(author, question, content);
        try {
            if (answerDAO.readAnswersByQuestionIdAndAuthorId(questionId, authorId).size() > 0) {
                throw new AnswerExistingException("User already answered to this question");
            }
            answerDAO.createAnswer(authorId, questionId, content);
        } catch (DAOException e) {
            throw new ServiceException("Problems with database. Unable to save answer", e);
        }
    }

    @Override
    public Answer findAnswer(int id) throws ServiceException {
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
        try {
            answers = answerDAO.readAnswersByQuestionId(questionId);
        } catch (DAOException e) {
            throw new ServiceException("Unable find answers to this question", e);
        }
        return answers;
    }

    @Override
    public Answer editAnswer(int authorId, int answerId, String content) throws ServiceException {
        answerValidation.validateAnswerEditing(authorId, answerId, content);
        Answer answer = new Answer();
        answer.setId(answerId);
        answer.setContent(content);
        try {
            answerDAO.updateAnswer(answer);
        } catch (DAOException e) {
            throw new ServiceException("Unable update answer with id : " + answerId, e);
        }
        return findAnswer(answerId);
    }

}
