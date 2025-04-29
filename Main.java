import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

        // -----------------------
        // 1. Criar autocarros
        // -----------------------
        Autocarro bus1 = new Autocarro(1, "12-34-AB", "Mercedes Sprinter", 20.0f, 80, 0);
        Autocarro bus2 = new Autocarro(2, "56-78-CD", "Volvo B7R", 22.5f, 120, 0);
        Autocarro bus3 = new Autocarro(3, "90-12-EF", "MAN Lion's Coach", 19.8f, 40, 0);

        // -----------------------
        // 2. Testar sensores
        // -----------------------
        Sensores sensor1 = new Sensores(bus1);
        Sensores sensor2 = new Sensores(bus2);
        Sensores sensor3 = new Sensores(bus3);

        sensor1.atualizarSensores();
        sensor2.atualizarSensores();
        sensor3.atualizarSensores();

        System.out.println("\n=== Dados dos Sensores ===");
        sensor1.exibirDados();
        sensor2.exibirDados();
        sensor3.exibirDados();

        // -----------------------
        // 3. Testar clientes
        // -----------------------
        Cliente admin = new Cliente("admin@email.com", "1234", "admin");
        Cliente cliente = new Cliente("cliente@email.com", "5678", "cliente");

        System.out.println("\n=== Teste de Autenticação ===");
        System.out.println("Admin autenticado? " + admin.autenticar("1234")); // true
        System.out.println("Cliente autenticado? " + cliente.autenticar("5678")); // true
        System.out.println("Cliente autenticado com senha errada? " + cliente.autenticar("errado")); // false

        // -----------------------
        // 4. Testar com base de dados (opcional)
        // -----------------------
        String url = "jdbc:mysql://localhost:3306/base_dados_grupo";
        String user = "root";
        String password = "1234";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            AutocarroDAO dao = new AutocarroDAO();

            // Adicionar autocarro (podes comentar/descomentar)
            // dao.adicionarAutocarro("12-34-AC", "Mercedes AAA", 30);

            // Listar autocarros
            System.out.println("\n=== Autocarros na Base de Dados ===");
            for (Autocarro a : dao.listarAutocarros(conn)) {
                System.out.println("ID: " + a.getId() + " | Matrícula: " + a.getMatricula());
            }

            // Atualizar ou apagar (descomentar se precisares)
            // dao.atualizarAutocarro(1, "Mercedes-Benz", 25);
            // dao.apagarAutocarro(1);

        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }
}
