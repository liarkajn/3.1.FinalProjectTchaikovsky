package main.java.com.likeit.web.service.impl;

import main.java.com.likeit.web.dao.DAOFactory;
import main.java.com.likeit.web.dao.VotingDAO;
import main.java.com.likeit.web.dao.exception.DAOException;
import main.java.com.likeit.web.domain.Vote;
import main.java.com.likeit.web.service.VotingService;
import main.java.com.likeit.web.service.exception.ServiceException;
import main.java.com.likeit.web.service.impl.validation.VotingValidation;

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
