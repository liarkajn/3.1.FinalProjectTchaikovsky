package com.likeit.web.controller.handler.command.impl;

import com.likeit.web.controller.handler.command.Command;
import com.likeit.web.service.ServiceFactory;
import com.likeit.web.service.VotingService;
import com.likeit.web.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AnswerRatingCommand implements Command {

    private final static String USER_ID_FIELD_NAME = "id";
    private final static String QUESTION_ID_FIELD_NAME = "question_id";
    private final static String ANSWER_ID_FIELD_NAME = "answer_id";
    private final static String RATING_FIELD_NAME = "rating";
    private final static String QUESTION_PAGE = "?command=question&id=";
    private final static String ERROR_PAGE = "?command=error&message=";
    private final VotingService votingService = ServiceFactory.getInstance().getVotingService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getSession().getAttribute(USER_ID_FIELD_NAME).toString());
        int questionId = Integer.parseInt(request.getParameter(QUESTION_ID_FIELD_NAME));
        int answerId = Integer.parseInt(request.getParameter(ANSWER_ID_FIELD_NAME));
        int mark = Integer.parseInt(request.getParameter(RATING_FIELD_NAME));
        try {
            votingService.setVote(userId, answerId, mark);
            response.sendRedirect(QUESTION_PAGE + questionId);
        } catch (ServiceException e) {
            response.sendRedirect(ERROR_PAGE + e.getMessage());
        }
    }

}
