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
import java.util.LinkedList;
import java.util.List;

public class SQLUserDAO implements UserDAO {

    private final static String createUser = "INSERT INTO user (login, password, email, registration_date, role) VALUES (?, ?, ?, ?, ?);";
    private final static String getIdByLogin = "SELECT id FROM user WHERE login=?";
    private final static String readUserById = "SELECT * FROM user WHERE id=?";
    private final static String readUserByLoginAndPassword = "SELECT * FROM user WHERE login=? AND password=?";
    private final static String readUsersBySearchString = "SELECT * FROM user WHERE login LIKE ?";

    public SQLUserDAO() {

    }

    @Override
    public void createUser(String login, String password, String email) throws DAOException {
        User user;
        user = readUser(login, password);
        if (user != null) {
            throw new DAOException("User already exists");
        }
        try (Connection connection = Connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(createUser)) {

            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, LocalDateTime.now().toString());
            preparedStatement.setInt(5, 2);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Problems with database operations. Unable save user", e);
        }
    }

    @Override
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
            throw new DAOException("Problems with database operations. Unable find user by login : " + login, e);
        }
        return id;
    }

    @Override
    public User readUser(int id) throws DAOException {
        User user;
        try (Connection connection = Connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(readUserById)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                user = extractUser(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException("Problems with database operations. Unable find user by id : " + id, e);
        }
        return user;
    }

    @Override
    public User readUser(String login, String password) throws DAOException {
        User user;
        try (Connection connection = Connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(readUserByLoginAndPassword)) {

            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                user = extractUser(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException("Problems with database operations. Unable find user with login : " + login, e);
        }
        return user;
    }

    @Override
    public List<User> readUsers(String searchString) throws DAOException {
        List<User> result = new LinkedList<>();
        try (Connection connection = Connector.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(readUsersBySearchString)) {
            preparedStatement.setString(1, "%" + searchString + "%") ;
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getInt(1));
                    user.setLogin(resultSet.getString(2));
                    user.setEmail(resultSet.getString(4));
                    user.setRegistrationDate(resultSet.getTimestamp(5).toLocalDateTime());
                    user.setName(resultSet.getString(6));
                    user.setSurname(resultSet.getString(7));
                    user.setRole(resultSet.getShort(8));
                    result.add(user);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Problems with database operations. Unable search users", e);
        }
        return result;
    }

    private User extractUser(ResultSet resultSet) throws SQLException {
        User user = null;
        while (resultSet.next()) {
            user = new User();
            user.setId(resultSet.getInt(1));
            user.setLogin(resultSet.getString(2));
            user.setEmail(resultSet.getString(4));
            user.setRegistrationDate(resultSet.getTimestamp(5).toLocalDateTime());
            user.setName(resultSet.getString(6));
            user.setSurname(resultSet.getString(7));
            user.setRole(resultSet.getShort(8));
        }
        return user;
    }

    @Override
    public void updateUser(User user) throws DAOException {

    }

    @Override
    public void deleteUser(int id) throws DAOException {

    }

}
