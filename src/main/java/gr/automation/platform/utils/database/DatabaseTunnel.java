package gr.automation.platform.utils.database;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Duration;

public class DatabaseTunnel {

    private static final String CONTAINER = "ap-suite-db-tunnel";
    private static final String NETWORK = "automation-platform-student-txt_default";

    private static final String JDBC_URL =
            "jdbc:mysql://127.0.0.1:3307/apitesting";

    public void start() {
        try {
            // Remove an old tunnel container, if one exists
            execute("docker", "rm", "-f", CONTAINER);

            // Start a new tunnel
            execute(
                    "docker", "run", "-d",
                    "--name", CONTAINER,
                    "--network", NETWORK,
                    "-p", "127.0.0.1:3307:3307",
                    "alpine/socat",
                    "-d", "-d",
                    "TCP-LISTEN:3307,fork,reuseaddr",
                    "TCP:db:3306"
            );

            // Wait until JDBC can connect
            long deadline = System.nanoTime()
                    + Duration.ofSeconds(30).toNanos();

            while (System.nanoTime() < deadline) {
                try (var connection = DriverManager.getConnection(
                        JDBC_URL, "cf_user", "1234")) {

                    if (!"apitesting".equalsIgnoreCase(connection.getCatalog())) {
                        throw new IllegalStateException("Wrong database!");
                    }

                    System.out.println("Database tunnel READY.");
                    return;

                } catch (SQLException e) {
                    Thread.sleep(500);
                }
            }

            throw new IllegalStateException("Database tunnel not ready.");

        } catch (Exception e) {
            throw new IllegalStateException(
                    "Failed to start database tunnel.", e);
        }
    }

    public void stop() {
        try {
            execute("docker", "rm", "-f", CONTAINER);
            System.out.println("Database tunnel STOPPED.");
        } catch (Exception e) {
            throw new IllegalStateException(
                    "Failed to stop database tunnel.", e);
        }
    }

    private void execute(String... command)
            throws IOException, InterruptedException {

        Process process = new ProcessBuilder(command)
                .redirectErrorStream(true)
                .inheritIO()
                .start();

        if (process.waitFor() != 0) {
            throw new IllegalStateException(
                    "Command failed: " + String.join(" ", command));
        }
    }
}
