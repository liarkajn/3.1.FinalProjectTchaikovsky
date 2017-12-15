package main.java.com.likeit.web.controller.handler.command.impl;

import main.java.com.likeit.web.controller.handler.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogOutCommand implements Command {

    private final static String LOGIN_FIELD_NAME = "login";
    private final static String QUESTIONS_PAGE = "/main";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().removeAttribute(LOGIN_FIELD_NAME);
        response.sendRedirect(QUESTIONS_PAGE);
    }

}
