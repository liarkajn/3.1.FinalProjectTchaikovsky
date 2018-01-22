package com.likeit.web.controller.handler.command;

import com.likeit.web.controller.handler.command.impl.*;

import java.util.HashMap;
import java.util.Map;

public class CommandProvider {

    private Map<CommandName, Command> commands = new HashMap<>();

    public CommandProvider() {
        commands.put(CommandName.SIGNIN, new SignInCommand());
        commands.put(CommandName.SIGNUP, new SignUpCommand());
        commands.put(CommandName.AUTHORIZATION, new AuthorizationCommand());
        commands.put(CommandName.REGISTRATION, new RegistrationCommand());
        commands.put(CommandName.QUESTIONS, new QuestionsCommand());
        commands.put(CommandName.QUESTION, new QuestionCommand());
        commands.put(CommandName.QUESTION_CREATION, new QuestionCreationCommand());
        commands.put(CommandName.AFTER_QUESTION_CREATION, new AfterQuestionCreationCommand());
        commands.put(CommandName.AFTER_ANSWER_CREATION, new AfterAnswerCreationCommand());
        commands.put(CommandName.ANSWER_EDITING, new AnswerEditingCommand());
        commands.put(CommandName.VOTE_CREATION, new VoteCreationCommand());
        commands.put(CommandName.PROFILE, new UserProfileCommand());
        commands.put(CommandName.LOGOUT, new LogOutCommand());
        commands.put(CommandName.ERROR, new ErrorCommand());
    }

    public Command getCommand(String name) {
        CommandName commandName = CommandName.valueOf(name.toUpperCase());
        return commands.get(commandName);
    }

}
