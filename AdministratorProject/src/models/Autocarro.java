public class Autocarro {
    private String numero;
    private int capacidade;
    private String rota;

    public Autocarro(String numero, int capacidade, String rota) {
        this.numero = numero;
        this.capacidade = capacidade;
        this.rota = rota;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public String getRota() {
        return rota;
    }

    public void setRota(String rota) {
        this.rota = rota;
    }

    public void atualizarDetalhes(String novoNumero, int novaCapacidade, String novaRota) {
        this.numero = novoNumero;
        this.capacidade = novaCapacidade;
        this.rota = novaRota;
    }
}