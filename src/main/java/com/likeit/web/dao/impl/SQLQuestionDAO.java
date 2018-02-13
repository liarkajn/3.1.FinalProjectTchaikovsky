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
    private final static String readQuestions = "SELECT * FROM question ORDER BY creation_time DESC LIMIT ? OFFSET ?";
    private final static String readQuestionsBySearchString = "SELECT * FROM question WHERE topic LIKE ? ORDER BY creation_time DESC LIMIT ? OFFSET ?";
    private final static String readQuestionsByUserId = "SELECT * FROM question WHERE author_id=? ORDER BY creation_time DESC LIMIT ? OFFSET ?";
    private final static String readQuestionsCount = "SELECT COUNT(*) FROM question";
    private final static String readQuestionsCountBySearchString = "SELECT COUNT(*) FROM question WHERE topic LIKE ?";
    private final static String readQuestionsCountByAuthorId = "SELECT COUNT(*) FROM question WHERE author_id=?;";
    private final static String deleteQuestionById = "DELETE FROM question WHERE id=?";

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
    public List<Question> readQuestions(int limit, int offset) throws DAOException {
        List<Question> result = new LinkedList<>();
        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(readQuestions)) {

            preparedStatement.setInt(1, limit);
            preparedStatement.setInt(2, offset);

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
    public List<Question> readQuestionsBySearchString(int limit, int offset, String searchString) throws DAOException {
        List<Question> result;
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(readQuestionsBySearchString)) {

            preparedStatement.setString(1, "%" + searchString + "%");
            preparedStatement.setInt(2, limit);
            preparedStatement.setInt(3, offset);

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
            preparedStatement.setInt(2, 7);
            preparedStatement.setInt(3, 0);

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
    public int readQuestionsCount() throws DAOException {
        int count = 0;
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(readQuestionsCount)) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    count = resultSet.getInt(1);
                }
            }

        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Problems with database operations. Unable read questions count", e);
        }
        return count;
    }

    @Override
    public int readQuestionsCount(String searchString) throws DAOException {
        int count = 0;
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(readQuestionsCountBySearchString)) {

            preparedStatement.setString(1, '%' + searchString + '%');

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    count = resultSet.getInt(1);
                }
            }

        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Problems with database operations. Unable read questions count", e);
        }
        return count;
    }

    @Override
    public int readQuestionsCount(int authorId) throws DAOException {
        int count = 0;
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(readQuestionsCountByAuthorId)) {

            preparedStatement.setInt(1, authorId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    count = resultSet.getInt(1);
                }
            }

        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Problems with database operations. Unable read questions count", e);
        }
        return count;
    }

    @Override
    public void deleteQuestion(int id) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuestionById)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Problems with database operations. Unable delete question with id " + id, e);
        }
    }

}
