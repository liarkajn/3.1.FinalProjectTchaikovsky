package main.java.com.likeit.web.controller.handler.unauthorized;

import main.java.com.likeit.web.controller.handler.unauthorized.command.CommandProvider;
import main.java.com.likeit.web.controller.handler.unauthorized.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UnauthorisedHandler {

    private final static String COMMAND_FIELD_NAME = "command";
    private final static String INDEX_PAGE = "/WEB-INF/page/index.jsp";
    private CommandProvider commandProvider = new CommandProvider();

    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String commandName = request.getParameter(COMMAND_FIELD_NAME);

        if (commandName == null) {
            request.getRequestDispatcher(INDEX_PAGE).forward(request, response);
        } else {
            Command command = commandProvider.getCommand(commandName);
            command.execute(request, response);
        }

    }

}
