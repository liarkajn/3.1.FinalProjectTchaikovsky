package main.java.com.likeit.web.controller.handler.unauthorized.command.impl;

import main.java.com.likeit.web.controller.handler.unauthorized.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignInCommand implements Command {

    private final static String SIGN_IN_PAGE = "/WEB-INF/page/signIn.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(SIGN_IN_PAGE).forward(request, response);
    }

}