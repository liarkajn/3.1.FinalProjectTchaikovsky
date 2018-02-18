package com.likeit.web.controller.handler.command.impl.administration;

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
    private final static String ADMIN_LOGIN_PARAMETER_NAME = "login";
    private final static String ADMIN_PASSWORD_PARAMETER_NAME = "password";
    private final static String ADMIN_SESSION_ID_ATTRIBUTE_NAME = "id";
    private final static String ADMIN_SESSION_ROLE_ATTRIBUTE_NAME = "role";
    private final static String ADMIN_SESSION_ROLE_ATTRIBUTE_VALUE = "admin";
    private final static String ERROR_MESSAGE_FIELD_NAME = "error_message";
    private final static String AUTHORIZATION_PAGE = "/WEB-INF/page/administration/entry_point.jsp";
    private final static String QUESTIONS_PAGE = "?command=questions";
    private final UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute(USER_ID_SESSION_FIELD_NAME) == null) {
            executeRequest(request, response);
        } else {
            response.sendRedirect(QUESTIONS_PAGE);
        }
    }

    private void executeRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter(ADMIN_LOGIN_PARAMETER_NAME);
        String password = request.getParameter(ADMIN_PASSWORD_PARAMETER_NAME);
        try {
            User user = userService.adminAuthorization(login, password);
            request.getSession(true).setAttribute(ADMIN_SESSION_ID_ATTRIBUTE_NAME, user.getId());
            request.getSession(true).setAttribute(ADMIN_SESSION_ROLE_ATTRIBUTE_NAME, ADMIN_SESSION_ROLE_ATTRIBUTE_VALUE);
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
        } catch (UserNotAdminException ex) {
            logger.error("User without rights trying log in into administration panel", ex);
            handleException(request, response, "Incorrect login or password");
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
