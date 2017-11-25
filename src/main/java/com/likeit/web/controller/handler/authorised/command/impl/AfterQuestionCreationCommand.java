package main.java.com.likeit.web.controller.handler.authorised.command.impl;

import main.java.com.likeit.web.controller.handler.authorised.command.Command;
import main.java.com.likeit.web.service.QuestionService;
import main.java.com.likeit.web.service.ServiceFactory;
import main.java.com.likeit.web.service.exception.ServiceException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AfterQuestionCreationCommand implements Command {

    private final static String TOPIC_FIELD_NAME = "topic";
    private final static String CONTENT_FIELD_NAME = "content";
    private final static String LOGIN_SESSION_FIELD_NAME = "login";
    private final static String MAIN_PAGE = "?command=main";
    private final static String ERROR_PAGE = "?command=error&&message=";
    private final QuestionService questionService = ServiceFactory.getInstance().getQuestionService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String topic = request.getParameter(TOPIC_FIELD_NAME);
        String content = request.getParameter(CONTENT_FIELD_NAME);
        String authorLogin = request.getSession(true).getAttribute(LOGIN_SESSION_FIELD_NAME).toString();
        try {
            questionService.createQuestion(topic, content, authorLogin);
            response.sendRedirect(MAIN_PAGE);
        } catch (ServiceException ex) {
            response.sendRedirect(ERROR_PAGE + ex.getMessage());
        }
    }

}
