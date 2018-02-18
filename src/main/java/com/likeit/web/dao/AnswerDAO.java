package com.likeit.web.dao;

import com.likeit.web.dao.exception.DAOException;
import com.likeit.web.domain.Answer;

import java.util.List;

public interface AnswerDAO {

    void createAnswer(int authorId, int questionId, String content) throws DAOException;
    Answer readAnswerById(int id) throws DAOException;
    List<Answer> readAnswersByQuestionId(int questionId) throws DAOException;
    List<Answer> readAnswersByQuestionIdAndAuthorId(int questionId, int answerId) throws DAOException;
    void updateAnswer(Answer answer) throws DAOException;
    int readAnswersCount(int authorId) throws DAOException;

}
