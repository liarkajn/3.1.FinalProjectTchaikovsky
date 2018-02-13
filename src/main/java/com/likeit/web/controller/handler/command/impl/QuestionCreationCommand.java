package com.likeit.web.controller.handler.command.impl;

import com.likeit.web.controller.handler.command.Command;
import com.likeit.web.service.QuestionService;
import com.likeit.web.service.ServiceFactory;
import com.likeit.web.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class QuestionCreationCommand implements Command {

    private final static String USER_ID_SESSION_FIELD_NAME = "id";
    private final static String TOPIC_FIELD_NAME = "topic";
    private final static String CONTENT_FIELD_NAME = "content";
    private final static String QUESTIONS_PAGE = "?command=questions";
    private final static String ERROR_PAGE = "?command=error&&message=";
    private final QuestionService questionService = ServiceFactory.getInstance().getQuestionService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int authorId = Integer.parseInt(request.getSession(true).getAttribute(USER_ID_SESSION_FIELD_NAME).toString());
        String topic = request.getParameter(TOPIC_FIELD_NAME);
        String content = request.getParameter(CONTENT_FIELD_NAME);
        try {
            questionService.createQuestion(topic, content, authorId);
            response.sendRedirect(QUESTIONS_PAGE);
        } catch (ServiceException ex) {
            response.sendRedirect(ERROR_PAGE + ex.getMessage());
        }
    }

}
