package com.likeit.web.controller.handler.command.impl;

import com.likeit.web.controller.handler.command.Command;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToQuestionCreationCommand implements Command {

    private final static String QUESTION_CREATION_PAGE = "/WEB-INF/page/question_creation.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(QUESTION_CREATION_PAGE).forward(request, response);
    }

}
