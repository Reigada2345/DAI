import java.util.Random;

public class Sensores {
    private Bus bus1;
    private Random random;

    public Sensores() {
        bus1 = new Bus(0, 0, 0);
        random = new Random();

        // Configura a capacidade do ônibus
        bus1.setCapacidade(80);
        
        // Inicializa a lotação e temperatura com valores aleatórios
        atualizarSensores();
    }

    public void atualizarSensores() {
        // Gera uma temperatura entre -10°C e 40°C
        float temperaturaAleatoria = -10 + random.nextFloat() * (40 - (-10));
        bus1.setTemperatura(temperaturaAleatoria);

        // Gera uma lotação entre 0 e a capacidade do ônibus
        int lotacaoAleatoria = random.nextInt(bus1.getCapacidade() + 1);
        bus1.setLotacao(lotacaoAleatoria);
    }

    public void exibirDados() {
        System.out.println("Temperatura: " + bus1.getTemperatura() + "°C");
        System.out.println("Capacidade: " + bus1.getCapacidade());
        System.out.println("Lotação: " + bus1.getLotacao());
        System.out.println("Ocupação: " + bus1.percentagem_lotacao() + "%");
    }

    public static void main(String[] args) {
        Sensores sensores = new Sensores();
        sensores.exibirDados();
    }

}
