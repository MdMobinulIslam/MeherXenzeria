import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateDatabase {

    public static void main(String[] args) {
        // Database connection parameters
        String url = "jdbc:mysql://localhost:3306";
        String username = "your_username";
        String password = "your_password";
        String databaseName = "MeherXenzeria";

        // SQL code for creating the database
        String sql = "CREATE DATABASE IF NOT EXISTS " + databaseName;

        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement()) {

            // Execute the SQL code to create the database
            statement.executeUpdate(sql);

            System.out.println("Database '" + databaseName + "' created successfully!");

        } catch (SQLException e) {
            System.err.println("Error creating database: " + e.getMessage());
        }
    }
}
