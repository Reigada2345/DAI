public class Sensores {
    private Autocarro bus;

    public Sensores(Autocarro bus) {
        this.bus = bus;
    }

    public void registarEntrada() {
        if (bus.getLotacao() < bus.getCapacidade()) {
            bus.setLotacao(bus.getLotacao() + 1);
            System.out.println("Entrada registada.");
        } else {
            System.out.println("Autocarro cheio!");
        }
    }

    public void registarSaida() {
        if (bus.getLotacao() > 0) {
            bus.setLotacao(bus.getLotacao() - 1);
            System.out.println("Saída registada.");
        } else {
            System.out.println("O autocarro está vazio!");
        }
    }

    public void exibirDados() {
        System.out.println("Temperatura: " + bus.getTemperatura() + "°C");
        System.out.println("Capacidade: " + bus.getCapacidade());
        System.out.println("Lotação atual: " + bus.getLotacao());
        System.out.println("Ocupação: " + bus.percentagem_lotacao() + "%");
    }
}
