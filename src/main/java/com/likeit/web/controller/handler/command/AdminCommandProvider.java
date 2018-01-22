package com.likeit.web.controller.handler.command;

import com.likeit.web.controller.handler.command.impl.administration.*;

import java.util.HashMap;
import java.util.Map;

public class AdminCommandProvider {

    private Map<AdminCommandName, Command> commands = new HashMap<>();

    public AdminCommandProvider() {
        commands.put(AdminCommandName.SIGN_IN, new SignInCommand());
        commands.put(AdminCommandName.AUTHORIZATION, new AuthorizationCommand());
        commands.put(AdminCommandName.LOG_OUT, new LogOutCommand());
        commands.put(AdminCommandName.QUESTIONS, new QuestionsCommand());
        commands.put(AdminCommandName.QUESTION, new QuestionCommand());
        commands.put(AdminCommandName.USERS, new UsersCommand());
        commands.put(AdminCommandName.USER, new UserCommand());
        commands.put(AdminCommandName.USER_EDITING, new UserEditingCommand());
        commands.put(AdminCommandName.QUESTION_EDITING, new QuestionEditingCommand());
    }

    public Command getCommand(String name) {
        AdminCommandName commandName = AdminCommandName.valueOf(name.toUpperCase());
        return commands.get(commandName);
    }

}