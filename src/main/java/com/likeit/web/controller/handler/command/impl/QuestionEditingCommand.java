package com.likeit.web.controller.handler.command.impl;

import com.likeit.web.controller.handler.command.Command;
import com.likeit.web.domain.Question;
import com.likeit.web.service.AnswerService;
import com.likeit.web.service.QuestionService;
import com.likeit.web.service.ServiceFactory;
import com.likeit.web.service.exception.ServiceException;
import com.likeit.web.service.exception.question.*;
import com.likeit.web.service.exception.user.BannedException;
import com.likeit.web.service.exception.user.NoSuchUserException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class QuestionEditingCommand implements Command {

    private final static Logger logger = Logger.getLogger(QuestionEditingCommand.class);

    private final static String USER_ID_SESSION_FIELD_NAME = "id";
    private final static String QUESTION_ID_FIELD_NAME = "question_id";
    private final static String QUESTION_FIELD_NAME = "question";
    private final static String QUESTION_TOPIC_FIELD_NAME = "topic";
    private final static String QUESTION_CONTENT_FIELD_NAME = "content";
    private final static String ANSWERS_FIELD_NAME = "answers";
    private final static String ERROR_MESSAGE_FIELD_NAME = "error_message";
    private final static String QUESTIONS_PAGE = "?command=questions";
    private final static String QUESTION_PAGE = "?command=question&id=";
    private final static String QUESTION_PAGE_JSP = "/WEB-INF/page/question.jsp";
    private final static String AUTHORIZATION_PAGE = "/WEB-INF/page/signIn.jsp";
    private final static String QUESTION_EDITING_PAGE = "/WEB-INF/page/question_creation.jsp";
    private final QuestionService questionService = ServiceFactory.getInstance().getQuestionService();
    private final AnswerService answerService = ServiceFactory.getInstance().getAnswerService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int questionId = Integer.parseInt(request.getParameter(QUESTION_ID_FIELD_NAME));
        if (request.getSession().getAttribute(USER_ID_SESSION_FIELD_NAME) != null) {
            executeRequest(request, response);
        } else {
            response.sendRedirect(QUESTION_PAGE + questionId);
        }
    }

    private void executeRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = (int) request.getSession().getAttribute(USER_ID_SESSION_FIELD_NAME);
        int questionId = Integer.parseInt(request.getParameter(QUESTION_ID_FIELD_NAME));
        String questionTopic = request.getParameter(QUESTION_TOPIC_FIELD_NAME);
        String questionContent = request.getParameter(QUESTION_CONTENT_FIELD_NAME);
        Question question = new Question();
        question.setId(questionId);
        question.setTopic(questionTopic);
        question.setContent(questionContent);
        try {
            questionService.editQuestion(userId, questionId, questionTopic, questionContent);
            response.sendRedirect(QUESTION_PAGE + questionId);
        } catch (NoSuchUserException ex) {
            logger.error("Nonexistent user with id: " + userId + " trying edit question with id: " + questionId, ex);
            request.getSession().removeAttribute(USER_ID_SESSION_FIELD_NAME);
            request.setAttribute(ERROR_MESSAGE_FIELD_NAME, "You must to log in");
            request.getRequestDispatcher(AUTHORIZATION_PAGE).forward(request, response);
        } catch (BannedException ex) {
            logger.error("Banned user trying edit question", ex);
            request.getSession().removeAttribute(USER_ID_SESSION_FIELD_NAME);
            request.setAttribute(ERROR_MESSAGE_FIELD_NAME, "You have been banned");
            request.getRequestDispatcher(AUTHORIZATION_PAGE).forward(request, response);
        } catch (NoSuchQuestionException ex) {
            logger.error("Unable to find question with id: " + questionId, ex);
            response.sendRedirect(QUESTIONS_PAGE);
        } catch (AuthorException ex) {
            logger.error("User with id + " + userId + " trying edit not his question with id" + questionId, ex);
            response.sendRedirect(QUESTION_PAGE + questionId);
        } catch (AnswerExistsException ex ) {
            logger.error("User with id + " + userId + " trying edit question with id" + questionId + " with answers ", ex);
            returnToQuestionPage(request, response, questionId, "You can't edit question with answers");
        } catch (TopicValidationException ex) {
            logger.error("Question topic too short ", ex);
            returnToEditingPage(request, response, questionId, "Topic must contain at least 50 characters");
        } catch (ContentValidationException ex) {
            logger.error("Question content too short ", ex);
            returnToEditingPage(request, response, questionId, "Content must contain at least 100 characters");
        } catch (ServiceException ex) {
            logger.log(Level.FATAL, "Internal error", ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void returnToQuestionPage(HttpServletRequest request, HttpServletResponse response, int questionId, String errorMessage) throws ServletException, IOException {
        try {
            request.setAttribute(QUESTION_FIELD_NAME, questionService.findQuestion(questionId));
            request.setAttribute(ANSWERS_FIELD_NAME, answerService.findAnswersByQuestion(questionId));
            request.setAttribute(ERROR_MESSAGE_FIELD_NAME, errorMessage);
            request.getRequestDispatcher(QUESTION_PAGE_JSP).forward(request, response);
        } catch (NoSuchQuestionException ex) {
            logger.error("Unable to find question with id: " + questionId, ex);
            response.sendRedirect(QUESTIONS_PAGE);
        } catch (ServiceException ex) {
            logger.log(Level.FATAL, "Internal error", ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void returnToEditingPage(HttpServletRequest request, HttpServletResponse response, int questionId, String errorMessage) throws ServletException, IOException {
        try {
            request.setAttribute(QUESTION_FIELD_NAME, questionService.findQuestion(questionId));
            request.setAttribute(ERROR_MESSAGE_FIELD_NAME, errorMessage);
            request.getRequestDispatcher(QUESTION_EDITING_PAGE).forward(request, response);
        } catch (NoSuchQuestionException ex) {
            logger.error("Unable to find question with id: " + questionId, ex);
            response.sendRedirect(QUESTIONS_PAGE);
        } catch (ServiceException ex) {
            logger.log(Level.FATAL, "Internal error", ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

}
