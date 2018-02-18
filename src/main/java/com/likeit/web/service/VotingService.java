package com.likeit.web.service;

import com.likeit.web.service.exception.ServiceException;

public interface VotingService {

    void setVote(int authorId, int answerId, int mark) throws ServiceException;

}
