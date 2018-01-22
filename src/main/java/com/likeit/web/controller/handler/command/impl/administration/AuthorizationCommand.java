package com.likeit.web.controller.handler.command.impl.administration;

import com.likeit.web.controller.handler.command.Command;
import com.likeit.web.domain.User;
import com.likeit.web.service.ServiceFactory;
import com.likeit.web.service.UserService;
import com.likeit.web.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthorizationCommand implements Command {

    private final static String ADMIN_LOGIN_PARAMETER_NAME = "login";
    private final static String ADMIN_PASSWORD_PARAMETER_NAME = "password";
    private final static String ADMIN_SESSION_ID_ATTRIBUTE_NAME = "id";
    private final static String ADMIN_SESSION_ROLE_ATTRIBUTE_NAME = "role";
    private final static String ADMIN_SESSION_ROLE_ATTRIBUTE_VALUE = "admin";
    private final static String MAIN_PAGE = "/administration";
    private final static String ERROR_PAGE = "/main?command=error&&message=";
    private final UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter(ADMIN_LOGIN_PARAMETER_NAME);
        String password = request.getParameter(ADMIN_PASSWORD_PARAMETER_NAME);
        try {
            User user = userService.adminSignIn(login, password);
            request.getSession(true).setAttribute(ADMIN_SESSION_ID_ATTRIBUTE_NAME, user.getId());
            request.getSession(true).setAttribute(ADMIN_SESSION_ROLE_ATTRIBUTE_NAME, ADMIN_SESSION_ROLE_ATTRIBUTE_VALUE);
            response.sendRedirect(MAIN_PAGE);
        } catch (ServiceException ex) {
            response.sendRedirect(ERROR_PAGE + ex.getMessage());
        }
    }
}
