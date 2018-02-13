package com.likeit.web.controller.handler.command.impl;

import com.likeit.web.controller.handler.command.Command;
import com.likeit.web.domain.User;
import com.likeit.web.service.ServiceFactory;
import com.likeit.web.service.UserService;
import com.likeit.web.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegistrationCommand implements Command {

    private final static String USER_ID_SESSION_FIELD_NAME = "id";
    private final static String LOGIN_FIELD_NAME = "login";
    private final static String EMAIL_FIELD_NAME = "email";
    private final static String PASSWORD_FIELD_NAME = "password";
    private final static String REPEATED_PASSWORD_FIELD_NAME = "repeatedPassword";
    private final static String GENDER_FIELD_NAME = "gender";
    private final static String QUESTIONS_PAGE = "?command=questions";
    private final static String ERROR_PAGE = "?command=error&&message=";
    private final UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter(LOGIN_FIELD_NAME);
        String email = request.getParameter(EMAIL_FIELD_NAME);
        String password = request.getParameter(PASSWORD_FIELD_NAME);
        String repeatedPassword = request.getParameter(REPEATED_PASSWORD_FIELD_NAME);
        String gender = request.getParameter(GENDER_FIELD_NAME);
        if (gender != null) {
            gender = gender.toLowerCase();
        }
        User user;
        try {
            user = userService.signUp(login, email, password, repeatedPassword, gender);
            request.getSession(true).setAttribute(USER_ID_SESSION_FIELD_NAME, user.getId());
            response.sendRedirect(QUESTIONS_PAGE);
        } catch (ServiceException ex) {
            response.sendRedirect(ERROR_PAGE + ex.getMessage());
        }
    }

}
