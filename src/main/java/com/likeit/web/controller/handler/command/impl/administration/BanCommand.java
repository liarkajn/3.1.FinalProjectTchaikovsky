package com.likeit.web.controller.handler.command.impl.administration;

import com.likeit.web.controller.handler.command.Command;
import com.likeit.web.service.ServiceFactory;
import com.likeit.web.service.UserService;
import com.likeit.web.service.exception.ServiceException;
import com.likeit.web.service.exception.user.UserNotAdminException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BanCommand implements Command {

    private final static Logger logger = Logger.getLogger(BanCommand.class);

    private final static String USER_ID_SESSION_FIELD_NAME = "id";
    private final static String ID_FIELD_NAME = "id";
    private final static String BAN_FIELD_NAME = "ban";
    private final static String PROFILE_PAGE = "administration?command=user&id=";
    private final static String AUTHORIZATION_PAGE = "?command=go_to_authorization";
    private final UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute(USER_ID_SESSION_FIELD_NAME) != null) {
            executeRequest(request, response);
        } else {
            response.sendRedirect(AUTHORIZATION_PAGE);
        }
    }

    private void executeRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int adminId = (int) request.getSession().getAttribute(USER_ID_SESSION_FIELD_NAME);
        int userId = Integer.parseInt(request.getParameter(ID_FIELD_NAME));
        boolean isBan = Boolean.parseBoolean(request.getParameter(BAN_FIELD_NAME));
        try {
            userService.banUser(adminId, userId, isBan);
            response.sendRedirect(PROFILE_PAGE + userId);
        } catch (UserNotAdminException ex) {
            logger.error("User with id=" + adminId + " trying ban user with administration panel", ex);
            response.sendRedirect(AUTHORIZATION_PAGE);
        } catch (ServiceException ex) {
            logger.log(Level.FATAL, "Internal error", ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

}
