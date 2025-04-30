package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
  
    private static final String URL = "jdbc:mysql://server-dai-bd.mysql.database.azure.com:3306/base_dados_grupo?useSSL=true";
    private static final String USER = "CloudSAa64f1709@server-dai-bd";
    private static final String PASSWORD = "1234";
    
    public static Connection connect() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Erro na conexão com o banco de dados: " + e.getMessage());
            return null;
        }
    }
}