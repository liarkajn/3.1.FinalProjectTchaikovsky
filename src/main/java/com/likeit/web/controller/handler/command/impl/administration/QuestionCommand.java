package com.likeit.web.controller.handler.command.impl.administration;

import com.likeit.web.controller.handler.command.Command;
import com.likeit.web.domain.Question;
import com.likeit.web.service.QuestionService;
import com.likeit.web.service.ServiceFactory;
import com.likeit.web.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class QuestionCommand implements Command {

    private final static String AUTHORIZATION_PAGE = "/administration/authorization";
    private final static String QUESTION_PAGE = "/WEB-INF/page/administration/question.jsp";
    private final static String ERROR_PAGE = "/main?command=error&&message=";
    private final static String ID_FIELD_NAME = "id";
    private final static String QUESTION_FIELD_NAME = "question";
    private final QuestionService questionService = ServiceFactory.getInstance().getQuestionService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter(ID_FIELD_NAME));
        try {
            Question question = questionService.findQuestion(id);
            request.setAttribute(QUESTION_FIELD_NAME, question);
            request.getRequestDispatcher(QUESTION_PAGE).forward(request, response);
        } catch (ServiceException ex) {
            response.sendRedirect(ERROR_PAGE + ex.getMessage());
        }
    }
}
