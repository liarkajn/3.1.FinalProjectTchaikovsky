package com.likeit.web.controller.handler.command.impl;

import com.likeit.web.controller.handler.command.Command;
import com.likeit.web.domain.Gender;
import com.likeit.web.domain.User;
import com.likeit.web.service.ServiceFactory;
import com.likeit.web.service.UserService;
import com.likeit.web.service.exception.ServiceException;
import com.likeit.web.service.exception.user.BannedException;
import com.likeit.web.service.exception.user.NoSuchUserException;
import com.likeit.web.service.exception.user.ProfileOwnerException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProfileEditingCommand implements Command {

    private final static Logger logger = Logger.getLogger(ProfileEditingCommand.class);

    private final static String USER_ID_SESSION_FIELD_NAME = "id";
    private final static String ID_FIELD_NAME = "id";
    private final static String LOGIN_FIELD_NAME = "login";
    private final static String EMAIL_FIELD_NAME = "email";
    private final static String NAME_FIELD_NAME = "name";
    private final static String SURNAME_FIELD_NAME = "surname";
    private final static String GENDER_FIELD_NAME = "gender";
    private final static String BIO_FIELD_NAME = "bio";
    private final static String ERROR_MESSAGE_FIELD_NAME = "error_message";
    private final static String PROFILE_PAGE = "?command=profile&id=";
    private final static String AUTHORIZATION_PAGE = "?command=go_to_authorization";
    private final static String AUTHORIZATION_PAGE_JSP = "/WEB-INF/page/signIn.jsp";
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
        int userId = (int) request.getSession().getAttribute(USER_ID_SESSION_FIELD_NAME);
        int requestedId = Integer.parseInt(request.getParameter(ID_FIELD_NAME));
        if (userId == requestedId) {
            String login = request.getParameter(LOGIN_FIELD_NAME);
            String email = request.getParameter(EMAIL_FIELD_NAME);
            String name = request.getParameter(NAME_FIELD_NAME);
            String surname = request.getParameter(SURNAME_FIELD_NAME);
            Gender gender = Gender.valueOf(request.getParameter(GENDER_FIELD_NAME).toUpperCase());
            String bio = request.getParameter(BIO_FIELD_NAME);
            User profile = new User();
            profile.setId(requestedId);
            profile.setLogin(login);
            profile.setEmail(email);
            profile.setName(name);
            profile.setSurname(surname);
            profile.setGender(gender);
            profile.setBio(bio);
            try {
                userService.editUser(userId, profile);
                response.sendRedirect(PROFILE_PAGE + userId);
            } catch (NoSuchUserException ex) {
                logger.error("Unable to find user profile with id: " + requestedId);
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            } catch (BannedException ex) {
                request.setAttribute(ERROR_MESSAGE_FIELD_NAME, "You have been banned");
                request.getRequestDispatcher(AUTHORIZATION_PAGE_JSP).forward(request, response);
            } catch (ProfileOwnerException ex) {
                logger.error("User with id: " + userId + " trying update user with id: " + requestedId);
                response.sendRedirect(PROFILE_PAGE + requestedId);
            }
            catch (ServiceException ex) {
                logger.log(Level.FATAL, "Internal error", ex);
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else {
            response.sendRedirect(PROFILE_PAGE + userId);
        }
    }

}
