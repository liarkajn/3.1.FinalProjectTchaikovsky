package com.likeit.web.dao;

import com.likeit.web.dao.exception.DAOException;
import com.likeit.web.domain.Answer;

import java.util.List;

public interface AnswerDAO {

    void createAnswer(int authorId, int questionId, String content) throws DAOException;
    Answer readAnswerById(int id) throws DAOException;
    List<Answer> readAnswersByQuestionId(int questionId) throws DAOException;
    List<Answer> readAnswersByAuthorId(int authorId) throws DAOException;
    void updateAnswer(Answer answer) throws DAOException;
    void deleteAnswer(int id) throws DAOException;

}
