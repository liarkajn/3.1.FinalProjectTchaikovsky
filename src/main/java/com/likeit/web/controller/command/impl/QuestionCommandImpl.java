package main.java.com.likeit.web.controller.command.impl;

import main.java.com.likeit.web.controller.command.Command;
import main.java.com.likeit.web.service.QuestionService;
import main.java.com.likeit.web.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class QuestionCommandImpl implements Command {

    private QuestionService questionService = ServiceFactory.getInstance().getQuestionService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        switch (action) {
            case "ask":
                request.getRequestDispatcher("/WEB-INF/page/createQuestion.jsp").forward(request, response);
                break;
            case "create" :
                askQuestion(request);
                request.setAttribute("questions", questionService.getAll());
                request.getRequestDispatcher("/WEB-INF/page/main.jsp").forward(request, response);
                break;
        }
    }

    private void askQuestion(HttpServletRequest request) {
        String topic = request.getParameter("topic");
        String content = request.getParameter("content");
        String authorLogin = request.getSession(true).getAttribute("login").toString();
        questionService.createQuestion(topic, content, authorLogin);
    }

}
