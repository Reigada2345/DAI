import java.util.*;
import java.sql.Connection;


public class Sensores {
    private AutocarroDAO busDAO;
    private int autocarroId;  // Para saber qual autocarro estamos monitorando
    private Random random;

    public Sensores(AutocarroDAO busDAO, int autocarroId) {
        this.random = new Random();
        this.busDAO = busDAO;
        this.autocarroId = autocarroId;
        
        atualizarSensores();
    }

    public void atualizarSensores() {
        Connection conn = busDAO.getConnection();  // Você precisará adicionar este método em AutocarroDAO
        
        // GERAR TEMPERATURA
        float temperaturaAleatoria = -10 + random.nextFloat() * (40 - (-10));
        AutocarroDAO.atualizarTemperatura(conn, autocarroId, temperaturaAleatoria);

        // GERAR LOTACAO    
        // Você precisará de um método para obter a capacidade primeiro
        int capacidade = busDAO.getCapacidade(autocarroId);  // Adicionar este método em AutocarroDAO
        int lotacaoAleatoria = random.nextInt(capacidade + 1);
        AutocarroDAO.atualizarLotacao(conn, autocarroId, lotacaoAleatoria);
    }

    
}