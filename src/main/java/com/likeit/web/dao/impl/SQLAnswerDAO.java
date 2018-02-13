package com.likeit.web.dao.impl;

import com.likeit.web.dao.*;
import com.likeit.web.dao.connector.ConnectionPool;
import com.likeit.web.dao.connector.ConnectionPoolException;
import com.likeit.web.dao.exception.DAOException;
import com.likeit.web.domain.Answer;
import com.likeit.web.domain.Question;
import com.likeit.web.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class SQLAnswerDAO implements AnswerDAO {

    private final static String createAnswer = "INSERT INTO answer (author_id, question_id, content, creation_time) VALUES (?, ?, ?, ?)";
    private final static String readAnswerById = "SELECT * FROM answer WHERE id=?";
    private final static String readAnswersByQuestionId = "SELECT * FROM answer WHERE question_id=?";
    private final static String updateAnswerById = "UPDATE answer SET content=? WHERE id=?";
    private final static String readAnswersCountByAuthorId = "SELECT COUNT(*) FROM answer WHERE author_id=?";

    public SQLAnswerDAO() {

    }

    @Override
    public void createAnswer(int authorId, int questionId, String content) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(createAnswer)) {

            preparedStatement.setInt(1, authorId);
            preparedStatement.setInt(2, questionId);
            preparedStatement.setString(3, content);
            preparedStatement.setString(4, LocalDateTime.now().toString());

            preparedStatement.executeUpdate();

        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Problems with database operations. Unable save user", e);
        }
    }

    @Override
    public Answer readAnswerById(int id) throws DAOException {
        Answer answer = null;
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(readAnswerById)) {

            preparedStatement.setInt(1, id);

            UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
            QuestionDAO questionDAO = DAOFactory.getInstance().getQuestionDAO();

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                VotingDAO votingDAO = DAOFactory.getInstance().getVotingDAO();
                while (resultSet.next()) {
                    answer = new Answer();
                    answer.setId(resultSet.getInt(1));
                    answer.setContent(resultSet.getString(2));
                    User user =userDAO.readUser(resultSet.getInt(3));
                    answer.setAuthor(user);
                    answer.setPublishDate(resultSet.getTimestamp(4).toLocalDateTime());
                    Question question = questionDAO.readQuestion(resultSet.getInt(5));
                    answer.setQuestion(question);
                    answer.setVote(votingDAO.readVoteByAnswer(answer.getId()));
                }
            }

        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Problems with database operations. Unable find answer with id : " + id, e);
        }
        return answer;
    }

    @Override
    public List<Answer> readAnswersByQuestionId(int questionId) throws DAOException {
        List<Answer> answers = new LinkedList<>();
        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
        QuestionDAO questionDAO = DAOFactory.getInstance().getQuestionDAO();
        Question question = questionDAO.readQuestion(questionId);
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(readAnswersByQuestionId)) {

            preparedStatement.setInt(1, questionId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                VotingDAO votingDAO = DAOFactory.getInstance().getVotingDAO();
                while (resultSet.next()) {
                    Answer answer = new Answer();
                    answer.setId(resultSet.getInt(1));
                    User author = userDAO.readUser(resultSet.getInt(3));
                    answer.setAuthor(author);
                    answer.setQuestion(question);
                    answer.setContent(resultSet.getString(2));
                    answer.setVote(votingDAO.readVoteByAnswer(answer.getId()));
                    answer.setPublishDate(resultSet.getTimestamp(4).toLocalDateTime());
                    answers.add(answer);
                }
            }

        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Problems with database operations. Unable read find answers for question " + questionId, e);
        }
        return answers;
    }

    @Override
    public List<Answer> readAnswersByAuthorId(int authorId) {
        return null;
    }

    @Override
    public void updateAnswer(Answer answer) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateAnswerById)) {

            preparedStatement.setString(1, answer.getContent());
            preparedStatement.setInt(2, answer.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Problems with database operations. Unable update answer with id : " + answer.getId(), e);
        }
    }

    @Override
    public int readAnswersCount(int authorId) throws DAOException {
        int count = 0;
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(readAnswersCountByAuthorId)) {

            preparedStatement.setInt(1, authorId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    count = resultSet.getInt(1);
                }
            }

        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Problems with database operations. Unable read answers count", e);
        }
        return count;
    }

    @Override
    public void deleteAnswer(int id) throws DAOException {
    }

}
