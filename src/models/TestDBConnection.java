package models;
import java.sql.Connection;
import java.sql.SQLException;

public class TestDBConnection {
    public static void main(String[] args) {
        try {
            Connection conn = DatabaseConnection.connect();
            if (conn != null && !conn.isClosed()) {
                System.out.println("Conexão à base de dados estabelecida com sucesso!");
                conn.close();
            } else {
                System.out.println("Falha ao conectar à base de dados.");
            }
        } catch (SQLException e) {
            System.err.println("Erro na conexão: " + e.getMessage());
        }
    }
}
