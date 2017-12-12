package main.java.com.likeit.web.controller.handler.unauthorized.command.impl;

import main.java.com.likeit.web.controller.handler.unauthorized.command.Command;
import main.java.com.likeit.web.domain.User;
import main.java.com.likeit.web.service.ServiceFactory;
import main.java.com.likeit.web.service.UserService;
import main.java.com.likeit.web.service.exception.ServiceException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthorizationCommand implements Command {

    private final static String LOGIN_FIELD_NAME = "login";
    private final static String PASSWORD_FIELD_NAME = "password";
    private final static String MAIN_PAGE = "?command=main";
    private final static String ERROR_PAGE = "?command=error&&message=";
    private final UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter(LOGIN_FIELD_NAME);
        String password = request.getParameter(PASSWORD_FIELD_NAME);
        User user;
        try {
            user = userService.signIn(username, password);
            request.getSession(true).setAttribute(LOGIN_FIELD_NAME, user.getLogin());
            response.sendRedirect(MAIN_PAGE);
        } catch (ServiceException ex) {
            response.sendRedirect(ERROR_PAGE + ex.getMessage());// сообщения исключений нельзя отправлять пользователю, вообще нельзя
        }
    }

}
