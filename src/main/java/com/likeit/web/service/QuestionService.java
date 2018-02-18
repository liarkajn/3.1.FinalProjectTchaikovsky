package com.likeit.web.service;

import com.likeit.web.domain.Question;
import com.likeit.web.service.exception.ServiceException;

import java.util.List;

public interface QuestionService {

    void createQuestion(String topic, String content, int authorId) throws ServiceException;
    Question findQuestion(int questionId) throws ServiceException;
    List<Question> findAllQuestions(int limit, int offset) throws ServiceException;
    List<Question> findQuestionsByTopic(int limit, int offset, String searchString) throws ServiceException;
    void editQuestion(int authorId, int id, String topic, String content) throws ServiceException;
    void adminEditQuestion(int adminId, int id, String topic, String content) throws ServiceException;
    int getQuestionsCount() throws ServiceException;
    int getQuestionsCount(String searchString) throws ServiceException;
    void deleteQuestion(int questionId, int authorId) throws ServiceException;
    void adminDeleteQuestion(int questionId, int adminId) throws ServiceException;

}
