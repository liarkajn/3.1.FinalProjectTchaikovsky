package com.likeit.web.dao;

import com.likeit.web.dao.exception.DAOException;
import com.likeit.web.domain.Question;

import java.util.List;

public interface QuestionDAO {

    void createQuestion(String topic, String content, int authorId) throws DAOException;
    Question readQuestion(int id) throws DAOException;
    List<Question> readQuestions(int limit, int offset) throws DAOException;
    List<Question> readQuestionsBySearchString(int limit, int offset, String searchString) throws DAOException;
    void updateQuestion(Question question) throws DAOException;
    int readQuestionsCount() throws DAOException;
    int readQuestionsCount(String searchString) throws DAOException;
    int readQuestionsCount(int authorId) throws DAOException;
    void deleteQuestion(int id) throws DAOException;

}
