package com.likeit.web.controller.handler.command.impl.administration;

import com.likeit.web.controller.handler.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogOutCommand implements Command {

    private final static String ADMIN_SESSION_ID_ATTRIBUTE_NAME = "id";
    private final static String ADMIN_SESSION_ROLE_ATTRIBUTE_NAME = "role";
    private final static String AUTHORIZATION_PAGE = "/administration/authorization";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!AdministratorChecker.isAdmin(request)) {
            response.sendRedirect(AUTHORIZATION_PAGE);
            return;
        }
        request.getSession().removeAttribute(ADMIN_SESSION_ID_ATTRIBUTE_NAME);
        request.getSession().removeAttribute(ADMIN_SESSION_ROLE_ATTRIBUTE_NAME);
        response.sendRedirect(AUTHORIZATION_PAGE);
    }
}
