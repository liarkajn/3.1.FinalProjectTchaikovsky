package com.likeit.web.controller.handler.command.impl;

import com.likeit.web.controller.handler.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToAuthorizationCommand implements Command {

    private final static String USER_ID_FIELD_NAME = "id";
    private final static String SIGN_IN_PAGE = "/WEB-INF/page/signIn.jsp";
    private final static String QUESTIONS_PAGE = "main?command=questions";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute(USER_ID_FIELD_NAME) != null) {
            response.sendRedirect(QUESTIONS_PAGE);
        } else {
            request.getRequestDispatcher(SIGN_IN_PAGE).forward(request, response);
        }
    }

}