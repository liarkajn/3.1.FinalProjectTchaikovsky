package main.java.com.likeit.web.service;

import main.java.com.likeit.web.domain.Question;
import main.java.com.likeit.web.service.exception.ServiceException;

import java.util.List;

public interface QuestionService {

    void createQuestion(String topic, String content, int authorId) throws ServiceException;
    Question findQuestion(int id) throws ServiceException;
    List<Question> findAllQuestions() throws ServiceException;
    List<Question> findQuestionsByTopic(String searchString) throws ServiceException;
    void editQuestion(int authorId, int id, String topic, String content) throws ServiceException;

}
