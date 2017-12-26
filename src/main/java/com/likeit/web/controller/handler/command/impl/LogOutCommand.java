package main.java.com.likeit.web.controller.handler.command.impl;

import main.java.com.likeit.web.controller.handler.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogOutCommand implements Command {

    private final static String USER_ID_SESSION_FIELD_NAME = "id";
    private final static String QUESTIONS_PAGE = "/main";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().removeAttribute(USER_ID_SESSION_FIELD_NAME);
        response.sendRedirect(QUESTIONS_PAGE);
    }

}
