package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:sqlserver://localhost;"
            + "instanceName=SQLEXPRESS;"
            + "databaseName=LaLigaAnalytics;"
            + "user=sa;"
            + "password=Admin123!;" // Pune aici parola setata in SSMS pentru sa
            + "trustServerCertificate=true;";

    static {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driverul JDBC nu a fost gasit.", e);
        }
    }

    private DatabaseConnection() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}