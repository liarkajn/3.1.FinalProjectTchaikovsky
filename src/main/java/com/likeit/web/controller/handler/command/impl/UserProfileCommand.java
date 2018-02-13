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

public class UserProfileCommand implements Command {

    private final static String ID_FIELD_NAME = "id";
    private final static String USER_FIELD_NAME = "user";
    private final static String PROFILE_PAGE = "/WEB-INF/page/profile.jsp";
    private final static String ERROR_PAGE = "/likeIT?command=error&&message=";
    private final UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter(ID_FIELD_NAME));
        try {
            User user = userService.findUser(id);
            request.setAttribute(USER_FIELD_NAME, user);
            request.getRequestDispatcher(PROFILE_PAGE).forward(request, response);
        } catch (ServiceException ex) {
            response.sendRedirect(ERROR_PAGE + ex.getMessage());
        }
    }

}
