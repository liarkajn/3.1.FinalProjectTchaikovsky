package com.likeit.web.service;

import com.likeit.web.service.impl.AnswerServiceImpl;
import com.likeit.web.service.impl.QuestionServiceImpl;
import com.likeit.web.service.impl.UserServiceImpl;
import com.likeit.web.service.impl.VotingServiceImpl;

public class ServiceFactory {

    private static final ServiceFactory instance = new ServiceFactory();

    private AnswerService answerService = new AnswerServiceImpl();
    private QuestionService questionService = new QuestionServiceImpl();
    private UserService userService = new UserServiceImpl();
    private VotingService votingService = new VotingServiceImpl();

    private ServiceFactory() {}

    public static ServiceFactory getInstance() {
        return instance;
    }

    public AnswerService getAnswerService() {
        return answerService;
    }

    public QuestionService getQuestionService() {
        return questionService;
    }

    public UserService getUserService() {
        return userService;
    }

    public VotingService getVotingService() {
        return votingService;
    }
}
