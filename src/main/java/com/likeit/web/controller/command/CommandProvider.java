package main.java.com.likeit.web.controller.command;

import main.java.com.likeit.web.controller.command.impl.QuestionCommandImpl;
import main.java.com.likeit.web.controller.command.impl.UserCommandImpl;

import java.util.HashMap;
import java.util.Map;

public class CommandProvider {

    private Map<CommandName, Command> commands = new HashMap<>();

    public CommandProvider() {
        commands.put(CommandName.USER, new UserCommandImpl());
        commands.put(CommandName.QUESTION, new QuestionCommandImpl());
    }

    public Command getCommand(String name) {
        CommandName commandName = CommandName.valueOf(name.toUpperCase());
        return commands.get(commandName);
    }

}
