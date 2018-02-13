package com.likeit.web.dao.impl;

import com.likeit.web.dao.AnswerDAO;
import com.likeit.web.dao.DAOFactory;
import com.likeit.web.dao.UserDAO;
import com.likeit.web.dao.VotingDAO;
import com.likeit.web.dao.connector.ConnectionPool;
import com.likeit.web.dao.connector.ConnectionPoolException;
import com.likeit.web.dao.exception.DAOException;
import com.likeit.web.domain.Vote;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLVotingDAO implements VotingDAO {

    private final static String createVote = "INSERT INTO mark (user_id, answer_id, value) VALUES (?, ?, ?);";
    private final static String readVoteById = "SELECT * FROM mark WHERE id=?";
    private final static String readVoteByAnswerId = "SELECT * FROM mark WHERE answer_id=?";
    private final static String updateVote = "UPDATE mark SET value=? WHERE id=?";
    private final static String readAverageMarkByAuthorId = "SELECT ROUND(AVG(value), 2) FROM questionnaire.answer as answer INNER JOIN questionnaire.mark ON answer.id=answer_id WHERE author_id=?";

    @Override
    public void createVote(int authorId, int answerId, int mark) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(createVote)) {

            preparedStatement.setInt(1, authorId);
            preparedStatement.setInt(2, answerId);
            preparedStatement.setInt(3, mark);

            preparedStatement.executeUpdate();

        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Problems with database operations. Unable save vote", e);
        }
    }

    @Override
    public Vote readVote(int voteId) throws DAOException {
        Vote vote = null;
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(readVoteById)) {

            preparedStatement.setInt(1, voteId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
//                AnswerDAO answerDAO = DAOFactory.getInstance().getAnswerDAO();
                while (resultSet.next()) {
                    vote = new Vote();
                    vote.setId(resultSet.getInt(1));
                    int authorId = resultSet.getInt(2);
                    vote.setAuthor(userDAO.readUser(authorId));
//                    int answerId = resultSet.getInt(3);
//                    vote.setAnswer(answerDAO.readAnswerById(answerId));
                    vote.setMark(resultSet.getInt(4));
                }
            }

        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Problems with database operations. Unable find vote with id " + voteId, e);
        }
        return vote;
    }

    @Override
    public Vote readVoteByAnswer(int answerId) throws DAOException {
        Vote vote = null;
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(readVoteByAnswerId)) {

            preparedStatement.setInt(1, answerId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
//                AnswerDAO answerDAO = DAOFactory.getInstance().getAnswerDAO();
                while (resultSet.next()) {
                    vote = new Vote();
                    vote.setId(resultSet.getInt(1));
                    int authorId = resultSet.getInt(2);
                    vote.setAuthor(userDAO.readUser(authorId));
//                    vote.setAnswer(answerDAO.readAnswerById( resultSet.getInt(3)));
                    vote.setMark(resultSet.getInt(4));
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Problems with database operations. Unable save vote", e);
        }
        return vote;
    }

    @Override
    public void updateVote(Vote vote) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateVote)) {

            preparedStatement.setInt(1, vote.getMark());
            preparedStatement.setInt(2, vote.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Problems with database operations. Unable save vote", e);
        }
    }

    @Override
    public double readAverageMark(int authorId) throws DAOException {
        double averageMark = 0;
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(readAverageMarkByAuthorId)) {

            preparedStatement.setInt(1, authorId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    averageMark = resultSet.getDouble(1);
                }
            }

        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Problems with database operations. Unable read average mark", e);
        }
        return averageMark;
    }

}
