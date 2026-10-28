package gr.automation.platform.utils.database;

import gr.automation.platform.utils.Utils;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Connection {

    private final Utils utils;
    private java.sql.Connection connection;

    public Connection(Utils utils) {
        this.utils = utils;
    }

    public java.sql.Connection connect() throws SQLException {

        if (connection == null || connection.isClosed()) {

            connection = DriverManager.getConnection(
                    utils.getDbUrl(),
                    utils.getDbUsername(),
                    utils.getDbPassword()
            );

            String database = connection.getCatalog();

            if (!"apitesting".equalsIgnoreCase(database)) {
                connection.close();
                connection = null;

                throw new IllegalStateException(
                        "Wrong database: " + database
                );
            }

            System.out.println("Connected to database: " + database);
        }

        return connection;
    }

    public void closeConnections() throws SQLException {

        if (connection != null && !connection.isClosed()) {
            connection.close();
            connection = null;
            System.out.println("Database connection closed.");
        }
    }
}