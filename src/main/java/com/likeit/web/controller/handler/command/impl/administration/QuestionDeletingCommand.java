package com.likeit.web.controller.handler.command.impl.administration;

import com.likeit.web.controller.handler.command.Command;
import com.likeit.web.service.QuestionService;
import com.likeit.web.service.ServiceFactory;
import com.likeit.web.service.exception.ServiceException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class QuestionDeletingCommand implements Command {

    private final static Logger logger = Logger.getLogger(QuestionDeletingCommand.class);

    private final static String USER_ID_SESSION_FIELD_NAME = "id";
    private final static String ID_FIELD_NAME = "id";
    private final static String QUESTIONS_PAGE = "administration?command=questions";
    private final static String AUTHORIZATION_PAGE = "?command=go_to_authorization";
    private final QuestionService questionService = ServiceFactory.getInstance().getQuestionService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute(USER_ID_SESSION_FIELD_NAME) != null) {
            executeRequest(request, response);
        } else {
            response.sendRedirect(AUTHORIZATION_PAGE);
        }
    }

    private void executeRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int adminId = (int) request.getSession().getAttribute(USER_ID_SESSION_FIELD_NAME);
        int questionId = Integer.parseInt(request.getParameter(ID_FIELD_NAME));
        try {
            questionService.adminDeleteQuestion(questionId, adminId);
            response.sendRedirect(QUESTIONS_PAGE);
        } catch (ServiceException ex) {
            logger.log(Level.FATAL, "Internal error", ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

}
