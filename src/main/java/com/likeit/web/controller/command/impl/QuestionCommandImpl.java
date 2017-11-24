package main.java.com.likeit.web.controller.command.impl;

import main.java.com.likeit.web.controller.command.Command;
import main.java.com.likeit.web.service.QuestionService;
import main.java.com.likeit.web.service.ServiceFactory;
import main.java.com.likeit.web.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class QuestionCommandImpl implements Command {

    private final static String ACTION_FIELD_NAME = "action";
    private final static String QUESTIONS_FIELD_NAME = "questions";
    private final static String TOPIC_FIELD_NAME = "topic";
    private final static String CONTENT_FIELD_NAME = "content";
    private final static String LOGIN_SESSION_FIELD_NAME = "login";
    private final static String EXCEPTION_FIELD_NAME = "exception";
    private final static String ASK_ACTION = "ask";
    private final static String CREATE_ACTION = "create";
    private final static String MAIN_PAGE = "/WEB-INF/page/main.jsp";
    private final static String QUESTION_CREATOR_PAGE = "/WEB-INF/page/createQuestion.jsp";
    private final static String ERROR_PAGE = "/WEB-INF/page/error.jsp";
    private QuestionService questionService = ServiceFactory.getInstance().getQuestionService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter(ACTION_FIELD_NAME);
        switch (action) {
            case ASK_ACTION:
                request.getRequestDispatcher(QUESTION_CREATOR_PAGE).forward(request, response);
                break;
            case CREATE_ACTION :
                try {
                    askQuestion(request);
                    request.setAttribute(QUESTIONS_FIELD_NAME, questionService.getAll());
                    request.getRequestDispatcher(MAIN_PAGE).forward(request, response);
                } catch (ServiceException ex) {
                    request.setAttribute(EXCEPTION_FIELD_NAME, ex);
                    request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
                }
                break;
        }
    }

    private void askQuestion(HttpServletRequest request) throws ServiceException {
        String topic = request.getParameter(TOPIC_FIELD_NAME);
        String content = request.getParameter(CONTENT_FIELD_NAME);
        String authorLogin = request.getSession(true).getAttribute(LOGIN_SESSION_FIELD_NAME).toString();
        questionService.createQuestion(topic, content, authorLogin);
    }

}
