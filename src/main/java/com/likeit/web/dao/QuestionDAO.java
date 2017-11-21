package main.java.com.likeit.web.dao;

import main.java.com.likeit.web.dao.exception.DAOException;
import main.java.com.likeit.web.domain.Question;

import java.util.List;

public interface QuestionDAO {

    void saveQuestion(String topic, String content, String authorLogin) throws DAOException;
    List<Question> getQuestions() throws DAOException;

}
