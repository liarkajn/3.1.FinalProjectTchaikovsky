package main.java.com.likeit.web.controller.command.impl;

import main.java.com.likeit.web.controller.command.Command;
import main.java.com.likeit.web.domain.User;
import main.java.com.likeit.web.service.QuestionService;
import main.java.com.likeit.web.service.ServiceFactory;
import main.java.com.likeit.web.service.UserService;
import main.java.com.likeit.web.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UserCommandImpl implements Command {

    private UserService userService = ServiceFactory.getInstance().getUserService();
    private QuestionService questionService = ServiceFactory.getInstance().getQuestionService();

    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        switch (action) {
            case "authorization":
                response.sendRedirect("jsp/signIn.jsp");
                break;
            case "registration":
                response.sendRedirect("jsp/signUp.jsp");
                break;
            case "signIn":
                signIn(request, response);
                break;
            case "signUp":
                signUp(request, response);
                break;
        }
    }

    private void signIn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("login");
        String password = request.getParameter("password");
        User user;
        try {
            user = userService.signIn(username, password);
            returnToMainPage(request, response, user);
        } catch (ServiceException ex) {
            request.setAttribute("exception", ex);
            request.getRequestDispatcher("/WEB-INF/page/error.jsp").forward(request, response);
        }
    }

    private void signUp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("login");
        String password = request.getParameter("password");
        String repeatedPassword = request.getParameter("repeatedPassword");
        String email = request.getParameter("email");
        User user;
        try {
            user = userService.signUp(username, password, repeatedPassword, email);
            returnToMainPage(request, response, user);
        } catch (ServiceException ex) {
            request.setAttribute("exception", ex);
            request.getRequestDispatcher("/WEB-INF/page/error.jsp").forward(request, response);
        }
    }

    private void returnToMainPage(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException, ServiceException {
        request.setAttribute("questions", questionService.getAll());
        request.setAttribute("user", user);
        request.getSession(true).setAttribute("login", user.getLogin());
        request.getRequestDispatcher("/WEB-INF/page/main.jsp").forward(request, response);
    }

}
