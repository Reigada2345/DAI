public class Main {
    public static void main(String[] args) {
        // Initialize the application
        System.out.println("Welcome to the Administrator Project!");


        // Here you can add code to handle user interactions
        // For example, creating an instance of Administrador and using its methods
        Administrador admin = new Administrador("Admin Name");
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
    }
}