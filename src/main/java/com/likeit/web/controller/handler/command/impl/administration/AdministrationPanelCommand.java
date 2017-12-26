package main.java.com.likeit.web.controller.handler.command.impl.administration;

import main.java.com.likeit.web.controller.handler.command.Command;
import main.java.com.likeit.web.service.AnswerService;
import main.java.com.likeit.web.service.QuestionService;
import main.java.com.likeit.web.service.ServiceFactory;
import main.java.com.likeit.web.service.UserService;
import main.java.com.likeit.web.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdministrationPanelCommand implements Command {

    private final static String USER_ID_SESSION_FIELD_NAME = "id";
    private final static String QUESTIONS_PAGE = "?command=questions";
    private final static String ERROR_PAGE = "?command=error&&message=";
    private final UserService userService = ServiceFactory.getInstance().getUserService();
    private final QuestionService questionService = ServiceFactory.getInstance().getQuestionService();
    private final AnswerService answerService = ServiceFactory.getInstance().getAnswerService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = (int) request.getSession().getAttribute(USER_ID_SESSION_FIELD_NAME);
        try {
            if (userService.findUser(id).getRole() == 0) {
                String tab = request.getParameter("tab");
                switch (tab) {
                    case "users" :
                        break;
                    case "questions" :
                        questionService.findAllQuestions();
                        break;
                    case "bio" :
                        break;
                    default :

                        break;
                }
            } else {
                response.sendRedirect(QUESTIONS_PAGE);
            }
        } catch (ServiceException e) {
            response.sendRedirect(ERROR_PAGE + e.getMessage());
        }
    }

}
