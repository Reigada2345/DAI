package models;
import java.util.*;
import java.sql.Connection;

public class Sensores {
    private AutocarroDAO busDAO;
    private int autocarroId;  
    private Random random;

    public Sensores(AutocarroDAO busDAO, int autocarroId) {
        this.random = new Random();
        this.busDAO = busDAO;
        this.autocarroId = autocarroId;
        
        atualizarSensores();
    }

    public void atualizarSensores() {
        Connection conn = busDAO.getConnection();  
        
        // GERAR TEMPERATURA
        float temperaturaAleatoria = -10 + random.nextFloat() * (40 - (-10));
        AutocarroDAO.atualizarTemperatura(conn, autocarroId, temperaturaAleatoria);

        // GERAR LOTACAO    
        // Você precisará de um método para obter a capacidade primeiro
        int capacidade = busDAO.getCapacidade(autocarroId);  
        int lotacaoAleatoria = random.nextInt(capacidade + 1);
        AutocarroDAO.atualizarLotacao(conn, autocarroId, lotacaoAleatoria);
    }
    public void exibirDados() {
        System.out.println("Sensores do Autocarro ID: " + autocarroId);
        Connection conn = busDAO.getConnection();
        
        float temperatura = busDAO.getTemperatura(conn, autocarroId); 
        int lotacao = busDAO.getLotacao(conn, autocarroId); 
        System.out.println("Temperatura: " + temperatura + " °C");
        System.out.println("Lotação: " + lotacao + " passageiros");
    }
    

    
}