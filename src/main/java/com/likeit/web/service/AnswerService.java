package com.likeit.web.service;

import com.likeit.web.domain.Answer;
import com.likeit.web.service.exception.ServiceException;

import java.util.List;

public interface AnswerService {

    void saveAnswer(int authorId, int questionId, String content) throws ServiceException;
    Answer findAnswer(int id) throws ServiceException;
    List<Answer> findAnswersByQuestion(int questionId) throws ServiceException;
    List<Answer> findAnswersByAuthor(int authorId) throws ServiceException;
    Answer editAnswer(int authorId, int answerId, String content) throws ServiceException;

}
