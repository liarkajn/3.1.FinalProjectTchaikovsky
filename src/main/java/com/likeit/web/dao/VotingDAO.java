package main.java.com.likeit.web.dao;

import main.java.com.likeit.web.dao.exception.DAOException;
import main.java.com.likeit.web.domain.Vote;

public interface VotingDAO {

    void createVote(int authorId, int answerId, int mark) throws DAOException;
    Vote readVote(int voteId) throws DAOException;

}
