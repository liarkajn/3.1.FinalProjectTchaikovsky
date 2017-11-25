package main.java.com.likeit.web.dao.exception;

public enum Exceptions {

    CONNECTION_CREATION_EXCEPTION("Can't establish connection to database"),
    DATABASE_OPERATIONS_EXCEPTION("Problems with database operations"),
    UNABLE_OPEN_PROPERTY_FILE("Unable open properties file"),
    MISSED_PROPERTY_FILE("Properties file not found"),
    MISSED_JDBC_DRIVER("Can't find JDBC driver"),
    USER_ALREADY_EXISTS("User already exists");


    private String message;

    Exceptions(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
