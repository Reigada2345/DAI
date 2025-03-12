public class Main {
    public static void main(String[] args) {
        Bus bus1 = new Bus(0, 80, 0);
        Sensores sensor = new Sensores(bus1);
        sensor.atualizarSensores();
        sensor.exibirDados();
    }
}
