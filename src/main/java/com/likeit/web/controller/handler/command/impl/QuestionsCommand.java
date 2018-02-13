package com.likeit.web.controller.handler.command.impl;

import com.likeit.web.controller.handler.command.Command;
import com.likeit.web.service.QuestionService;
import com.likeit.web.service.ServiceFactory;
import com.likeit.web.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.lang.Math.ceil;

public class QuestionsCommand implements Command {

    private final static int questionsPerPage = 1;
    private final static int paginationPagesPerSide = 2;
    private final static String SEARCH_STRING_FIELD_NAME = "search_string";
    private final static String PAGE_NUMBER_FIELD_NAME = "page";
    private final static String QUESTIONS_FIELD_NAME = "questions";
    private final static String CURRENT_PAGE_FIELD_NAME = "currentPage";
    private final static String LAST_PAGE_FIELD_NAME = "lastPage";
    private final static String OFFSET_FIELD_NAME = "offset";
    private final static String QUESTIONS_PAGE = "/WEB-INF/page/questions.jsp";
    private final static String ERROR_PAGE = "?command=error&&message=";
    private final QuestionService questionService = ServiceFactory.getInstance().getQuestionService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int page = 1;
        String searchString = request.getParameter(SEARCH_STRING_FIELD_NAME);
        if (request.getParameter(PAGE_NUMBER_FIELD_NAME) != null) {
            page = Integer.parseInt(request.getParameter(PAGE_NUMBER_FIELD_NAME));
        }
        try {
            if (searchString != null) {
                request.setAttribute(QUESTIONS_FIELD_NAME, questionService.findQuestionsByTopic(questionsPerPage, page ,searchString));
            } else {
                request.setAttribute(QUESTIONS_FIELD_NAME, questionService.findAllQuestions(questionsPerPage, page));
            }
            setPaginationAttribute(request, page, searchString);
            request.getRequestDispatcher(QUESTIONS_PAGE).forward(request, response);
        } catch (ServiceException ex) {
            response.sendRedirect(ERROR_PAGE + ex.getMessage());
        }
    }

    private void setPaginationAttribute(HttpServletRequest request, int page, String searchString) throws ServiceException{
        int questionsCount;
        if (searchString != null) {
            questionsCount = questionService.getQuestionsCount(searchString);
        } else {
            questionsCount = questionService.getQuestionsCount();
        }
        request.setAttribute(CURRENT_PAGE_FIELD_NAME, page);
        int lastPage = (int)ceil((double)questionsCount / questionsPerPage);
        request.setAttribute(LAST_PAGE_FIELD_NAME, lastPage);
        request.setAttribute(OFFSET_FIELD_NAME, paginationPagesPerSide);
    }

}
