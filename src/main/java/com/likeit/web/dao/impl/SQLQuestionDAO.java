package com.likeit.web.dao.impl;

import com.likeit.web.dao.DAOFactory;
import com.likeit.web.dao.QuestionDAO;
import com.likeit.web.dao.UserDAO;
import com.likeit.web.dao.connector.ConnectionPool;
import com.likeit.web.dao.connector.ConnectionPoolException;
import com.likeit.web.dao.exception.DAOException;
import com.likeit.web.domain.Question;
import com.likeit.web.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class SQLQuestionDAO implements QuestionDAO {

    private final static String createQuestion = "INSERT INTO question (author_id, topic, content, creation_time) VALUES (?, ?, ?, ?);";
    private final static String updateQuestionById = "UPDATE question SET topic=?, content=? WHERE id=?";
    private final static String readQuestionById = "SELECT * FROM question WHERE id=?";
    private final static String readQuestions = "SELECT * FROM question ORDER BY creation_time DESC";
    private final static String readQuestionsBySearchString = "SELECT * FROM question WHERE topic LIKE ? ORDER BY creation_time DESC";
    private final static String readQuestionsByUserId = "SELECT * FROM question WHERE author_id=?";

    @Override
    public void createQuestion(String topic, String content, int authorId) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(createQuestion)) {

            preparedStatement.setInt(1, authorId);
            preparedStatement.setString(2, topic);
            preparedStatement.setString(3, content);
            preparedStatement.setString(4, LocalDateTime.now().toString());

            preparedStatement.executeUpdate();

        } catch (SQLException | ConnectionPoolException e) {
//        } catch (SQLException e) {
            throw new DAOException("Problems with database operations. Unable save question", e);
        }
    }

    @Override
    public Question readQuestion(int id) throws DAOException {
        Question question = null;
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(readQuestionById)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    question = new Question();
                    int authorId = resultSet.getInt(4);
                    User author = DAOFactory.getInstance().getUserDAO().readUser(authorId);
                    question.setId(resultSet.getInt(1));
                    question.setAuthor(author);
                    question.setTopic(resultSet.getString(2));
                    question.setContent(resultSet.getString(3));
                    question.setPublishDate(resultSet.getTimestamp(5).toLocalDateTime());
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Problems with database operations. Unable read question with id " + id, e);
        }
        return question;
    }

    @Override
    public List<Question> readQuestions() throws DAOException {
        List<Question> result = new LinkedList<>();
        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(readQuestions)) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Question question = new Question();
                    int authorId = resultSet.getInt(4);
                    User author = userDAO.readUser(authorId);
                    question.setId(resultSet.getInt(1));
                    question.setAuthor(author);
                    question.setTopic(resultSet.getString(2));
                    question.setContent(resultSet.getString(3));
                    question.setPublishDate(resultSet.getTimestamp(5).toLocalDateTime());
                    result.add(question);
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Problems with database operations. Unable read question list", e);
        }
        return result;
    }

    @Override
    public List<Question> readQuestionsBySearchString(String searchString) throws DAOException {
        List<Question> result;
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(readQuestionsBySearchString)) {

            preparedStatement.setString(1, "%" + searchString + "%");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                result = extractQuestions(resultSet);
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Problems with database operations. Unable search questions", e);
        }
        return result;
    }

    @Override
    public List<Question> readQuestionsByAuthorId(int authorId) throws DAOException {
        List<Question> result;
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(readQuestionsByUserId)) {

            preparedStatement.setInt(1, authorId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                result = extractQuestions(resultSet);
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Problems with database operations. Unable find questions by author id : " + authorId, e);
        }
        return result;
    }

    private List<Question> extractQuestions(ResultSet resultSet) throws SQLException, DAOException {
        List<Question> result = new LinkedList<>();
        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
        while (resultSet.next()) {
            Question question = new Question();
            int authorId = resultSet.getInt(4);
            User author = userDAO.readUser(authorId);
            question.setId(resultSet.getInt(1));
            question.setAuthor(author);
            question.setTopic(resultSet.getString(2));
            question.setContent(resultSet.getString(3));
            question.setPublishDate(resultSet.getTimestamp(5).toLocalDateTime());
            result.add(question);
        }
        return result;
    }

    @Override
    public void updateQuestion(Question question) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuestionById)) {

            preparedStatement.setString(1, question.getTopic());
            preparedStatement.setString(2, question.getContent());
            preparedStatement.setInt(3, question.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Problems with database operations. Unable update question with id : " + question.getId(), e);
        }
    }

    @Override
    public void deleteQuestion(int id) throws DAOException {

    }

}
