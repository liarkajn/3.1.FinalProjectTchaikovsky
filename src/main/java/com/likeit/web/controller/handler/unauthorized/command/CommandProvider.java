package main.java.com.likeit.web.controller.handler.unauthorized.command;

import main.java.com.likeit.web.controller.handler.unauthorized.command.impl.*;

import java.util.HashMap;
import java.util.Map;

public class CommandProvider {

    private Map<CommandName, Command> commands = new HashMap<>();

    public CommandProvider() {
        commands.put(CommandName.SIGNIN, new SignInCommand());
        commands.put(CommandName.SIGNUP, new SignUpCommand());
        commands.put(CommandName.AUTHORIZATION, new AuthorizationCommand());
        commands.put(CommandName.REGISTRATION, new RegistrationCommand());
        commands.put(CommandName.ERROR, new ErrorCommand());
    }

    public Command getCommand(String name) {
        CommandName commandName = CommandName.valueOf(name.toUpperCase());
        return commands.get(commandName);
    }

}
