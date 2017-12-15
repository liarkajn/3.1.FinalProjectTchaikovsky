package main.java.com.likeit.web.controller.handler.command.impl;

import main.java.com.likeit.web.controller.handler.command.Command;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ErrorCommand implements Command {

    private final static String ERROR_PAGE = "/WEB-INF/page/error.jsp";
    private final static String ERROR_MESSAGE_FIELD_NAME = "message";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute(ERROR_MESSAGE_FIELD_NAME, request.getParameter(ERROR_MESSAGE_FIELD_NAME));
        request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
    }

}
