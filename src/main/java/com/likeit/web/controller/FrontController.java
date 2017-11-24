package main.java.com.likeit.web.controller;

import main.java.com.likeit.web.controller.command.Command;
import main.java.com.likeit.web.controller.command.CommandProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FrontController extends HttpServlet {

    private final static String COMMAND_FIELD_NAME = "command";
    private final static String LOCAL_FIELD_NAME = "local";
    private final static String INDEX_PAGE = "index.jsp";
    private final CommandProvider commandProvider = new CommandProvider();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String commandName = request.getParameter(COMMAND_FIELD_NAME);
        Command command = commandProvider.getCommand(commandName);
        command.execute(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession(true).setAttribute(LOCAL_FIELD_NAME, request.getParameter(LOCAL_FIELD_NAME));
        request.getRequestDispatcher(INDEX_PAGE).forward(request, response);
    }

}
