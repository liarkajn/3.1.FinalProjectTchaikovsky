package com.likeit.web.controller.handler.command.impl.administration;

import com.likeit.web.controller.handler.command.Command;
import com.likeit.web.service.ServiceFactory;
import com.likeit.web.service.UserService;
import com.likeit.web.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BanCommand implements Command {

    private final static String ID_FIELD_NAME = "id";
    private final static String BAN_FIELD_NAME = "ban";
    private final static String PROFILE_PAGE = "administration?command=user&id=";
    private final static String ERROR_PAGE = "/main?command=error&&message=";
    private final UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter(ID_FIELD_NAME));
        boolean isBan = Boolean.parseBoolean(request.getParameter(BAN_FIELD_NAME));
        try {
            userService.banUser(userId, isBan);
            response.sendRedirect(PROFILE_PAGE + userId);
        } catch (ServiceException e) {
            response.sendRedirect(ERROR_PAGE + e.getMessage());
        }
    }

}
