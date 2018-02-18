package com.likeit.web.controller.handler.command.impl;

import com.likeit.web.controller.handler.command.Command;
import com.likeit.web.domain.Gender;
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

public class RegistrationCommand implements Command {

    private final static Logger logger = Logger.getLogger(RegistrationCommand.class);

    private final static String USER_ID_SESSION_FIELD_NAME = "id";
    private final static String LOGIN_FIELD_NAME = "login";
    private final static String EMAIL_FIELD_NAME = "email";
    private final static String PASSWORD_FIELD_NAME = "password";
    private final static String REPEATED_PASSWORD_FIELD_NAME = "repeatedPassword";
    private final static String GENDER_FIELD_NAME = "gender";
    private final static String ERROR_MESSAGE_FIELD_NAME = "error_message";
    private final static String QUESTIONS_PAGE = "?command=questions";
    private final static String REGISTRATION_PAGE = "/WEB-INF/page/signUp.jsp";
    private final UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter(LOGIN_FIELD_NAME);
        String email = request.getParameter(EMAIL_FIELD_NAME);
        String password = request.getParameter(PASSWORD_FIELD_NAME);
        String repeatedPassword = request.getParameter(REPEATED_PASSWORD_FIELD_NAME);
        Gender gender = Gender.valueOf(request.getParameter(GENDER_FIELD_NAME).toUpperCase());
        User user;
        try {
            user = userService.registration(login, email, password, repeatedPassword, gender);
            request.getSession(true).setAttribute(USER_ID_SESSION_FIELD_NAME, user.getId());
            response.sendRedirect(QUESTIONS_PAGE);
        } catch (LoginValidationException ex) {
            logger.error("Login doesn't math with pattern", ex);
            handleException(request, response, "Wrong login format");
        } catch (EmailValidationException ex) {
            logger.error("Email doesn't math with pattern", ex);
            handleException(request, response, "Wrong email format");
        } catch (PasswordValidationException ex) {
            logger.error("Password doesn't math with pattern", ex);
            handleException(request, response, "Wrong password format");
        } catch (PasswordMatchException ex) {
            logger.error("Password and repeated password are different", ex);
            handleException(request, response, "Password and repeated password are different");
        } catch (LoginExistingException ex) {
            logger.error("User with same login already exists", ex);
            handleException(request, response, "User with same login already exists");
        } catch (EmailExistingException ex) {
            logger.error("User with same email already exists", ex);
            handleException(request, response, "User with same email already exists");
        } catch (ServiceException ex) {
            logger.log(Level.FATAL, "Internal error", ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void handleException(HttpServletRequest request, HttpServletResponse response, String errorMessage) throws ServletException, IOException {
        request.setAttribute(ERROR_MESSAGE_FIELD_NAME, errorMessage);
        request.getRequestDispatcher(REGISTRATION_PAGE).forward(request, response);
    }

}
