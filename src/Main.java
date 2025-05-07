import models.Administrador;
import models.Autocarro;
import models.AutocarroDAO;
import models.Passageiro;
import models.Sensores;

public class Main {
    public static void main(String[] args) {
        // Criar instâncias de autocarro de forma genérica
        // A ideia aqui é interagir com a base de dados para obter dados reais no futuro

        AutocarroDAO dao = new AutocarroDAO(); // instância do DAO para autocarros

        // Exemplo de como obter um autocarro da base de dados
        // Autocarro bus1 = dao.obterAutocarroPorId(1); // Método hipotético para buscar um autocarro no banco de dados
        // Aqui podemos testar com uma instância genérica enquanto a base de dados não estiver configurada.
        Autocarro bus1 = new Autocarro(1, 0, 20.0f, "12-34-AB", 80, "Mercedes Sprinter", "Rota 1", "12-34-AB", true);

        // Criar instância de sensores para o autocarro
        Sensores sensor1 = new Sensores(dao, bus1.getId()); // Passa o ID do autocarro para o sensor
        sensor1.atualizarSensores();
        sensor1.exibirDados();
        
        // Exemplo de testes de dados de passageiros e paragens (sem dados estáticos)
        System.out.println("Welcome to the Administrator Project!");

        // Exemplo de como o administrador pode criar uma paragem (ligando à base de dados no futuro)
        Administrador admin = new Administrador("Admin Name", "admin@example.com", "password123", "Admin Role", true, "Admin Department");
        System.out.println("Administrador criado: " + admin.getNomeProprio());
        // A seguir, criamos a paragem usando o método de administrador
        // Paragem paragem = admin.criarParagem("Central", "Main St", true, 50);
        // No futuro, podemos ajustar o método para interagir com a base de dados e criar paragens dinâmicas.

        // Testando Passageiro
        System.out.println("\nTesting Passageiro:");
        Passageiro passageiro = new Passageiro("João", 25, "B12345");

        // Exibir informações do passageiro
        System.out.println("Nome: " + passageiro.getNome());
        System.out.println("Idade: " + passageiro.getIdade());
        System.out.println("Bilhete: " + passageiro.getBilhete());

        // Atualizando informações do passageiro
        passageiro.atualizarInformacoes("Maria", 30, "C67890");

        // Exibindo as informações atualizadas
        System.out.println("\nInformações atualizadas:");
        System.out.println("Nome: " + passageiro.getNome());
        System.out.println("Idade: " + passageiro.getIdade());
        System.out.println("Bilhete: " + passageiro.getBilhete());
    }
}
