package models;

public class Autocarro {
    private String numero;
    private int capacidade;
    private String rota;
    private boolean ativo = true; // O autocarro está ativo por padrão

    public Autocarro(String numero, int capacidade, String rota) {
        this.numero = numero;
        this.capacidade = capacidade;
        this.rota = rota;
        this.ativo = true; // Ativo por padrão
    }

    // Método estático para criar um autocarro
    public static Autocarro criarAutocarro(String numero, int capacidade, String rota) {
        return new Autocarro(numero, capacidade, rota);
    }

    // Método para editar os detalhes do autocarro
    public void editarAutocarro(String novoNumero, int novaCapacidade, String novaRota) {
        this.numero = novoNumero;
        this.capacidade = novaCapacidade;
        this.rota = novaRota;
    }

    // Método para desativar o autocarro
    public void desativarAutocarro() {
        this.ativo = false;
    }

    // GETTERS
    public String getNumero() {
        return numero;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public String getRota() {
        return rota;
    }

    public boolean isAtivo() {
        return ativo;
    }

    // SETTERS
    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public void setRota(String rota) {
        this.rota = rota;
    }
}