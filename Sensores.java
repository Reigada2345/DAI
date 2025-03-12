import java.util.*;

public class Sensores {
    private Bus bus;
    private Random random;

    public Sensores(Bus bus) {
        random = new Random();
        this.bus = bus;
        
        // INICIALIZAR DADOS
        atualizarSensores();
    }

    public void atualizarSensores() {
        // GERAR TEMPERATURA
        float temperaturaAleatoria = -10 + random.nextFloat() * (40 - (-10));
        bus.setTemperatura(temperaturaAleatoria);

        // GERAR LOTACAO
        int lotacaoAleatoria = random.nextInt(bus.getCapacidade() + 1);
        bus.setLotacao(lotacaoAleatoria);
    }

    public void exibirDados() {
        System.out.println("Temperatura: " + bus.getTemperatura() + "°C");
        System.out.println("Capacidade: " + bus.getCapacidade());
        System.out.println("Lotação: " + bus.getLotacao());
        System.out.println("Ocupação: " + bus.percentagem_lotacao() + "%");
    }
}
