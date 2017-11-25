package main.java.com.likeit.web.controller.handler.authorised.command.impl;

import main.java.com.likeit.web.controller.handler.authorised.command.Command;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class QuestCreationCommand implements Command {

    private final static String QUESTION_CREATION_PAGE = "/WEB-INF/page/question_creation.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(QUESTION_CREATION_PAGE).forward(request, response);
    }

}
