package main.java.com.likeit.web.controller.handler.command.impl;

import main.java.com.likeit.web.controller.handler.command.Command;
import main.java.com.likeit.web.domain.Answer;
import main.java.com.likeit.web.service.AnswerService;
import main.java.com.likeit.web.service.ServiceFactory;
import main.java.com.likeit.web.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AnswerEditingCommand implements Command {

    private final static String USER_ID_SESSION_FIELD_NAME = "id";
    private final static String ANSWER_ID_FIELD_NAME = "answer_id";
    private final static String ANSWER_CONTENT_FIELD_NAME = "content";
    private final static String QUESTION_PAGE = "?command=question&&id=";
    private final static String ERROR_PAGE = "?command=error&&message=";
    private final AnswerService answerService = ServiceFactory.getInstance().getAnswerService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getSession().getAttribute(USER_ID_SESSION_FIELD_NAME).toString());
        String answerId = request.getParameter(ANSWER_ID_FIELD_NAME);
        String content = request.getParameter(ANSWER_CONTENT_FIELD_NAME);
        try {
            Answer answer = answerService.editAnswer(userId, Integer.parseInt(answerId), content);
            response.sendRedirect(QUESTION_PAGE + answer.getQuestion().getId());
        } catch (ServiceException ex) {
            response.sendRedirect(ERROR_PAGE + ex.getMessage());
        }
    }

}
