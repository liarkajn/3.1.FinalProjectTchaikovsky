package main.java.com.likeit.web.service;

import main.java.com.likeit.web.domain.Question;

import java.util.List;

public interface QuestionService {

    void createQuestion(String topic, String content, String authorLogin);
    List<Question> getAll();

}
