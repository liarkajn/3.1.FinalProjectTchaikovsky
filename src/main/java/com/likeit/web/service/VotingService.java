package main.java.com.likeit.web.service;

import main.java.com.likeit.web.domain.Vote;
import main.java.com.likeit.web.service.exception.ServiceException;

public interface VotingService {

    void setVote(int authorId, int answerId, int mark) throws ServiceException;
    Vote deleteVote(int id) throws ServiceException;

}
