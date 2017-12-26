package main.java.com.likeit.web.controller.handler.command.impl;

import main.java.com.likeit.web.controller.handler.command.Command;
import main.java.com.likeit.web.service.QuestionService;
import main.java.com.likeit.web.service.ServiceFactory;
import main.java.com.likeit.web.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AfterQuestionCreationCommand implements Command {

    private final static String USER_ID_SESSION_FIELD_NAME = "id";
    private final static String QUESTION_ID_FIELD_NAME = "question_id";
    private final static String TOPIC_FIELD_NAME = "topic";
    private final static String CONTENT_FIELD_NAME = "content";
    private final static String QUESTIONS_PAGE = "?command=questions";
    private final static String QUESTION_PAGE = "?command=question&&id=";
    private final static String ERROR_PAGE = "?command=error&&message=";
    private final QuestionService questionService = ServiceFactory.getInstance().getQuestionService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int authorId = Integer.parseInt(request.getSession(true).getAttribute(USER_ID_SESSION_FIELD_NAME).toString());
        String topic = request.getParameter(TOPIC_FIELD_NAME);
        String content = request.getParameter(CONTENT_FIELD_NAME);
        try {
            if (request.getParameter(QUESTION_ID_FIELD_NAME) == null) {
                questionService.createQuestion(topic, content, authorId);
                response.sendRedirect(QUESTIONS_PAGE);
            } else {
                int questionId = Integer.parseInt(request.getParameter(QUESTION_ID_FIELD_NAME));
                questionService.editQuestion(authorId, questionId, topic, content);
                response.sendRedirect(QUESTION_PAGE + questionId);
            }
        } catch (ServiceException ex) {
            response.sendRedirect(ERROR_PAGE + ex.getMessage());
        }
    }

}
