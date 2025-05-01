import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import models.Administrador;
import models.Autocarro;
import models.AutocarroDAO;
import models.Passageiro;
import models.Paragem;
import models.Sensores;


public class Main {
    public static void main(String[] args) {
        // Criar instâncias de autocarro corretamente
       Autocarro bus1 = new Autocarro(1, 0, 20.0f, "12-34-AB", 80, "Mercedes Sprinter", "Rota 1", "12-34-AB", true);
       // Autocarro bus2 = new Autocarro(2, "56-78-CD", "Volvo B7R", 22.5f, 120, 0);
        //Autocarro bus3 = new Autocarro(3, "90-12-EF", "MAN Lion's Coach", 19.8f, 40, 0);
        
        AutocarroDAO dao = new AutocarroDAO(); // já tem essa instância abaixo
        Sensores sensor1 = new Sensores(dao, bus1.getId()); // ✅ Correto
        //Sensores sensor2 = new Sensores(bus2);
        //Sensores sensor3 = new Sensores(bus3);
        
        sensor1.atualizarSensores();
        sensor1.exibirDados();
       // sensor2.atualizarSensores();
        //sensor2.exibirDados();
       // sensor3.atualizarSensores();
       // sensor3.exibirDados();
        
        //testes de dados de passageiros e paragem
        System.out.println("Welcome to the Administrator Project!");


        // Here you can add code to handle user interactions
        // For example, creating an instance of Administrador and using its methods
        Administrador admin = new Administrador("Admin", "Apelido", "912345678", false, "admin@email.com", "1234");
Paragem paragem = admin.criarParagem("Central", "Main St", true, 50);
        System.out.println("Paragem created: " + paragem.getNome());

        // Example of testing Passageiro
        System.out.println("\nTesting Passageiro:");
        Passageiro passageiro = new Passageiro("João", 25, "B12345");

        // Display initial information
        System.out.println("Nome: " + passageiro.getNome());
        System.out.println("Idade: " + passageiro.getIdade());
        System.out.println("Bilhete: " + passageiro.getBilhete());

        // Update passenger information
        passageiro.atualizarInformacoes("Maria", 30, "C67890");

        // Display updated information
        System.out.println("\nInformações atualizadas:");
        System.out.println("Nome: " + passageiro.getNome());
        System.out.println("Idade: " + passageiro.getIdade());
        System.out.println("Bilhete: " + passageiro.getBilhete());
        //fim dos testes de dados de passageiros e paragem


        // Conectar ao banco de dados
        String url = "jdbc:mysql://localhost:3306/base_dados_grupo";
        String user = "root";
        String password = "1234";
        
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
    
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