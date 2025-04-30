import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // URL da base de dados (substitua "meubanco" pelo nome da sua BD)
    private static final String URL = "jdbc:mysql://localhost:3306/base_dados_grupo";
    private static final String USER = "root"; // Usuário do MySQL
    private static final String PASSWORD = "1234"; // Senha do MySQL

    public static Connection connect() {
        Connection conn = null;
        try {
            // Carregar o driver JDBC (opcional para versões recentes do Java)
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Estabelecer a conexão
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexão bem-sucedida!");
        } catch (ClassNotFoundException e) {
            System.err.println("Driver JDBC não encontrado: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Erro ao conectar à BD: " + e.getMessage());
        }
        return conn;
    }
    
    public static void main(String[] args) {
        Connection connection = connect();
        if (connection != null) {
            try {
                connection.close(); // Fechar a conexão ao terminar
                System.out.println("Conexão fechada.");
            } catch (SQLException e) {
                System.err.println("Erro ao fechar a conexão: " + e.getMessage());
            }
        }
    }
}