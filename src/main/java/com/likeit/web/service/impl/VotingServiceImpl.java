package com.likeit.web.service.impl;

import com.likeit.web.dao.DAOFactory;
import com.likeit.web.dao.VotingDAO;
import com.likeit.web.dao.exception.DAOException;
import com.likeit.web.domain.Vote;
import com.likeit.web.service.VotingService;
import com.likeit.web.service.exception.ServiceException;
import com.likeit.web.service.impl.validation.VotingValidation;

public class VotingServiceImpl implements VotingService {

    private final VotingValidation votingValidation = new VotingValidation();

    @Override
    public void setVote(int authorId, int answerId, int mark) throws ServiceException {

        votingValidation.validateCreation(authorId, answerId, mark);
        VotingDAO votingDAO = DAOFactory.getInstance().getVotingDAO();

        try {
            votingDAO.createVote(authorId, answerId, mark);
        } catch (DAOException ex) {
            throw new ServiceException();
        }
    }

    @Override
    public Vote deleteVote(int id) throws ServiceException {
        return null;
    }

}
