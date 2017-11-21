package main.java.com.likeit.web.controller.command.impl;

import main.java.com.likeit.web.controller.command.Command;
import main.java.com.likeit.web.domain.User;
import main.java.com.likeit.web.service.QuestionService;
import main.java.com.likeit.web.service.ServiceFactory;
import main.java.com.likeit.web.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserCommandImpl implements Command {

    private UserService userService = ServiceFactory.getInstance().getUserService();
    private QuestionService questionService = ServiceFactory.getInstance().getQuestionService();

    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        User user;
        switch (action) {
            case "authorization" :
                response.sendRedirect("jsp/signIn.jsp");
                break;
            case "registration" :
                response.sendRedirect("jsp/signUp.jsp");
                break;
            case "signIn":
                user = signIn(request);
                request.getSession(true).setAttribute("login", user.getLogin());
                request.setAttribute("user", user);
                request.setAttribute("questions", questionService.getAll());
                request.getRequestDispatcher("/WEB-INF/page/main.jsp").forward(request, response);
                break;
            case "signUp" :
                user = signUp(request);
                request.getSession(true).setAttribute("login", user.getLogin());
                request.setAttribute("user", user);
                request.setAttribute("questions", questionService.getAll());
                request.getRequestDispatcher("/WEB-INF/page/main.jsp").forward(request, response);
                break;
        }
    }

    private User signIn(HttpServletRequest request) {
        String username = request.getParameter("login");
        String password = request.getParameter("password");
        User user;
        try {
            user = userService.signIn(username, password);
        } catch (Exception e) {
            user = new User();
        }
        return user;
    }

    private User signUp(HttpServletRequest request) {
        String username = request.getParameter("login");
        String password = request.getParameter("password");
        String repeatedPassword = request.getParameter("repeatedPassword");
        String email = request.getParameter("email");
        User user;
        try {
            user = userService.signUp(username, password, repeatedPassword, email);
        } catch (Exception e) {
            user = new User();
        }
        return user;
    }

}
