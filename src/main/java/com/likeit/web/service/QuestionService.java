package com.likeit.web.service;

import com.likeit.web.domain.Question;
import com.likeit.web.service.exception.ServiceException;

import java.util.List;

public interface QuestionService {

    void createQuestion(String topic, String content, int authorId) throws ServiceException;
    Question findQuestion(int id) throws ServiceException;
    List<Question> findAllQuestions(int limit, int offset) throws ServiceException;
    List<Question> findQuestionsByTopic(int limit, int offset, String searchString) throws ServiceException;
    void editQuestion(Question question) throws ServiceException;
    void editQuestion(int authorId, int id, String topic, String content) throws ServiceException;
    int getQuestionsCount() throws ServiceException;
    int getQuestionsCount(String searchString) throws ServiceException;
    int getQuestionsCount(int authorId) throws ServiceException;
    void deleteQuestion(int id) throws ServiceException;

}
