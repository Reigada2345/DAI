package models;

public class Autocarro {
    private int id;
    private int lotacao;
    private float temperatura;
    private String numero;
    private int capacidade;
    private String modelo;
    private String rota;
    private String matricula;
    private boolean ativo = true;

    // Construtor
    public Autocarro(int id, int lotacao, float temperatura, String numero, int capacidade, String modelo, String rota, String matricula, boolean ativo) {
        this.id = id;
        this.lotacao = lotacao;
        this.temperatura = temperatura;
        this.numero = numero;
        this.capacidade = capacidade;
        this.modelo = modelo;
        this.rota = rota;
        this.matricula = matricula;
        this.ativo = ativo;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getLotacao() {
        return lotacao;
    }

    public String getNumero() {
        return numero;
    }

    public String getRota() {
        return rota;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getModelo() {
        return modelo;
    }

    public float getTemperatura() {
        return temperatura;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public boolean isAtivo() {
        return ativo;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setLotacao(int lotacao) {
        this.lotacao = lotacao;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setRota(String rota) {
        this.rota = rota;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setTemperatura(float temperatura) {
        this.temperatura = temperatura;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    // Método toString opcional para visualização do objeto
    @Override
    public String toString() {
        return "Autocarro{" +
                "id=" + id +
                ", lotacao=" + lotacao +
                ", temperatura=" + temperatura +
                ", numero='" + numero + '\'' +
                ", capacidade=" + capacidade +
                ", modelo='" + modelo + '\'' +
                ", rota='" + rota + '\'' +
                ", matricula='" + matricula + '\'' +
                ", ativo=" + ativo +
                '}';
    }
}
