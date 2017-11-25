package main.java.com.likeit.web.dao.impl;

import main.java.com.likeit.web.dao.DAOFactory;
import main.java.com.likeit.web.dao.QuestionDAO;
import main.java.com.likeit.web.dao.UserDAO;
import main.java.com.likeit.web.dao.connector.Connector;
import main.java.com.likeit.web.dao.exception.DAOException;
import main.java.com.likeit.web.domain.Question;
import main.java.com.likeit.web.domain.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import static main.java.com.likeit.web.dao.exception.Exceptions.DATABASE_OPERATIONS_EXCEPTION;

public class SQLQuestionDAO implements QuestionDAO {

    private final static String queryAllQuestions = "SELECT * FROM question";
    private final static String saveQuestion = "INSERT INTO question (author_id, topic, content, publish_date) VALUES (?, ?, ?, ?);";

    @Override
    public void saveQuestion(String topic, String content, String authorLogin) throws DAOException {
        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
        int authorId = userDAO.getId(authorLogin);

        try (Connection connection = Connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(saveQuestion)) {

            preparedStatement.setInt(1, authorId);
            preparedStatement.setString(2, topic);
            preparedStatement.setString(3, content);
            preparedStatement.setString(4, LocalDateTime.now().toString());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException(DATABASE_OPERATIONS_EXCEPTION.getMessage(), e);
        }
    }

    @Override
    public List<Question> getQuestions() throws DAOException {
        List<Question> result = new LinkedList<>();
        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
        try (Connection connection = Connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(queryAllQuestions)) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Question question = new Question();
                    int authorId = resultSet.getInt(2);
                    User author = userDAO.getUser(authorId);
                    question.setAuthor(author);
                    question.setTopic(resultSet.getString(3));
                    question.setContent(resultSet.getString(4));
                    question.setPublishDate(resultSet.getTimestamp(5).toLocalDateTime());
                    result.add(question);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(DATABASE_OPERATIONS_EXCEPTION.getMessage(), e);
        }
        return result;
    }

}
