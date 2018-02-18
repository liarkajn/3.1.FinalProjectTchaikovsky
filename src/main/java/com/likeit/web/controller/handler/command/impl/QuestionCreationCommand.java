package com.likeit.web.controller.handler.command.impl;

import com.likeit.web.controller.handler.command.Command;
import com.likeit.web.service.QuestionService;
import com.likeit.web.service.ServiceFactory;
import com.likeit.web.service.exception.ServiceException;
import com.likeit.web.service.exception.question.ContentValidationException;
import com.likeit.web.service.exception.question.TopicValidationException;
import com.likeit.web.service.exception.user.BannedException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class QuestionCreationCommand implements Command {

    private final static Logger logger = Logger.getLogger(QuestionCreationCommand.class);

    private final static String USER_ID_SESSION_FIELD_NAME = "id";
    private final static String TOPIC_FIELD_NAME = "topic";
    private final static String CONTENT_FIELD_NAME = "content";
    private final static String ERROR_MESSAGE_FIELD_NAME = "error_message";
    private final static String AUTHORIZATION_PAGE = "/WEB-INF/page/signIn.jsp";
    private final static String QUESTION_CREATION_PAGE = "/WEB-INF/page/question_creation.jsp";
    private final static String QUESTIONS_PAGE = "?command=questions";
    private final QuestionService questionService = ServiceFactory.getInstance().getQuestionService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession(true).getAttribute(USER_ID_SESSION_FIELD_NAME) != null) {
            int authorId = Integer.parseInt(request.getSession(true).getAttribute(USER_ID_SESSION_FIELD_NAME).toString());
            String topic = request.getParameter(TOPIC_FIELD_NAME);
            String content = request.getParameter(CONTENT_FIELD_NAME);
            try {
                questionService.createQuestion(topic, content, authorId);
                response.sendRedirect(QUESTIONS_PAGE);
            } catch (BannedException ex) {
                logger.error("Banned user trying create question", ex);
                request.getSession().removeAttribute(USER_ID_SESSION_FIELD_NAME);
                request.setAttribute(ERROR_MESSAGE_FIELD_NAME, "You have been banned");
                request.getRequestDispatcher(AUTHORIZATION_PAGE).forward(request, response);
            } catch (TopicValidationException ex) {
                logger.error("Topic is too short", ex);
                handleException(request, response, "Topic must be at least 50 characters");
            } catch (ContentValidationException ex) {
                logger.error("Content is too short", ex);
                handleException(request, response, "Content must be at least 100 characters");
            } catch (ServiceException ex) {
                logger.log(Level.FATAL, "Internal error", ex);
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else {
            response.sendRedirect(QUESTIONS_PAGE);
        }
    }

    private void handleException(HttpServletRequest request, HttpServletResponse response, String errorMessage) throws ServletException, IOException {
        request.setAttribute(ERROR_MESSAGE_FIELD_NAME, errorMessage);
        request.getRequestDispatcher(QUESTION_CREATION_PAGE).forward(request, response);
    }

}
