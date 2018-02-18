package com.likeit.web.controller.handler.command.impl;

import com.likeit.web.controller.handler.command.Command;
import com.likeit.web.domain.Question;
import com.likeit.web.service.QuestionService;
import com.likeit.web.service.ServiceFactory;
import com.likeit.web.service.exception.ServiceException;
import com.likeit.web.service.exception.question.NoSuchQuestionException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToQuestionEditingCommand implements Command {

    private final static Logger logger = Logger.getLogger(GoToQuestionEditingCommand.class);

    private final static String USER_ID_SESSION_FIELD_NAME = "id";
    private final static String QUESTION_ID_FIELD_NAME = "id";
    private final static String QUESTION_FIELD_NAME = "question";
    private final static String QUESTIONS_PAGE = "main?command=questions";
    private final static String QUESTION_PAGE = "main?command=question&id=";
    private final static String QUESTION_EDITING_PAGE = "/WEB-INF/page/question_creation.jsp";
    private final QuestionService questionService = ServiceFactory.getInstance().getQuestionService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute(USER_ID_SESSION_FIELD_NAME) != null) {
            executeRequest(request, response);
        } else {
            response.sendRedirect(QUESTIONS_PAGE);
        }
    }

    private void executeRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = (int) request.getSession().getAttribute(USER_ID_SESSION_FIELD_NAME);
        int questionId = Integer.parseInt(request.getParameter(QUESTION_ID_FIELD_NAME));
        try {
            Question question = questionService.findQuestion(questionId);
            if (question.getAuthor().getId() == userId) {
                request.setAttribute(QUESTION_FIELD_NAME, question);
                request.getRequestDispatcher(QUESTION_EDITING_PAGE).forward(request, response);
            } else {
                response.sendRedirect(QUESTION_PAGE + questionId);
            }
        } catch (NoSuchQuestionException ex) {
            logger.error("Unable to find question with id: " + questionId, ex);
            response.sendRedirect(QUESTIONS_PAGE);
        } catch (ServiceException ex) {
            logger.log(Level.FATAL, "Internal error", ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

}
