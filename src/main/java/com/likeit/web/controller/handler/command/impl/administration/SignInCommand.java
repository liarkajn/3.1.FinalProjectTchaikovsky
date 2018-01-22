package com.likeit.web.controller.handler.command.impl.administration;

import com.likeit.web.controller.handler.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignInCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/page/administration/entry_point.jsp").forward(request, response);
    }
}
