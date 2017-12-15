package main.java.com.likeit.web.controller.handler.command.impl;

import main.java.com.likeit.web.controller.handler.command.Command;
import main.java.com.likeit.web.domain.User;
import main.java.com.likeit.web.service.ServiceFactory;
import main.java.com.likeit.web.service.UserService;
import main.java.com.likeit.web.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegistrationCommand implements Command {

    private final static String LOGIN_FIELD_NAME = "login";
    private final static String PASSWORD_FIELD_NAME = "password";
    private final static String REPEATED_PASSWORD_FIELD_NAME = "repeatedPassword";
    private final static String EMAIL_FIELD_NAME = "email";
    private final static String QUESTIONS_PAGE = "?command=questions";
    private final static String ERROR_PAGE = "?command=error&&message=";
    private final UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter(LOGIN_FIELD_NAME);
        String password = request.getParameter(PASSWORD_FIELD_NAME);
        String repeatedPassword = request.getParameter(REPEATED_PASSWORD_FIELD_NAME);
        String email = request.getParameter(EMAIL_FIELD_NAME);
        User user;
        try {
            user = userService.signUp(username, password, repeatedPassword, email);
            request.getSession(true).setAttribute(LOGIN_FIELD_NAME, user.getLogin());
            response.sendRedirect(QUESTIONS_PAGE);
        } catch (ServiceException ex) {
            response.sendRedirect(ERROR_PAGE + ex.getMessage());
        }
    }

}
