package com.likeit.web.controller.handler.command.impl;

import com.likeit.web.controller.handler.command.Command;
import com.likeit.web.service.AnswerService;
import com.likeit.web.service.QuestionService;
import com.likeit.web.service.ServiceFactory;
import com.likeit.web.service.exception.ServiceException;
import com.likeit.web.service.exception.answer.AnswerExistingException;
import com.likeit.web.service.exception.answer.AuthorException;
import com.likeit.web.service.exception.answer.ContentValidationException;
import com.likeit.web.service.exception.user.BannedException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AnswerCreationCommand implements Command {

    private final static Logger logger = Logger.getLogger(AnswerCreationCommand.class);

    private final static String USER_ID_SESSION_FIELD_NAME = "id";
    private final static String QUESTION_ID_FIELD_NAME = "question_id";
    private final static String CONTENT_FIELD_NAME = "content";
    private final static String QUESTION_FIELD_NAME = "question";
    private final static String ANSWERS_FIELD_NAME = "answers";
    private final static String ERROR_MESSAGE_FIELD_NAME = "error_message";
    private final static String AUTHORIZATION_PAGE = "/WEB-INF/page/signIn.jsp";
    private final static String QUESTION_PAGE_JSP = "/WEB-INF/page/question.jsp";
    private final static String QUESTION_PAGE = "?command=question&&id=";
    private final AnswerService answerService = ServiceFactory.getInstance().getAnswerService();
    private final QuestionService questionService = ServiceFactory.getInstance().getQuestionService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int authorId = (int) request.getSession().getAttribute(USER_ID_SESSION_FIELD_NAME);
        int questionId = Integer.parseInt(request.getParameter(QUESTION_ID_FIELD_NAME));
        String content = request.getParameter(CONTENT_FIELD_NAME);
        try {
            answerService.saveAnswer(authorId, questionId, content);
            response.sendRedirect(QUESTION_PAGE + questionId);
        } catch (BannedException ex) {
            logger.error("User banned", ex);
            request.getSession().removeAttribute(USER_ID_SESSION_FIELD_NAME);
            request.setAttribute(ERROR_MESSAGE_FIELD_NAME, "You have been banned!");
            request.getRequestDispatcher(AUTHORIZATION_PAGE).forward(request, response);
        } catch (AuthorException ex) {
            logger.error("User with id: " + authorId + "trying answer to his question", ex);
            handleException(request, response, questionId, "You can't answer for your question");
        } catch (AnswerExistingException ex) {
            logger.error("User with id: " + authorId + "trying answer second time" , ex);
            handleException(request, response, questionId, "You can't answer for same question several times");
        } catch (ContentValidationException ex) {
            logger.error("Content too short", ex);
            handleException(request, response, questionId, "Content must contain at least 50 characters");
        } catch (ServiceException ex) {
            logger.log(Level.FATAL, "Internal error", ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void handleException(HttpServletRequest request, HttpServletResponse response, int questionId, String errorMessage) throws ServletException, IOException {
        request.setAttribute(ERROR_MESSAGE_FIELD_NAME, errorMessage);
        try {
            request.setAttribute(QUESTION_FIELD_NAME, questionService.findQuestion(questionId));
            request.setAttribute(ANSWERS_FIELD_NAME, answerService.findAnswersByQuestion(questionId));
            request.getRequestDispatcher(QUESTION_PAGE_JSP).forward(request, response);
        } catch (ServiceException ex) {
            logger.log(Level.FATAL, "Internal error", ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

}
