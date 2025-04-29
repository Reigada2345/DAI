public class Main {
    public static void main(String[] args) {
        // Initialize the application
        System.out.println("Welcome to the Administrator Project!");

        // Here you can add code to handle user interactions
        // For example, creating an instance of Administrador and using its methods
        Administrador admin = new Administrador("Admin Name");

        // Example of creating a Paragem
        Paragem paragem = admin.criarParagem("Central", "Main St", true, 50);
        System.out.println("Paragem created: " + paragem.getNome());

        // Further interactions can be implemented here
    }
}