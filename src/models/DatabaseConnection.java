package models;
//package lib;
// Removed package declaration to match the expected package
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
  
    // private static final String URL = "jdbc:mysql://server-dai-bd.mysql.database.azure.com:3306/base_dados_grupo?useSSL=true";
    // private static final String USER = "CloudSAa64f1709@server-dai-bd";
    // private static final String PASSWORD = "1234";
    private static final String URL = "jdbc:mysql://server-dai-bd.mysql.database.azure.com:3306/base_dados_grupo?useSSL=true";
    private static final String USER = "CloudSAa64f1709";
    private static final String PASSWORD = "Mariana.0311";
    
    public static Connection connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("Driver JDBC não encontrado: " + e.getMessage());
            return null;
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            return null;
        }
    }
}