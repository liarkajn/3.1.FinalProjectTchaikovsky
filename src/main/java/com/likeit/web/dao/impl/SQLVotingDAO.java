package main.java.com.likeit.web.dao.impl;

import main.java.com.likeit.web.dao.AnswerDAO;
import main.java.com.likeit.web.dao.DAOFactory;
import main.java.com.likeit.web.dao.UserDAO;
import main.java.com.likeit.web.dao.VotingDAO;
import main.java.com.likeit.web.dao.connector.Connector;
import main.java.com.likeit.web.dao.exception.DAOException;
import main.java.com.likeit.web.domain.Vote;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLVotingDAO implements VotingDAO {

    private final static String createVote = "INSERT INTO vote (authorId, answerId, mark) VALUES (?, ?, ?);";
    private final static String readVoteById = "SELECT * FROM vote WHERE id=?";

    @Override
    public void createVote(int authorId, int answerId, int mark) throws DAOException {
        try(Connection connection = Connector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(createVote)) {

            preparedStatement.setInt(1, authorId);
            preparedStatement.setInt(2, answerId);
            preparedStatement.setInt(3, mark);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Problems with database operations. Unable save vote", e);
        }
    }

    @Override
    public Vote readVote(int voteId) throws DAOException {
        Vote vote = null;
        try(Connection connection = Connector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(readVoteById)) {

            preparedStatement.setInt(1, voteId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
                AnswerDAO answerDAO = DAOFactory.getInstance().getAnswerDAO();
                while (resultSet.next()) {
                    vote = new Vote();
                    vote.setId(resultSet.getInt(1));
                    int authorId = resultSet.getInt(2);
                    vote.setAuthor(userDAO.readUser(authorId));
                    int answerId = resultSet.getInt(3);
                    vote.setAnswer(answerDAO.readAnswerById(answerId));
                    vote.setMark(resultSet.getInt(4));
                }
            }

        } catch (SQLException e) {
            throw new DAOException("Problems with database operations. Unable find vote with id " + voteId, e);
        }
        return vote;
    }

}
