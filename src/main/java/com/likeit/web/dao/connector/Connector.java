package main.java.com.likeit.web.dao.connector;

import main.java.com.likeit.web.dao.exception.DAOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Connector {

    private final static Logger connectorLogger = Logger.getLogger(Connector.class.getName());
    private final static String DB_URL = "jdbc:mysql://localhost:3306/like_it";
    private final static String USERNAME = "root";
    private final static String PASSWORD = "root";
    private final static String JDBC_DRIVER = "com.mysql.jdbc.Driver";

    static {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            connectorLogger.log(Level.INFO, "Can't find JDBC driver", e);
        }
    }

    public static Connection getConnection() throws DAOException {
        Connection connection;
        try {
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new DAOException("Can't establish connection to database", e);
        }
        return connection;
    }

}
