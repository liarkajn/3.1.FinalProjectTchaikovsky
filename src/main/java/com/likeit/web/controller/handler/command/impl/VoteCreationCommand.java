package com.likeit.web.controller.handler.command.impl;

import com.likeit.web.controller.handler.command.Command;
import com.likeit.web.service.ServiceFactory;
import com.likeit.web.service.VotingService;
import com.likeit.web.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class VoteCreationCommand implements Command {

    private final static String USER_ID_SESSION_FIELD_NAME = "id";
    private final static String QUESTION_ID_FIELD_NAME = "question_id";
    private final static String ANSWER_ID_FIELD_NAME = "answer_id";
    private final static String VOTE_FIELD_NAME = "vote";
    private final static String QUESTION_PAGE = "?command=question&&id=";
    private final static String ERROR_PAGE = "?command=error&&message=";
    private final VotingService votingService = ServiceFactory.getInstance().getVotingService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int authorId = Integer.parseInt(request.getSession().getAttribute(USER_ID_SESSION_FIELD_NAME).toString());
        String questionId = request.getParameter(QUESTION_ID_FIELD_NAME);
        int answerId = Integer.parseInt(request.getParameter(ANSWER_ID_FIELD_NAME));
        int mark = Integer.parseInt(request.getParameter(VOTE_FIELD_NAME));
        try {
            votingService.setVote(authorId, answerId, mark);
            response.sendRedirect(QUESTION_PAGE + questionId);
        } catch (ServiceException e) {
            response.sendRedirect(ERROR_PAGE + e.getMessage());
        }
    }

}
