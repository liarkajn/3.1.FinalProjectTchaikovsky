package main.java.com.likeit.web.dao;

import main.java.com.likeit.web.domain.Answer;

import java.util.List;

public interface AnswerDAO {

    Answer saveAnswer(Answer answer);
    List<Answer> getAnswers();

}
