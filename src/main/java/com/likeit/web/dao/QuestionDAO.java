package main.java.com.likeit.web.dao;

import main.java.com.likeit.web.dao.exception.DAOException;
import main.java.com.likeit.web.domain.Question;

import java.util.List;

public interface QuestionDAO {

    void createQuestion(String topic, String content, int authorId) throws DAOException;
    Question readQuestion(int id) throws DAOException;
    List<Question> readQuestions() throws DAOException;
    List<Question> readQuestionsBySearchString(String searchString) throws DAOException;
    List<Question> readQuestionsByAuthorId(int authorId) throws DAOException;
    void updateQuestion(Question question) throws DAOException;
    void deleteQuestion(int id) throws DAOException;

}
