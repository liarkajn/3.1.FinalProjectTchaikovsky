package main.java.com.likeit.web.controller;

import main.java.com.likeit.web.controller.handler.authorised.AuthorisedHandler;
import main.java.com.likeit.web.controller.handler.unauthorized.UnauthorisedHandler;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FrontController extends HttpServlet {

    private final static String LOCAL_FIELD_NAME = "local";
    private final static String LOGIN_FIELD_NAME = "login";
    private final static String INDEX_PAGE = "/WEB-INF/page/index.jsp";
    private final AuthorisedHandler authorisedHandler = new AuthorisedHandler();
    private final UnauthorisedHandler unauthorisedHandler = new UnauthorisedHandler();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession(true).getAttribute(LOGIN_FIELD_NAME) == null) {
            unauthorisedHandler.execute(request, response);
        } else {
            authorisedHandler.execute(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession(true).setAttribute(LOCAL_FIELD_NAME, request.getParameter(LOCAL_FIELD_NAME));
        request.getRequestDispatcher(INDEX_PAGE).forward(request, response);
    }

}
