package com.likeit.web.dao;

import com.likeit.web.dao.exception.DAOException;
import com.likeit.web.domain.Vote;

public interface VotingDAO {

    void createVote(int authorId, int answerId, int mark) throws DAOException;
    Vote readVoteByAnswer(int answerId) throws DAOException;
    void updateVote(Vote vote) throws DAOException;
    double readAverageMark(int authorId) throws DAOException;

}
