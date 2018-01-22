package com.likeit.web.controller.handler.command.impl;

import com.likeit.web.controller.handler.command.Command;
import com.likeit.web.service.AnswerService;
import com.likeit.web.service.QuestionService;
import com.likeit.web.service.ServiceFactory;
import com.likeit.web.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class QuestionCommand implements Command {

    private final static String QUESTION_ID_FIELD_NAME = "id";
    private final static String QUESTION_FIELD_NAME = "question";
    private final static String ANSWERS_FIELD_NAME = "answers";
    private final static String QUESTION_PAGE = "/WEB-INF/page/question.jsp";
    private final static String ERROR_PAGE = "?command=error&&message=";
    private final QuestionService questionService = ServiceFactory.getInstance().getQuestionService();
    private final AnswerService answerService = ServiceFactory.getInstance().getAnswerService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter(QUESTION_ID_FIELD_NAME));
        try {
            request.setAttribute(QUESTION_FIELD_NAME, questionService.findQuestion(id));
            request.setAttribute(ANSWERS_FIELD_NAME, answerService.findAnswersByQuestion(id));
            request.getRequestDispatcher(QUESTION_PAGE).forward(request, response);
        } catch (ServiceException ex) {
            response.sendRedirect(ERROR_PAGE + ex.getMessage());
        }
    }

}
