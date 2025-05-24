/*package models;

POR AQUI AQUELA EXTENSAO PARA FUNCIONAR


import java.sql.Connection;
import java.util.Random;

public class Sensores {
    private final AutocarroDAO busDAO;
    private final int autocarroId;  
    private final Random random;

    public Sensores(AutocarroDAO busDAO, int autocarroId) {
        this.random = new Random();
        this.busDAO = busDAO;
        this.autocarroId = autocarroId;
     
    }

    public void atualizarSensores() {
        // trocar para api 
        float temperaturaAleatoria = -10 + random.nextFloat() * (40 - (-10));
        busDAO.atualizarTemperatura(autocarroId, temperaturaAleatoria);

        // GERAR LOTAÇÃO    
        int capacidade = busDAO.getCapacidade(autocarroId);  
        int lotacaoAleatoria = random.nextInt(capacidade + 1);
        Connection connection = busDAO.getConnection(); // Assuming getConnection() provides the required Connection object
        busDAO.atualizarLotacao(connection, autocarroId, lotacaoAleatoria);
    }

    public void exibirDados() {
        System.out.println("Sensores do Autocarro ID: " + autocarroId);

        float temperatura = busDAO.getTemperatura(autocarroId); 
        int lotacao = busDAO.getLotacao(autocarroId); 

        System.out.println("Temperatura: " + temperatura + " °C");
        System.out.println("Lotação: " + lotacao + " passageiros");
    }
}
*/