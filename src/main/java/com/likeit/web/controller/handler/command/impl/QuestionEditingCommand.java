package com.likeit.web.controller.handler.command.impl;

import com.likeit.web.controller.handler.command.Command;
import com.likeit.web.domain.Question;
import com.likeit.web.service.QuestionService;
import com.likeit.web.service.ServiceFactory;
import com.likeit.web.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class QuestionEditingCommand implements Command {

    private final static String QUESTION_ID_FIELD_NAME = "question_id";
    private final static String QUESTION_TOPIC_FIELD_NAME = "topic";
    private final static String QUESTION_CONTENT_FIELD_NAME = "content";
    private final static String QUESTION_PAGE = "?command=question&id=";
    private final static String ERROR_PAGE = "?command=error&message=";
    private final QuestionService questionService = ServiceFactory.getInstance().getQuestionService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String questionId = request.getParameter(QUESTION_ID_FIELD_NAME);
        String questionTopic = request.getParameter(QUESTION_TOPIC_FIELD_NAME);
        String questionContent = request.getParameter(QUESTION_CONTENT_FIELD_NAME);
        Question question = new Question();
        question.setId(Integer.parseInt(questionId));
        question.setTopic(questionTopic);
        question.setContent(questionContent);
        try {
            questionService.editQuestion(question);
            response.sendRedirect(QUESTION_PAGE + questionId);
        } catch (ServiceException ex) {
            response.sendRedirect(ERROR_PAGE + ex.getMessage());
        }
    }

}
