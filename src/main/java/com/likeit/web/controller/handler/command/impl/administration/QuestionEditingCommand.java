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

public class QuestionEditingCommand implements Command {

    private final static String QUESTION_PAGE = "/administration?command=question&id=";
    private final static String AUTHORIZATION_PAGE = "/administration/authorization";
    private final static String ERROR_PAGE = "/main?command=error&&message=";
    private final static String ID_FIELD_NAME = "id";
    private final static String TOPIC_FIELD_NAME = "topic";
    private final static String CONTENT_FIELD_NAME = "content";
    private final QuestionService questionService = ServiceFactory.getInstance().getQuestionService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!AdministratorChecker.isAdmin(request)) {
            response.sendRedirect(AUTHORIZATION_PAGE);
            return;
        }
        Question question = new Question();
        question.setId((Integer.parseInt(request.getParameter(ID_FIELD_NAME))));
        question.setTopic(request.getParameter(TOPIC_FIELD_NAME));
        question.setContent(request.getParameter(CONTENT_FIELD_NAME));
        try {
            questionService.editQuestion(question);
            response.sendRedirect(QUESTION_PAGE + question.getId());
        } catch (ServiceException ex) {
            response.sendRedirect(ERROR_PAGE + ex.getMessage());
        }
    }

}
