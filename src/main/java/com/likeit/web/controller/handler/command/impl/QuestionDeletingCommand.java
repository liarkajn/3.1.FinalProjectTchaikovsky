package com.likeit.web.controller.handler.command.impl;

import com.likeit.web.controller.handler.command.Command;
import com.likeit.web.service.QuestionService;
import com.likeit.web.service.ServiceFactory;
import com.likeit.web.service.exception.ServiceException;
import com.likeit.web.service.exception.question.AuthorException;
import com.likeit.web.service.exception.question.NoSuchQuestionException;
import com.likeit.web.service.exception.user.BannedException;
import com.likeit.web.service.exception.user.NoSuchUserException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class QuestionDeletingCommand implements Command {

    private final static Logger logger = Logger.getLogger(QuestionDeletingCommand.class);

    private final static String USER_ID_SESSION_FIELD_NAME = "id";
    private final static String QUESTION_ID_FIELD_NAME = "id";
    private final static String ERROR_MESSAGE_FIELD_NAME = "error_message";
    private final static String QUESTIONS_PAGE = "?command=questions";
    private final static String QUESTION_PAGE = "?command=question&id=";
    private final static String AUTHORIZATION_PAGE = "/WEB-INF/page/signIn.jsp";
    private final QuestionService questionService = ServiceFactory.getInstance().getQuestionService();

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
        int questionId = Integer.parseInt(request.getParameter(QUESTION_ID_FIELD_NAME));
        int authorId = (int) request.getSession().getAttribute(USER_ID_SESSION_FIELD_NAME);
        try {
            questionService.deleteQuestion(questionId, authorId);
            response.sendRedirect(QUESTIONS_PAGE);
        } catch (NoSuchUserException ex) {
            logger.error("Nonexistent user with id: " + authorId + " trying edit question with id: " + questionId, ex);
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
            logger.error("User with id + " + authorId + " trying edit not his question with id" + questionId, ex);
            response.sendRedirect(QUESTION_PAGE + questionId);
        } catch (ServiceException ex) {
            logger.log(Level.FATAL, "Internal error", ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

}
