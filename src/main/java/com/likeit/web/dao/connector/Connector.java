package main.java.com.likeit.web.dao.connector;

import main.java.com.likeit.web.dao.exception.DAOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static main.java.com.likeit.web.dao.exception.Exceptions.CONNECTION_CREATION_EXCEPTION;
import static main.java.com.likeit.web.dao.exception.Exceptions.MISSED_JDBC_DRIVER;

public class Connector {

    private final static Logger LOGGER = Logger.getLogger(Connector.class.getName());
    private final static String DB_URL = "jdbc:mysql://localhost:3306/like_it";
    private final static String USERNAME = "root";
    private final static String PASSWORD = "root";
    private final static String JDBC_DRIVER = "com.mysql.jdbc.Driver";

    static {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.INFO, MISSED_JDBC_DRIVER.getMessage(), e);
        }
    }

    public static Connection getConnection() throws DAOException {
        Connection connection;
        try {
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new DAOException(CONNECTION_CREATION_EXCEPTION.getMessage(), e);
        }
        return connection;
    }

}
