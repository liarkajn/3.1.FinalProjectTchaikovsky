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

public class GoToProfileEditingCommand implements Command {

    private final static Logger logger = Logger.getLogger(GoToProfileEditingCommand.class);

    private final static String USER_ID_SESSION_FIELD_NAME = "id";
    private final static String USER_ID_FIELD_NAME = "id";
    private final static String USER_FIELD_NAME = "user";
    private final static String ERROR_MESSAGE_FIELD_NAME = "error_message";
    private final static String PROFILE_EDITING_PAGE = "/WEB-INF/page/profile_editing.jsp";
    private final static String QUESTIONS_PAGE = "main?command=questions";
    private final static String AUTHORIZATION_PAGE = "/WEB-INF/page/signIn.jsp";
    private final UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute(USER_ID_SESSION_FIELD_NAME) != null) {
            int userId = (int) request.getSession().getAttribute(USER_ID_SESSION_FIELD_NAME);
            int requestedId = Integer.parseInt(request.getParameter(USER_ID_FIELD_NAME));
            if (userId == requestedId) {
                executeRequest(request, response, requestedId);
            } else {
                executeRequest(request, response, userId);
            }
        } else {
            response.sendRedirect(QUESTIONS_PAGE);
        }
    }

    private void executeRequest(HttpServletRequest request, HttpServletResponse response, int userId) throws ServletException, IOException {
        User user;
        try {
            user = userService.findUser(userId);
            UserBannedUtil.isBanned(user);
            request.setAttribute(USER_FIELD_NAME, user);
            request.getRequestDispatcher(PROFILE_EDITING_PAGE).forward(request, response);
        } catch (BannedException ex) {
            logger.error("User banned", ex);
            handleException(request, response, "You have been banned!");
        } catch (NoSuchUserException ex) {
            logger.error("User trying edit not existent profile", ex);
            handleException(request, response, "You must log in for editing your profile");
        } catch (ServiceException ex) {
            logger.log(Level.FATAL, "Internal error", ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void handleException(HttpServletRequest request, HttpServletResponse response, String errorMessage) throws ServletException, IOException {
        request.getSession().removeAttribute(USER_ID_SESSION_FIELD_NAME);
        request.setAttribute(ERROR_MESSAGE_FIELD_NAME, errorMessage);
        request.getRequestDispatcher(AUTHORIZATION_PAGE).forward(request, response);
    }

}
