package com.likeit.web.dao.impl;

import com.likeit.web.dao.*;
import com.likeit.web.dao.connector.ConnectionPool;
import com.likeit.web.dao.connector.ConnectionPoolException;
import com.likeit.web.dao.exception.DAOException;
import com.likeit.web.domain.Gender;
import com.likeit.web.domain.Role;
import com.likeit.web.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class SQLUserDAO implements UserDAO {

    private final static String createUser = "INSERT INTO user (login, email, password, gender, registration_time) VALUES (?, ?, ?, ?, ?);";
    private final static String getIdByLogin = "SELECT id FROM user WHERE login=?";
    private final static String readUserById = "SELECT * FROM user WHERE id=?";
    private final static String readUserByLogin = "SELECT * FROM user WHERE login=?";
    private final static String readUserByLoginAndPassword = "SELECT * FROM user WHERE login=? AND password=?";
    private final static String readUserByEmail = "SELECT * FROM user WHERE email=?";
    private final static String readUsers = "SELECT * FROM user LIMIT ? OFFSET ?";
    private final static String readUsersCount = "SELECT COUNT(*) FROM user";
    private final static String updateUser = "UPDATE user SET login=?, email=?, name=?, surname=?, bio=?, role=?, gender=? WHERE id=?";

    public SQLUserDAO() {

    }

    @Override
    public void createUser(String login, String email, String password, Gender gender) throws DAOException {
        User user;
        user = readUser(login, password);
        if (user != null) {
            throw new DAOException("User already exists");
        }
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(createUser)) {

            preparedStatement.setString(1, login);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, gender.toString());
            preparedStatement.setString(5, LocalDateTime.now().toString());

            preparedStatement.executeUpdate();

        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Problems with database operations. Unable save user", e);
        }
    }

    @Override
    public int getId(String login) throws DAOException {
        int id = -1;
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getIdByLogin)) {

            preparedStatement.setString(1, login);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    id = resultSet.getInt(1);
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Problems with database operations. Unable find user by login : " + login, e);
        }
        return id;
    }

    @Override
    public User readUser(int id) throws DAOException {
        User user;
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(readUserById)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                user = extractUser(resultSet);
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Problems with database operations. Unable find user by id : " + id, e);
        }
        return user;
    }

    @Override
    public User readUser(String login, String password) throws DAOException {
        User user;
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(readUserByLoginAndPassword)) {

            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                user = extractUser(resultSet);
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException("Problems with connection pool.", e);
        } catch (SQLException e) {
            throw new DAOException("Problems with database operations. Unable find user with login : " + login, e);
        }
        return user;
    }

    @Override
    public User readUserByLogin(String login) throws DAOException {
        User user;
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(readUserByLogin)) {

            preparedStatement.setString(1, login);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                user = extractUser(resultSet);
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException("Problems with connection pool.", e);
        } catch (SQLException e) {
            throw new DAOException("Problems with database operations. Unable find user with login : " + login, e);
        }
        return user;
    }

    @Override
    public User readUserByEmail(String email) throws DAOException {
        User user;
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(readUserByEmail)) {

            preparedStatement.setString(1, email);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                user = extractUser(resultSet);
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException("Problems with connection pool.", e);
        } catch (SQLException e) {
            throw new DAOException("Problems with database operations. Unable find user with email : " + email, e);
        }
        return user;
    }

    @Override
    public List<User> readUsers(int limit, int offset) throws DAOException {
        List<User> result = new LinkedList<>();
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(readUsers)) {

            preparedStatement.setInt(1, limit);
            preparedStatement.setInt(2, offset);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                QuestionDAO questionDAO = DAOFactory.getInstance().getQuestionDAO();
                AnswerDAO answerDAO = DAOFactory.getInstance().getAnswerDAO();
                VotingDAO votingDAO = DAOFactory.getInstance().getVotingDAO();
                while (resultSet.next()) {
                    User user = new User();
                    int userId = resultSet.getInt(1);
                    user.setId(userId);
                    user.setRole(Role.valueOf(resultSet.getString(2).toUpperCase()));
                    user.setLogin(resultSet.getString(3));
                    user.setEmail(resultSet.getString(5));
                    user.setName(resultSet.getString(6));
                    user.setSurname(resultSet.getString(7));
                    user.setRegistrationDate(resultSet.getTimestamp(8).toLocalDateTime());
                    user.setBio(resultSet.getString(9));
                    user.setGender(Gender.valueOf(resultSet.getString(10).toUpperCase()));
                    user.setBanned(resultSet.getBoolean(11));
                    user.setQuestionsCount(questionDAO.readQuestionsCount(userId));
                    user.setAnswersCount(answerDAO.readAnswersCount(userId));
                    user.setAverageMark(votingDAO.readAverageMark(userId));
                    result.add(user);
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Problems with database operations. Unable search users", e);
        }
        return result;
    }

    private User extractUser(ResultSet resultSet) throws SQLException, DAOException {
        User user = null;
        QuestionDAO questionDAO = DAOFactory.getInstance().getQuestionDAO();
        AnswerDAO answerDAO = DAOFactory.getInstance().getAnswerDAO();
        VotingDAO votingDAO = DAOFactory.getInstance().getVotingDAO();
        while (resultSet.next()) {
            user = new User();
            int userId = resultSet.getInt(1);
            user.setId(userId);
            user.setRole(Role.valueOf(resultSet.getString(2).toUpperCase()));
            user.setLogin(resultSet.getString(3));
            user.setEmail(resultSet.getString(5));
            user.setName(resultSet.getString(6));
            user.setSurname(resultSet.getString(7));
            user.setRegistrationDate(resultSet.getTimestamp(8).toLocalDateTime());
            user.setBio(resultSet.getString(9));
            user.setGender(Gender.valueOf(resultSet.getString(10).toUpperCase()));
            user.setBanned(resultSet.getBoolean(11));
            user.setQuestionsCount(questionDAO.readQuestionsCount(userId));
            user.setAnswersCount(answerDAO.readAnswersCount(userId));
            user.setAverageMark(votingDAO.readAverageMark(userId));
        }
        return user;
    }

    @Override
    public int readUsersCount() throws DAOException {
        int count = 0;
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(readUsersCount)) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    count = resultSet.getInt(1);
                }
            }

        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Problems with database operations. Unable read users count", e);
        }
        return count;
    }

    @Override
    public void updateUser(User user) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateUser)) {

            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setString(4, user.getSurname());
            preparedStatement.setString(5, user.getBio());
            preparedStatement.setString(6, user.getRole().toString());
            preparedStatement.setString(7, user.getGender().toString());
            preparedStatement.setInt(8, user.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Problems with database operations. Unable update user with id : " + user.getId(), e);
        }
    }

    @Override
    public void updateUser(int id, boolean banned) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE user SET banned=? WHERE id=?")) {

            preparedStatement.setBoolean(1, banned);
            preparedStatement.setInt(2, id);

            preparedStatement.executeUpdate();

        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Problems with database operations. Unable ban user with id : " + id, e);
        }
    }

}
