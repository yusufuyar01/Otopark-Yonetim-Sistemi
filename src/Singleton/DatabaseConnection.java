package Singleton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;

    // Veritabanı bilgileri
    private static final String DB_URL = "jdbc:mysql://localhost:3306/otaparkdb?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    // Private yapıcı, başka sınıflar bu sınıfı oluşturamaz
    private DatabaseConnection() {
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Veritabanına bağlanırken hata oluştu.");
        }
    }

    // Singleton örneği sağlayıcı
    public static DatabaseConnection getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnection.class) {
                if (instance == null) {
                    instance = new DatabaseConnection();
                }
            }
        }
        return instance;
    }

    // Bağlantıyı döndüren metod
    public Connection getConnection() {
        return connection;
    }
}
