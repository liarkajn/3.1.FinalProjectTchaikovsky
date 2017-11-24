package main.java.com.likeit.web.dao.impl;

import main.java.com.likeit.web.dao.DAOFactory;
import main.java.com.likeit.web.dao.QuestionDAO;
import main.java.com.likeit.web.dao.UserDAO;
import main.java.com.likeit.web.dao.exception.DAOException;
import main.java.com.likeit.web.domain.Question;
import main.java.com.likeit.web.domain.User;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import static main.java.com.likeit.web.dao.exception.Exceptions.*;

public class SQLQuestionDAO implements QuestionDAO {

    private final static String MYSQL_CONFIG = "mysql.properties";
    private final static String URL_PROPERTY = "url";
    private final static String USERNAME_PROPERTY = "username";
    private final static String PASSWORD_PROPERTY = "password";
    private final static String DRIVER_PROPERTY = "driver";

    private final static String queryAllQuestions = "SELECT * FROM question";
    private final static String saveQuestion = "INSERT INTO question (author_id, topic, content, publish_date) VALUES (?, ?, ?, ?);";

    private String DB_URL;
    private String USERNAME;
    private String PASSWORD;
    private String JDBC_DRIVER;

    @Override
    public void saveQuestion(String topic, String content, String authorLogin) throws DAOException {
        prepareDatabase();
        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
        int authorId = userDAO.getId(authorLogin);

        try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
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
        prepareDatabase();
        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
        try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
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

    private void prepareDatabase() throws DAOException {
        Properties properties = getMySQLProperties();
        DB_URL = properties.getProperty(URL_PROPERTY);
        USERNAME = properties.getProperty(USERNAME_PROPERTY);
        PASSWORD = properties.getProperty(PASSWORD_PROPERTY);
        JDBC_DRIVER = properties.getProperty(DRIVER_PROPERTY);
        loadDriver();
    }

    private Properties getMySQLProperties() throws DAOException {
        Properties properties = new Properties();
        URL url = getClass().getClassLoader().getResource(MYSQL_CONFIG);
        if (url == null) {
            throw new DAOException(MISSED_PROPERTY_FILE.getMessage());
        }
        try {
            InputStream inputStream = url.openStream();
            properties.load(inputStream);
        } catch (IOException e) {
            throw new DAOException(UNABLE_OPEN_PROPERTY_FILE.getMessage(), e);
        }
        return properties;
    }

    private void loadDriver() throws DAOException {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new DAOException(MISSED_JDBC_DRIVER.getMessage(), e);
        }
    }

}
