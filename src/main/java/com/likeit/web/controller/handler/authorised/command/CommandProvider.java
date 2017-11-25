package main.java.com.likeit.web.controller.handler.authorised.command;

import main.java.com.likeit.web.controller.handler.authorised.command.impl.AfterQuestionCreationCommand;
import main.java.com.likeit.web.controller.handler.authorised.command.impl.ErrorCommand;
import main.java.com.likeit.web.controller.handler.authorised.command.impl.MainCommand;
import main.java.com.likeit.web.controller.handler.authorised.command.impl.QuestCreationCommand;
import java.util.HashMap;
import java.util.Map;

public class CommandProvider {

    private Map<CommandName, Command> commands = new HashMap<>();

    public CommandProvider() {
        commands.put(CommandName.MAIN, new MainCommand());
        commands.put(CommandName.QUESTION_CREATION, new QuestCreationCommand());
        commands.put(CommandName.AFTER_QUESTION_CREATION, new AfterQuestionCreationCommand());
        commands.put(CommandName.ERROR, new ErrorCommand());
    }

    public Command getCommand(String name) {
        CommandName commandName = CommandName.valueOf(name.toUpperCase());
        return commands.get(commandName);
    }

}
