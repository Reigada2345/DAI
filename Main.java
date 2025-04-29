import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        // Criar instâncias de autocarro corretamente
       Autocarro bus1 = new Autocarro(1, "12-34-AB", "Mercedes Sprinter", 20.0f, 80, 0);
       // Autocarro bus2 = new Autocarro(2, "56-78-CD", "Volvo B7R", 22.5f, 120, 0);
        //Autocarro bus3 = new Autocarro(3, "90-12-EF", "MAN Lion's Coach", 19.8f, 40, 0);
        
        Sensores sensor1 = new Sensores(bus1);
        //Sensores sensor2 = new Sensores(bus2);
        //Sensores sensor3 = new Sensores(bus3);
        
        sensor1.atualizarSensores();
        sensor1.exibirDados();
       // sensor2.atualizarSensores();
        //sensor2.exibirDados();
       // sensor3.atualizarSensores();
       // sensor3.exibirDados();
        
        // Conectar ao banco de dados
        String url = "jdbc:mysql://localhost:3306/base_dados_grupo";
        String user = "root";
        String password = "1234";
        
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            AutocarroDAO dao = new AutocarroDAO();
    
            // Adicionar um autocarro
            dao.adicionarAutocarro("12-34-AC", "Mercedes AAA", 30);
    
            // Listar autocarros
            for (Autocarro a : dao.listarAutocarros(conn)) {
                System.out.println(a.getMatricula());
                System.out.println(a.getId());
            }
    
            // Atualizar um autocarro
           dao.atualizarAutocarro(1, "Mercedes-Benz", 25);
    
            // Apagar um autocarro
           dao.apagarAutocarro(1);
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }
}