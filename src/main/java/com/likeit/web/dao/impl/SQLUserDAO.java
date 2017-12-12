package main.java.com.likeit.web.dao.impl;

import main.java.com.likeit.web.dao.UserDAO;
import main.java.com.likeit.web.dao.connector.Connector;
import main.java.com.likeit.web.dao.exception.DAOException;
import main.java.com.likeit.web.domain.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import static main.java.com.likeit.web.dao.exception.Exceptions.DATABASE_OPERATIONS_EXCEPTION;
import static main.java.com.likeit.web.dao.exception.Exceptions.USER_ALREADY_EXISTS;

public class SQLUserDAO implements UserDAO {

    private final static String getIdByLogin = "SELECT id FROM user WHERE login=?";
    private final static String getUserById = "SELECT * FROM user WHERE id=?";
    private final static String getUserByLoginAndPassword = "SELECT * FROM user WHERE login=? AND password=?";
    private final static String saveUser = "INSERT INTO user (login, password, email, registration_date) VALUES (?, ?, ?, ?);";


    public SQLUserDAO() {
    }

    public int getId(String login) throws DAOException {
        int id = -1;
        try (Connection connection = Connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getIdByLogin)) {

            preparedStatement.setString(1, login);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    id = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(DATABASE_OPERATIONS_EXCEPTION.getMessage(), e);// в логах и исключениях используется просто сообщене - строка
            // их не надо именовать ,эти сообщения локальны
        }
        return id;
    }

    public User getUser(int id) throws DAOException {
        User user = null;
        try (Connection connection = Connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getUserById)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    user = new User();
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
        User user = null;
        try (Connection connection = Connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getUserByLoginAndPassword)) {

            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    user = new User();
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
        if (user != null) {
            throw new DAOException(USER_ALREADY_EXISTS.getMessage());
        }
        try (Connection connection = Connector.getConnection();
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
