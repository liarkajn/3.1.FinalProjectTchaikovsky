package com.likeit.web.controller.handler.command.impl.administration;

import com.likeit.web.controller.handler.command.Command;
import com.likeit.web.service.ServiceFactory;
import com.likeit.web.service.UserService;
import com.likeit.web.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UsersCommand implements Command {

    private final static String USERS_FIELD_NAME = "users";
    private final static String ERROR_PAGE = "?command=error&&message=";
    private final static String AUTHORIZATION_PAGE = "/administration/authorization";
    private final static String USERS_PAGE = "/WEB-INF/page/administration/users.jsp";
    private final UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!AdministratorChecker.isAdmin(request)) {
            response.sendRedirect(AUTHORIZATION_PAGE);
            return;
        }
        try {
            request.setAttribute(USERS_FIELD_NAME, userService.findUsers());
            request.getRequestDispatcher(USERS_PAGE).forward(request, response);
        }
        catch (ServiceException ex) {
            response.sendRedirect(ERROR_PAGE + ex.getMessage());
        }
    }
}
