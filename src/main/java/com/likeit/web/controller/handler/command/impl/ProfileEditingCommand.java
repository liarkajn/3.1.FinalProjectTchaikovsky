package com.likeit.web.controller.handler.command.impl;

import com.likeit.web.controller.handler.command.Command;
import com.likeit.web.domain.User;
import com.likeit.web.service.ServiceFactory;
import com.likeit.web.service.UserService;
import com.likeit.web.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProfileEditingCommand implements Command {

    private final static String ID_FIELD_NAME = "id";
    private final static String LOGIN_FIELD_NAME = "login";
    private final static String EMAIL_FIELD_NAME = "email";
    private final static String NAME_FIELD_NAME = "name";
    private final static String SURNAME_FIELD_NAME = "surname";
    private final static String GENDER_FIELD_NAME = "gender";
    private final static String BIO_FIELD_NAME = "bio";
    private final static String PROFILE_PAGE = "?command=profile&id=";
    private final static String ERROR_PAGE = "?command=error&message=";
    private final UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter(ID_FIELD_NAME));
        String login = request.getParameter(LOGIN_FIELD_NAME);
        String email = request.getParameter(EMAIL_FIELD_NAME);
        String name = request.getParameter(NAME_FIELD_NAME);
        String surname = request.getParameter(SURNAME_FIELD_NAME);
        String gender = request.getParameter(GENDER_FIELD_NAME);
        if (gender != null) {
            gender = gender.toLowerCase();
        }
        String bio = request.getParameter(BIO_FIELD_NAME);
        User user = new User();
        user.setId(id);
        user.setLogin(login);
        user.setEmail(email);
        user.setName(name);
        user.setSurname(surname);
        user.setGender(gender);
        user.setBio(bio);
        try {
            userService.editUser(user);
            response.sendRedirect(PROFILE_PAGE + user.getId());
        } catch (ServiceException ex) {
            response.sendRedirect(ERROR_PAGE + ex.getMessage());
        }
    }

}
