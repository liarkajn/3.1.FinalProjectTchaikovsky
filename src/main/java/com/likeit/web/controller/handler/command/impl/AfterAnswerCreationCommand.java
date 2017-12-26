package main.java.com.likeit.web.controller.handler.command.impl;

import main.java.com.likeit.web.controller.handler.command.Command;
import main.java.com.likeit.web.service.AnswerService;
import main.java.com.likeit.web.service.ServiceFactory;
import main.java.com.likeit.web.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AfterAnswerCreationCommand implements Command {

    private final static String USER_ID_SESSION_FIELD_NAME = "id";
    private final static String QUESTION_ID_FIELD_NAME = "question_id";
    private final static String CONTENT_FIELD_NAME = "content";
    private final static String QUESTION_PAGE = "?command=question&&id=";
    private final static String ERROR_PAGE = "?command=error&&message=";
    private final AnswerService answerService = ServiceFactory.getInstance().getAnswerService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int authorId = (int) request.getSession().getAttribute(USER_ID_SESSION_FIELD_NAME);
        int questionId = Integer.parseInt(request.getParameter(QUESTION_ID_FIELD_NAME));
        String content = request.getParameter(CONTENT_FIELD_NAME);
        try {
            answerService.saveAnswer(authorId, questionId, content);
            response.sendRedirect(QUESTION_PAGE + questionId);
        } catch (ServiceException e) {
            response.sendRedirect(ERROR_PAGE + e.getMessage());
        }
    }

}
