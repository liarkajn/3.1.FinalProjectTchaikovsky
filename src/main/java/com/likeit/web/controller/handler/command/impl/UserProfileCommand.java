package com.likeit.web.controller.handler.command.impl;

import com.likeit.web.controller.handler.command.Command;
import com.likeit.web.controller.handler.command.impl.exception.user.BannedException;
import com.likeit.web.controller.handler.command.impl.util.UserBannedUtil;
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

public class UserProfileCommand implements Command {

    private final static Logger logger = Logger.getLogger(UserProfileCommand.class);

    private final static String ID_FIELD_NAME = "id";
    private final static String USER_FIELD_NAME = "user";
    private final static String ERROR_MESSAGE_FIELD_NAME = "error_message";
    private final static String PROFILE_PAGE = "/WEB-INF/page/profile.jsp";
    private final UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter(ID_FIELD_NAME));
        User user = null;
        try {
            user = userService.findUser(id);
            UserBannedUtil.isBanned(user);
            request.setAttribute(USER_FIELD_NAME, user);
            request.getRequestDispatcher(PROFILE_PAGE).forward(request, response);
        } catch (NoSuchUserException ex) {
            logger.error("Unable to find user profile with id: " + id);
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } catch (BannedException ex) {
            request.setAttribute(USER_FIELD_NAME, user);
            request.setAttribute(ERROR_MESSAGE_FIELD_NAME, "This user has been banned");
            request.getRequestDispatcher(PROFILE_PAGE).forward(request, response);
        } catch (ServiceException ex) {
            logger.log(Level.FATAL, "Internal error", ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

}
