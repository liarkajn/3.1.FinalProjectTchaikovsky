package com.likeit.web.controller.handler.command.impl.administration;

import com.likeit.web.controller.handler.command.Command;
import com.likeit.web.service.QuestionService;
import com.likeit.web.service.ServiceFactory;
import com.likeit.web.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class QuestionsCommand implements Command {

    private final static String QUESTIONS_FIELD_NAME = "questions";
    private final static String AUTHORIZATION_PAGE = "/administration/authorization";
    private final static String QUESTIONS_PAGE = "/WEB-INF/page/administration/questions.jsp";
    private final static String ERROR_PAGE = "?command=error&&message=";
    private final QuestionService questionService = ServiceFactory.getInstance().getQuestionService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!AdministratorChecker.isAdmin(request)) {
            response.sendRedirect(AUTHORIZATION_PAGE);
            return;
        }
        try {
            request.setAttribute(QUESTIONS_FIELD_NAME, questionService.findAllQuestions());
            request.getRequestDispatcher(QUESTIONS_PAGE).forward(request, response);
        }
        catch (ServiceException ex) {
            response.sendRedirect(ERROR_PAGE + ex.getMessage());
        }
    }
}
