
package gr.automation.platform.utils.common;

import gr.automation.platform.utils.Utils;
import gr.automation.platform.utils.database.Connection;
import gr.automation.platform.utils.database.DatabaseTunnel;
import gr.automation.platform.utils.factory.StaticContextDataFactory;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class Cleanup {

    private final Utils utils;
    private final Connection databaseConnection;
    private final DatabaseTunnel databaseTunnel;

    private boolean tunnelStarted = false;

    public Cleanup(Utils utils) {
        this.utils = utils;
        this.databaseConnection = new Connection(utils);
        this.databaseTunnel = new DatabaseTunnel();
    }

    // ========================================
    // GET DATABASE CONNECTION
    // ========================================

    private java.sql.Connection getConnection() throws SQLException {

        if (!"apitesting".equalsIgnoreCase(utils.getDatabaseTarget())) {
            throw new IllegalStateException(
                    "Cleanup blocked. Database target is not apitesting."
            );
        }

        if (!tunnelStarted) {
            databaseTunnel.start();
            tunnelStarted = true;
        }

        return databaseConnection.connect();
    }

    // ========================================
    // CLEANUP REGISTERED USERS
    // ========================================

    public void cleanupRegisteredUsers() {

        System.out.println("=== START CLEANUP REGISTERED USERS ===");

        ArrayList<String> emails = new ArrayList<>(
                StaticContextDataFactory.userEmailList
        );

        System.out.println("Users for cleanup: " + emails.size());

        if (emails.isEmpty()) {
            System.out.println("No users to cleanup.");
            return;
        }

        String query = "DELETE FROM users WHERE email = ?";

        try {

            java.sql.Connection connection = getConnection();

            // VERIFY DATABASE
            if (!"apitesting".equalsIgnoreCase(connection.getCatalog())) {
                throw new IllegalStateException(
                        "Cleanup blocked. Wrong database: "
                                + connection.getCatalog()
                );
            }

            for (String email : emails) {

                System.out.println("Cleaning up user: " + email);

                try (PreparedStatement statement =
                             connection.prepareStatement(query)) {

                    statement.setString(1, email);

                    int deletedRows = statement.executeUpdate();

                    System.out.println("Deleted rows: " + deletedRows);

                    if (deletedRows == 1) {

                        StaticContextDataFactory.userEmailList.remove(email);

                        System.out.println("Cleanup SUCCESS: " + email);

                    } else {

                        System.out.println("Cleanup SKIPPED: " + email);
                    }
                }
            }

        } catch (SQLException e) {

            throw new IllegalStateException(
                    "Failed to cleanup registered users.", e
            );
        }

        System.out.println("=== END CLEANUP REGISTERED USERS ===");
    }

    // ========================================
    // CLOSE DATABASE CONNECTIONS AND TUNNEL
    // ========================================

    public void closeConnections() {

        try {

            databaseConnection.closeConnections();

        } catch (SQLException e) {

            throw new IllegalStateException(
                    "Failed to close database connection.", e
            );

        } finally {

            if (tunnelStarted) {
                databaseTunnel.stop();
                tunnelStarted = false;
            }
        }
    }
}
