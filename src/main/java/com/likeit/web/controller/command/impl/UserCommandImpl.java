package main.java.com.likeit.web.controller.command.impl;

import main.java.com.likeit.web.controller.command.Command;
import main.java.com.likeit.web.domain.User;
import main.java.com.likeit.web.service.QuestionService;
import main.java.com.likeit.web.service.ServiceFactory;
import main.java.com.likeit.web.service.UserService;
import main.java.com.likeit.web.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserCommandImpl implements Command {

    private final static String MAIN_PAGE = "/WEB-INF/page/main.jsp";
    private final static String SIGN_IN_PAGE = "jsp/signIn.jsp";
    private final static String SIGN_UP_PAGE = "jsp/signUp.jsp";
    private final static String ERROR_PAGE = "/WEB-INF/page/error.jsp";
    private final static String LOGIN_FIELD_NAME = "login";
    private final static String PASSWORD_FIELD_NAME = "password";
    private final static String REPEATED_PASSWORD_FIELD_NAME = "repeatedPassword";
    private final static String EMAIL_FIELD_NAME = "email";
    private final static String QUESTIONS_FIELD_NAME = "questions";
    private final static String EXCEPTION_FIELD_NAME = "exception";
    private final static String ACTION_FIELD_NAME = "action";
    private final static String ACTION_AUTHORIZATION = "authorization";
    private final static String ACTION_REGISTRATION = "registration";
    private final static String ACTION_SIGN_IN = "signIn";
    private final static String ACTION_SIGN_UP = "signUp";
    private UserService userService = ServiceFactory.getInstance().getUserService();
    private QuestionService questionService = ServiceFactory.getInstance().getQuestionService();

    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute(LOGIN_FIELD_NAME) != null) {
            try {
                returnToMainPage(request, response);
            } catch (ServiceException ex) {
                returnToErrorPage(request, response, ex);
            }
        } else {
            performRequest(request, response);
        }
    }

    private void performRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter(ACTION_FIELD_NAME);
        switch (action) {
            case ACTION_AUTHORIZATION:
                response.sendRedirect(SIGN_IN_PAGE);
                break;
            case ACTION_REGISTRATION:
                response.sendRedirect(SIGN_UP_PAGE);
                break;
            case ACTION_SIGN_IN:
                signIn(request, response);
                break;
            case ACTION_SIGN_UP:
                signUp(request, response);
                break;
        }
    }

    private void signIn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter(LOGIN_FIELD_NAME);
        String password = request.getParameter(PASSWORD_FIELD_NAME);
        User user;
        try {
            user = userService.signIn(username, password);
            request.getSession(true).setAttribute(LOGIN_FIELD_NAME, user.getLogin());
            returnToMainPage(request, response);
        } catch (ServiceException ex) {
            returnToErrorPage(request, response, ex);
        }
    }

    private void signUp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter(LOGIN_FIELD_NAME);
        String password = request.getParameter(PASSWORD_FIELD_NAME);
        String repeatedPassword = request.getParameter(REPEATED_PASSWORD_FIELD_NAME);
        String email = request.getParameter(EMAIL_FIELD_NAME);
        User user;
        try {
            user = userService.signUp(username, password, repeatedPassword, email);
            request.getSession(true).setAttribute(LOGIN_FIELD_NAME, user.getLogin());
            returnToMainPage(request, response);
        } catch (ServiceException ex) {
            returnToErrorPage(request, response, ex);
        }
    }

    private void returnToMainPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ServiceException {
        request.setAttribute(QUESTIONS_FIELD_NAME, questionService.getAll());
        request.getRequestDispatcher(MAIN_PAGE).forward(request, response);
    }

    private void returnToErrorPage(HttpServletRequest request, HttpServletResponse response, ServiceException ex) throws ServletException, IOException {
        request.setAttribute(EXCEPTION_FIELD_NAME, ex);
        request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
    }

}
