package main.java.com.likeit.web.service;

import main.java.com.likeit.web.service.impl.AnswerServiceImpl;
import main.java.com.likeit.web.service.impl.QuestionServiceImpl;
import main.java.com.likeit.web.service.impl.UserServiceImpl;

public class ServiceFactory {

    private static final ServiceFactory instance = new ServiceFactory();

    private AnswerService answerService = new AnswerServiceImpl();
    private QuestionService questionService = new QuestionServiceImpl();
    private UserService userService = new UserServiceImpl();

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

}
