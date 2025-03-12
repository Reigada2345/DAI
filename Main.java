public class Main {
    public static void main(String[] args) {
        Bus bus1 = new Bus(0, 80, 0);
        Bus bus2 = new Bus(0, 120, 0);
        Bus bus3 = new Bus(0, 40, 0);
        Sensores sensor1 = new Sensores(bus1);
        Sensores sensor2 = new Sensores(bus2);
        Sensores sensor3 = new Sensores(bus3);
        sensor1.atualizarSensores();
        sensor1.exibirDados();
        sensor2.atualizarSensores();
        sensor2.exibirDados();
        sensor3.atualizarSensores();
        sensor3.exibirDados();
    }
}
