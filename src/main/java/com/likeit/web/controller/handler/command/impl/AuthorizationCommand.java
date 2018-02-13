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

public class AuthorizationCommand implements Command {

    private final static String USER_ID_SESSION_FIELD_NAME = "id";
    private final static String LOGIN_FIELD_NAME = "login";
    private final static String PASSWORD_FIELD_NAME = "password";
    private final static String ERROR_MESSAGE_FIELD_NAME = "error_message";
    private final static String AUTHORIZATION_PAGE = "/WEB-INF/page/signIn.jsp";
    private final static String QUESTIONS_PAGE = "?command=questions";
    private final static String ERROR_PAGE = "?command=error&&message=";
    private final UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter(LOGIN_FIELD_NAME);
        String password = request.getParameter(PASSWORD_FIELD_NAME);
        User user;
        try {
            user = userService.signIn(username, password);
            request.getSession(true).setAttribute(USER_ID_SESSION_FIELD_NAME, user.getId());
            response.sendRedirect(QUESTIONS_PAGE);
        } catch (ServiceException ex) {
            request.setAttribute(LOGIN_FIELD_NAME, username);
            request.setAttribute(ERROR_MESSAGE_FIELD_NAME, ex.getMessage());
            request.getRequestDispatcher(AUTHORIZATION_PAGE).forward(request, response);
//            response.sendRedirect(ERROR_PAGE + ex.getMessage());
        }
    }

}
