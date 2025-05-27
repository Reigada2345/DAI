package models;


public class Capacidade {
    private int capacidadeMaxima;

    public Capacidade(int capacidadeMaxima) {
        this.capacidadeMaxima = capacidadeMaxima;
    }

    public int getCapacidadeMaxima() {
        return capacidadeMaxima;
    }

    public void setCapacidadeMaxima(int capacidadeMaxima) {
        this.capacidadeMaxima = capacidadeMaxima;
    }

    @Override
    public String toString() {
        return "Capacidade{" +
                "capacidadeMaxima=" + capacidadeMaxima +
                '}';
    }
}