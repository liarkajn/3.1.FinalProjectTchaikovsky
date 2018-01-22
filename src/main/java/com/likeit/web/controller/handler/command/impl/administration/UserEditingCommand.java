package com.likeit.web.controller.handler.command.impl.administration;

import com.likeit.web.controller.handler.command.Command;
import com.likeit.web.domain.User;
import com.likeit.web.service.ServiceFactory;
import com.likeit.web.service.UserService;
import com.likeit.web.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserEditingCommand implements Command {

    private final static String AUTHORIZATION_PAGE = "/administration/authorization";
    private final static String USER_PAGE = "/administration?command=user&id=";
    private final static String ERROR_PAGE = "/main?command=error&&message=";
    private final static String ID_FIELD_NAME = "id";
    private final static String LOGIN_FIELD_NAME = "login";
    private final static String EMAIL_FIELD_NAME = "email";
    private final static String NAME_FIELD_NAME = "name";
    private final static String SURNAME_FIELD_NAME = "surname";
    private final static String BIO_FIELD_NAME = "bio";
    private final static String ROLE_FIELD_NAME = "role";
    private final UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!AdministratorChecker.isAdmin(request)) {
            response.sendRedirect(AUTHORIZATION_PAGE);
            return;
        }
        User user = new User();
        user.setId((Integer.parseInt(request.getParameter(ID_FIELD_NAME))));
        user.setLogin(request.getParameter(LOGIN_FIELD_NAME));
        user.setEmail(request.getParameter(EMAIL_FIELD_NAME));
        user.setName(request.getParameter(NAME_FIELD_NAME));
        user.setSurname(request.getParameter(SURNAME_FIELD_NAME));
        user.setBio(request.getParameter(BIO_FIELD_NAME));
        user.setRole(Short.parseShort(request.getParameter(ROLE_FIELD_NAME)));
        try {
            userService.editUser(user);
            response.sendRedirect(USER_PAGE + user.getId());
        } catch (ServiceException ex) {
            response.sendRedirect(ERROR_PAGE + ex.getMessage());
        }
    }

}
