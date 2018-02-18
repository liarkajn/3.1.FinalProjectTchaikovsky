package com.likeit.web.controller;

import com.likeit.web.controller.handler.AdminHandler;
import com.likeit.web.controller.handler.Handler;
import com.likeit.web.dao.connector.ConnectionPool;
import com.likeit.web.dao.connector.ConnectionPoolException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

public class FrontController extends HttpServlet {

    private final static String ADMINISTRATION_PAGE_PARAMETER = "isAdminPage";
    private final static String LOCAL_FIELD_NAME = "local";
    private final static String QUESTIONS_PAGE = "/main?command=questions";
    private final AdminHandler adminHandler = new AdminHandler();
    private final Handler handler = new Handler();

    @Override
    public void init() throws ServletException {
        try {
            ConnectionPool.getInstance().initPoolData();
        } catch (ConnectionPoolException e) {
            throw new RuntimeException("Unable initialize connection pool");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getAttribute(ADMINISTRATION_PAGE_PARAMETER) != null) {
            adminHandler.execute(request, response);
        } else {
            handler.execute(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession(true).setAttribute(LOCAL_FIELD_NAME, request.getParameter(LOCAL_FIELD_NAME));
        response.sendRedirect(QUESTIONS_PAGE);
    }

}
