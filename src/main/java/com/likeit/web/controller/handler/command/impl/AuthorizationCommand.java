package com.likeit.web.controller.handler.command.impl;

import com.likeit.web.controller.handler.command.Command;
import com.likeit.web.domain.User;
import com.likeit.web.service.ServiceFactory;
import com.likeit.web.service.UserService;
import com.likeit.web.service.exception.ServiceException;
import com.likeit.web.service.exception.user.*;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthorizationCommand implements Command {

    private final static Logger logger = Logger.getLogger(AuthorizationCommand.class);

    private final static String USER_ID_SESSION_FIELD_NAME = "id";
    private final static String LOGIN_FIELD_NAME = "login";
    private final static String PASSWORD_FIELD_NAME = "password";
    private final static String ERROR_MESSAGE_FIELD_NAME = "error_message";
    private final static String AUTHORIZATION_PAGE = "/WEB-INF/page/signIn.jsp";
    private final static String QUESTIONS_PAGE = "?command=questions";
    private final UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter(LOGIN_FIELD_NAME);
        String password = request.getParameter(PASSWORD_FIELD_NAME);
        User user;
        try {
            user = userService.authorization(username, password);
            request.getSession(true).setAttribute(USER_ID_SESSION_FIELD_NAME, user.getId());
            response.sendRedirect(QUESTIONS_PAGE);
        } catch (LoginValidationException ex) {
            logger.error("Login doesn't math with pattern", ex);
            handleException(request, response, "Wrong login format");
        } catch (PasswordValidationException ex) {
            logger.error("Password doesn't math with pattern", ex);
            handleException(request, response, "Wrong password format");
        } catch (NoSuchUserException ex) {
            logger.error("No such user", ex);
            handleException(request, response, "Incorrect login or password");
        } catch (BannedException ex) {
            logger.error("User banned", ex);
            handleException(request, response, "You have been banned!");
        } catch (ServiceException ex) {
            logger.log(Level.FATAL, "Internal error", ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void handleException(HttpServletRequest request, HttpServletResponse response, String errorMessage) throws ServletException, IOException {
        request.setAttribute(ERROR_MESSAGE_FIELD_NAME, errorMessage);
        request.getRequestDispatcher(AUTHORIZATION_PAGE).forward(request, response);
    }

}
