package com.likeit.web.controller.handler.command.impl.administration;

import com.likeit.web.controller.handler.command.Command;
import com.likeit.web.domain.User;
import com.likeit.web.service.ServiceFactory;
import com.likeit.web.service.UserService;
import com.likeit.web.service.exception.ServiceException;
import com.likeit.web.service.exception.user.NoSuchUserException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserCommand implements Command {

    private final static Logger logger = Logger.getLogger(UserCommand.class);

    private final static String USER_ID_SESSION_FIELD_NAME = "id";
    private final static String USER_PROFILE_PAGE = "/WEB-INF/page/administration/user.jsp";
    private final static String AUTHORIZATION_PAGE = "?command=gp_to_authorization";
    private final static String ID_FIELD_NAME = "id";
    private final static String USER_FIELD_NAME = "user";
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
        int id = Integer.parseInt(request.getParameter(ID_FIELD_NAME));
        try {
            User user = userService.findUser(id);
            request.setAttribute(USER_FIELD_NAME, user);
            request.getRequestDispatcher(USER_PROFILE_PAGE).forward(request, response);
        } catch (NoSuchUserException ex) {
            logger.error("Admin with id=" + adminId + " can't find user with id=" + id, ex);
            response.sendRedirect(AUTHORIZATION_PAGE);
        } catch (ServiceException ex) {
            logger.log(Level.FATAL, "Internal error", ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

}
