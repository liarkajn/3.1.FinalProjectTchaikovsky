package main.java.com.likeit.web.dao.impl;

import main.java.com.likeit.web.dao.UserDAO;
import main.java.com.likeit.web.dao.exception.DAOException;
import main.java.com.likeit.web.domain.User;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Properties;

import static main.java.com.likeit.web.dao.exception.Exceptions.*;

public class SQLUserDAO implements UserDAO {

    private final static String MYSQL_CONFIG = "mysql.properties";
    private final static String URL_PROPERTY = "url";
    private final static String USERNAME_PROPERTY = "username";
    private final static String PASSWORD_PROPERTY = "password";
    private final static String DRIVER_PROPERTY = "driver";

    private final static String getIdByLogin = "SELECT id FROM user WHERE login=?";
    private final static String getUserById = "SELECT * FROM user WHERE id=?";
    private final static String getUserByLoginAndPassword = "SELECT * FROM user WHERE login=? AND password=?";
    private final static String saveUser = "INSERT INTO user (login, password, email, registration_date) VALUES (?, ?, ?, ?);";

    private String DB_URL;
    private String USERNAME;
    private String PASSWORD;
    private String JDBC_DRIVER;

    public int getId(String login) throws DAOException {
        int id = -1;
        prepareDatabase();
        try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(getIdByLogin)) {

            preparedStatement.setString(1, login);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    id = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(DATABASE_OPERATIONS_EXCEPTION.getMessage(), e);
        }
        return id;
    }

    public User getUser(int id) throws DAOException {
        User user = new User();
        prepareDatabase();
        try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(getUserById)) {

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    user.setLogin(resultSet.getString(2));
                    user.setEmail(resultSet.getString(4));
                    user.setRegistrationDate(resultSet.getTimestamp(5).toLocalDateTime());
                    user.setName(resultSet.getString(6));
                    user.setSurname(resultSet.getString(7));
                    user.setRating(resultSet.getShort(8));
                }
            }
        } catch (SQLException e) {
            throw new DAOException(DATABASE_OPERATIONS_EXCEPTION.getMessage(), e);
        }

        return user;
    }

    public User getUser(String login, String password) throws DAOException {
        User user = new User();
        prepareDatabase();
        try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(getUserByLoginAndPassword)) {

            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    user.setLogin(resultSet.getString(2));
                    user.setEmail(resultSet.getString(4));
                    user.setRegistrationDate(resultSet.getTimestamp(5).toLocalDateTime());
                    user.setName(resultSet.getString(6));
                    user.setSurname(resultSet.getString(7));
                    user.setRating(resultSet.getShort(8));
                }
            }

        } catch (SQLException e) {
            throw new DAOException(DATABASE_OPERATIONS_EXCEPTION.getMessage(), e);
        }
        return user;
    }

    public void saveUser(String login, String password, String email) throws DAOException {
        User user;
        user = getUser(login, password);
        if (user.getLogin() == null || user.getLogin().isEmpty()) {
            try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                 PreparedStatement preparedStatement = connection.prepareStatement(saveUser)) {

                preparedStatement.setString(1, login);
                preparedStatement.setString(2, password);
                preparedStatement.setString(3, email);
                preparedStatement.setString(4, LocalDateTime.now().toString());

                preparedStatement.executeUpdate();

            } catch (SQLException e) {
                throw new DAOException(DATABASE_OPERATIONS_EXCEPTION.getMessage(), e);
            }
        }
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
