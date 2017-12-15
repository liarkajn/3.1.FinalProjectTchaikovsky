package main.java.com.likeit.web.controller.handler;

import main.java.com.likeit.web.controller.handler.command.Command;
import main.java.com.likeit.web.controller.handler.command.CommandProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Handler {

    private final static String COMMAND_FIELD_NAME = "command";
    private final static String QUESTIONS_PAGE = "/WEB-INF/page/questions.jsp";
    private CommandProvider commandProvider = new CommandProvider();

    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String commandName = request.getParameter(COMMAND_FIELD_NAME);

        if (commandName == null) {
            Command command = commandProvider.getCommand("questions");
            command.execute(request, response);
//            request.getRequestDispatcher(QUESTIONS_PAGE).forward(request, response);
        } else {
            Command command = commandProvider.getCommand(commandName);
            command.execute(request, response);
        }

    }

}
