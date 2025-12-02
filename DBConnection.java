import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // Sesuaikan credential di bawah jika perlu
    // Use MySQL protocol so mysql-connector-j works; change if you use MariaDB driver
    // Add common params; adjust timezone or SSL settings if needed
    private static final String URL = "jdbc:mysql://localhost:3306/cbt_database?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = ""; 

    static {
        // Try to ensure JDBC driver is loaded. Modern drivers auto-register via ServiceLoader,
        // but loading explicitly helps when running without correct classloader.
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            try {
                // fallback to MariaDB driver if present
                Class.forName("org.mariadb.jdbc.Driver");
            } catch (ClassNotFoundException ex) {
                // no driver found on classpath; let getConnection() throw the SQLException later
            }
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
