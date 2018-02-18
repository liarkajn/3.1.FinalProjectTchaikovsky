package com.likeit.web.controller.handler.command.impl.administration;

import com.likeit.web.controller.handler.command.Command;
import com.likeit.web.domain.Gender;
import com.likeit.web.domain.Role;
import com.likeit.web.domain.User;
import com.likeit.web.service.ServiceFactory;
import com.likeit.web.service.UserService;
import com.likeit.web.service.exception.ServiceException;
import com.likeit.web.service.exception.user.NoSuchUserException;
import com.likeit.web.service.exception.user.UserNotAdminException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserEditingCommand implements Command {

    private final static Logger logger = Logger.getLogger(UserEditingCommand.class);

    private final static String USER_ID_SESSION_FIELD_NAME = "id";
    private final static String ID_FIELD_NAME = "id";
    private final static String LOGIN_FIELD_NAME = "login";
    private final static String EMAIL_FIELD_NAME = "email";
    private final static String NAME_FIELD_NAME = "name";
    private final static String SURNAME_FIELD_NAME = "surname";
    private final static String BIO_FIELD_NAME = "bio";
    private final static String GENDER_FIELD_NAME = "gender";
    private final static String ROLE_FIELD_NAME = "role";
    private final static String AUTHORIZATION_PAGE = "?command=go_to_authorization";
    private final static String USER_PAGE = "/administration?command=user&id=";
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
        int administratorId = (int) request.getSession().getAttribute(USER_ID_SESSION_FIELD_NAME);
        User user = new User();
        user.setId((Integer.parseInt(request.getParameter(ID_FIELD_NAME))));
        user.setLogin(request.getParameter(LOGIN_FIELD_NAME));
        user.setEmail(request.getParameter(EMAIL_FIELD_NAME));
        user.setName(request.getParameter(NAME_FIELD_NAME));
        user.setSurname(request.getParameter(SURNAME_FIELD_NAME));
        user.setBio(request.getParameter(BIO_FIELD_NAME));
        user.setGender(Gender.valueOf(request.getParameter(GENDER_FIELD_NAME).toUpperCase()));
        user.setRole(Role.valueOf(request.getParameter(ROLE_FIELD_NAME).toUpperCase()));
        try {
            userService.adminEditUser(administratorId, user);
            response.sendRedirect(USER_PAGE + user.getId());
        } catch (NoSuchUserException ex) {
            logger.error("Administrator with id=" + administratorId + " trying nonexistent user", ex);
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } catch (UserNotAdminException ex) {
            logger.error("Administrator with id=" + administratorId + " trying edit user with administration panel", ex);
            response.sendRedirect(AUTHORIZATION_PAGE);
        } catch (ServiceException ex) {
            logger.log(Level.FATAL, "Internal error", ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

}
