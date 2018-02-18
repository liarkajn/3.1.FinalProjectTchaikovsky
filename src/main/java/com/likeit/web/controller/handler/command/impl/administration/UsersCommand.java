package com.likeit.web.controller.handler.command.impl.administration;

import com.likeit.web.controller.handler.command.Command;
import com.likeit.web.service.ServiceFactory;
import com.likeit.web.service.UserService;
import com.likeit.web.service.exception.ServiceException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.lang.Math.ceil;

public class UsersCommand implements Command {

    private final static Logger logger = Logger.getLogger(BanCommand.class);

    private final static String USER_ID_SESSION_FIELD_NAME = "id";
    private final static int questionsPerPage = 5;
    private final static int paginationPagesPerSide = 2;
    private final static String USERS_FIELD_NAME = "users";
    private final static String PAGE_NUMBER_FIELD_NAME = "page";
    private final static String CURRENT_PAGE_FIELD_NAME = "currentPage";
    private final static String LAST_PAGE_FIELD_NAME = "lastPage";
    private final static String OFFSET_FIELD_NAME = "offset";
    private final static String AUTHORIZATION_PAGE = "/administration/authorization";
    private final static String USERS_PAGE = "/WEB-INF/page/administration/users.jsp";
    private final UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute(USER_ID_SESSION_FIELD_NAME) != null) {
            int page = 1;
            if (request.getParameter(PAGE_NUMBER_FIELD_NAME) != null) {
                page = Integer.parseInt(request.getParameter(PAGE_NUMBER_FIELD_NAME));
            }
            try {
                request.setAttribute(USERS_FIELD_NAME, userService.findUsers(questionsPerPage, page));
                setPaginationAttribute(request, page);
                request.getRequestDispatcher(USERS_PAGE).forward(request, response);
            }
            catch (ServiceException ex) {
                logger.log(Level.FATAL, "Internal error", ex);
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else {
            response.sendRedirect(AUTHORIZATION_PAGE);
        }
    }

    private void setPaginationAttribute(HttpServletRequest request, int page) throws ServiceException {
        int questionsCount;
        questionsCount = userService.getUsersCount();
        request.setAttribute(CURRENT_PAGE_FIELD_NAME, page);
        int lastPage = (int)ceil((double)questionsCount / questionsPerPage);
        request.setAttribute(LAST_PAGE_FIELD_NAME, lastPage);
        request.setAttribute(OFFSET_FIELD_NAME, paginationPagesPerSide);
    }
}
