package com.likeit.web.controller.handler.command.impl;

import com.likeit.web.controller.handler.command.Command;
import com.likeit.web.domain.Question;
import com.likeit.web.service.QuestionService;
import com.likeit.web.service.ServiceFactory;
import com.likeit.web.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class QuestionCreationCommand implements Command {

    private final static String QUESTION_ID_PARAMETER_NAME = "question_id";
    private final static String QUESTION_ID_ATTRIBUTE_NAME = "questionId";
    private final static String QUESTION_TOPIC_ATTRIBUTE_NAME = "topic";
    private final static String QUESTION_CONTENT_ATTRIBUTE_NAME = "content";
    private final static String QUESTION_CREATION_PAGE = "/WEB-INF/page/question_creation.jsp";
    private final static String ERROR_PAGE = "?command=error&&message=";
    private final QuestionService questionService = ServiceFactory.getInstance().getQuestionService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter(QUESTION_ID_PARAMETER_NAME) != null) {
            int questionId = Integer.parseInt(request.getParameter(QUESTION_ID_PARAMETER_NAME));
            try {
                Question question = questionService.findQuestion(questionId);
                request.setAttribute(QUESTION_ID_ATTRIBUTE_NAME, questionId);
                request.setAttribute(QUESTION_TOPIC_ATTRIBUTE_NAME, question.getTopic());
                request.setAttribute(QUESTION_CONTENT_ATTRIBUTE_NAME, question.getContent());
            } catch (ServiceException e) {
                response.sendRedirect(ERROR_PAGE + e.getMessage());
            }
        }
        request.getRequestDispatcher(QUESTION_CREATION_PAGE).forward(request, response);
    }

}
