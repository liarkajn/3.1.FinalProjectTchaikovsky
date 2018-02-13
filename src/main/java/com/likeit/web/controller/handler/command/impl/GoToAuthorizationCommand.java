package com.likeit.web.controller.handler.command.impl;

import com.likeit.web.controller.handler.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToAuthorizationCommand implements Command {

    private final static String SIGN_IN_PAGE = "/WEB-INF/page/signIn.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(SIGN_IN_PAGE).forward(request, response);
    }

}
