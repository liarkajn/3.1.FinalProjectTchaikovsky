package main.java.com.likeit.web.controller.handler.authorised;

import main.java.com.likeit.web.controller.handler.authorised.command.Command;
import main.java.com.likeit.web.controller.handler.authorised.command.CommandProvider;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthorisedHandler {

    private final static String COMMAND_FIELD_NAME = "command";
    private final static String MAIN_PAGE = "?command=main";
    private CommandProvider commandProvider = new CommandProvider();

    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String commandName = request.getParameter(COMMAND_FIELD_NAME);

        if (commandName == null) {
            response.sendRedirect(request.getPathInfo() + MAIN_PAGE);
        } else {
            Command command = commandProvider.getCommand(commandName);
            command.execute(request, response);
        }

    }

}
