package com.likeit.web.controller.handler.command;

import com.likeit.web.controller.handler.command.impl.*;

import java.util.HashMap;
import java.util.Map;

public class CommandProvider {

    private Map<CommandName, Command> commands = new HashMap<>();

    public CommandProvider() {
        commands.put(CommandName.GO_TO_AUTHORIZATION, new GoToAuthorizationCommand());
        commands.put(CommandName.GO_TO_REGISTRATION, new GoToRegistrationCommand());
        commands.put(CommandName.AUTHORIZATION, new AuthorizationCommand());
        commands.put(CommandName.REGISTRATION, new RegistrationCommand());
        commands.put(CommandName.QUESTIONS, new QuestionsCommand());
        commands.put(CommandName.QUESTION, new QuestionCommand());
        commands.put(CommandName.GO_TO_QUESTION_CREATION, new GoToQuestionCreationCommand());
        commands.put(CommandName.QUESTION_CREATION, new QuestionCreationCommand());
        commands.put(CommandName.GO_TO_QUESTION_EDITING, new GoToQuestionEditingCommand());
        commands.put(CommandName.QUESTION_EDITING, new QuestionEditingCommand());
        commands.put(CommandName.QUESTION_DELETING, new QuestionDeletingCommand());
        commands.put(CommandName.ANSWER_CREATION, new AnswerCreationCommand());
        commands.put(CommandName.ANSWER_EDITING, new AnswerEditingCommand());
        commands.put(CommandName.ANSWER_RATING, new AnswerRatingCommand());
        commands.put(CommandName.PROFILE, new UserProfileCommand());
        commands.put(CommandName.GO_TO_PROFILE_EDITING, new GoToProfileEditingCommand());
        commands.put(CommandName.PROFILE_EDITING, new ProfileEditingCommand());
        commands.put(CommandName.LOGOUT, new LogOutCommand());
    }

    public Command getCommand(String name) {
        CommandName commandName = CommandName.valueOf(name.toUpperCase());
        return commands.get(commandName);
    }

}
