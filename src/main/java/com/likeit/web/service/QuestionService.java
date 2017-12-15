package main.java.com.likeit.web.service;

import main.java.com.likeit.web.domain.Question;
import main.java.com.likeit.web.service.exception.ServiceException;

import java.util.List;

public interface QuestionService {

    void createQuestion(String topic, String content, String authorLogin) throws ServiceException;
    List<Question> getAll() throws ServiceException;
    Question findQuestion(int id) throws ServiceException;

}
