package com.likeit.web.controller.handler;

import com.likeit.web.controller.handler.command.AdminCommandProvider;
import com.likeit.web.controller.handler.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminHandler {

    private final static String COMMAND_FIELD_NAME = "command";
    private final static String DEFAULT_COMMAND = "questions";
    private AdminCommandProvider commandProvider = new AdminCommandProvider();

    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String commandName = request.getParameter(COMMAND_FIELD_NAME);
        if (commandName == null) {
            Command command = commandProvider.getCommand(DEFAULT_COMMAND);
            command.execute(request, response);
        } else {
            Command command = commandProvider.getCommand(commandName);
            command.execute(request, response);
        }
    }

}
