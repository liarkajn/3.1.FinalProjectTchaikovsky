package main.java.com.likeit.web.dao.impl;

import main.java.com.likeit.web.dao.AnswerDAO;
import main.java.com.likeit.web.dao.DAOFactory;
import main.java.com.likeit.web.dao.QuestionDAO;
import main.java.com.likeit.web.dao.UserDAO;
import main.java.com.likeit.web.dao.connector.Connector;
import main.java.com.likeit.web.dao.exception.DAOException;
import main.java.com.likeit.web.domain.Answer;
import main.java.com.likeit.web.domain.Question;
import main.java.com.likeit.web.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class SQLAnswerDAO implements AnswerDAO {

    private final static String createAnswer = "INSERT INTO answer (author_id, question_id, content, publish_date) VALUES (?, ?, ?, ?)";
    private final static String readAnswerById = "SELECT * FROM answer WHERE id=?";
    private final static String readAnswersByQuestionId = "SELECT * FROM answer WHERE question_id=?";
    private final static String updateAnswerById = "UPDATE answer SET content=? WHERE id=?";

    public SQLAnswerDAO() {

    }

    @Override
    public void createAnswer(int authorId, int questionId, String content) throws DAOException {
        try (Connection connection = Connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(createAnswer)) {

            preparedStatement.setInt(1, authorId);
            preparedStatement.setInt(2, questionId);
            preparedStatement.setString(3, content);
            preparedStatement.setString(4, LocalDateTime.now().toString());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Problems with database operations. Unable save user", e);
        }
    }

    @Override
    public Answer readAnswerById(int id) throws DAOException {
        Answer answer = null;
        try (Connection connection = Connector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(readAnswerById)) {

            preparedStatement.setInt(1, id);

            UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
            QuestionDAO questionDAO = DAOFactory.getInstance().getQuestionDAO();

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    answer = new Answer();
                    answer.setId(resultSet.getInt(1));
                    User user =userDAO.readUser(resultSet.getInt(2));
                    answer.setAuthor(user);
                    Question question = questionDAO.readQuestion(resultSet.getInt(3));
                    answer.setQuestion(question);
                    question.setContent(resultSet.getString(4));
                    question.setPublishDate(resultSet.getTimestamp(5).toLocalDateTime());
                }
            }

        } catch (SQLException e) {
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
        try (Connection connection = Connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(readAnswersByQuestionId)) {

            preparedStatement.setInt(1, questionId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Answer answer = new Answer();
                    answer.setId(resultSet.getInt(1));
                    User author = userDAO.readUser(resultSet.getInt(2));
                    answer.setAuthor(author);
                    answer.setQuestion(question);
                    answer.setContent(resultSet.getString(4));
                    answer.setPublishDate(resultSet.getTimestamp(5).toLocalDateTime());
                    answers.add(answer);
                }
            }

        } catch (SQLException e) {
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
        try (Connection connection = Connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateAnswerById)) {

            preparedStatement.setString(1, answer.getContent());
            preparedStatement.setInt(2, answer.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Problems with database operations. Unable update answer with id : " + answer.getId(), e);
        }
    }

    @Override
    public void deleteAnswer(int id) throws DAOException {
    }

}
