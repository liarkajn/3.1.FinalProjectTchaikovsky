package main.java.com.likeit.web.controller.handler.command.impl;

import main.java.com.likeit.web.controller.handler.command.Command;
import main.java.com.likeit.web.domain.Question;
import main.java.com.likeit.web.service.QuestionService;
import main.java.com.likeit.web.service.ServiceFactory;
import main.java.com.likeit.web.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class QuestionCreationCommand implements Command {

    private final static String QUESTION_CREATION_PAGE = "/WEB-INF/page/question_creation.jsp";
    private final static String ERROR_PAGE = "?command=error&&message=";
    private final QuestionService questionService = ServiceFactory.getInstance().getQuestionService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("question_id") != null) {
            int questionId = Integer.parseInt(request.getParameter("question_id"));
            try {
                Question question = questionService.findQuestion(questionId);
                request.setAttribute("questionId", questionId);
                request.setAttribute("topic", question.getTopic());
                request.setAttribute("content", question.getContent());
            } catch (ServiceException e) {
                response.sendRedirect(ERROR_PAGE + e.getMessage());
            }
        }
        request.getRequestDispatcher(QUESTION_CREATION_PAGE).forward(request, response);
    }

}
