package main.java.com.likeit.web.controller.handler.authorised.command.impl;

import main.java.com.likeit.web.controller.handler.authorised.command.Command;
import main.java.com.likeit.web.service.QuestionService;
import main.java.com.likeit.web.service.ServiceFactory;
import main.java.com.likeit.web.service.exception.ServiceException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MainCommand implements Command {

    private final static String MAIN_PAGE = "/WEB-INF/page/main.jsp";
    private final static String ERROR_PAGE = "?command=error&&message=";
    private final static String QUESTIONS_FIELD_NAME = "questions";
    private final QuestionService questionService = ServiceFactory.getInstance().getQuestionService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
                request.setAttribute(QUESTIONS_FIELD_NAME, questionService.getAll());
            request.getRequestDispatcher(MAIN_PAGE).forward(request, response);
        } catch (ServiceException ex) {
            response.sendRedirect(ERROR_PAGE + ex.getMessage());
        }
    }

}
