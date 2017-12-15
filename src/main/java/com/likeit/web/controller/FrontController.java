package main.java.com.likeit.web.controller;

import main.java.com.likeit.web.controller.handler.Handler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FrontController extends HttpServlet {

    private final static String LOCAL_FIELD_NAME = "local";
    private final static String QUESTIONS_PAGE = "/main?command=questions";
    private final Handler handler = new Handler();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handler.execute(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession(true).setAttribute(LOCAL_FIELD_NAME, request.getParameter(LOCAL_FIELD_NAME));
        response.sendRedirect(QUESTIONS_PAGE);
    }

}
