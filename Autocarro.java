
public class Autocarro {
    private int id;
    private int lotacao;
    private float temperatura;
    private String numero;
    private int capacidade;
    private String modelo;
    private String rota;
    private String matricula;
    private boolean ativo = true; // O autocarro está ativo por padrão

    public Autocarro(int id, int lotacao, float temperatura, String numero, int capacidade, String modelo, String rota, String matricula, boolean ativo) {
        this.id = id;
        this.lotacao = lotacao;
        this.numero = numero;
        this.rota = rota;
        this.ativo = ativo;
        this.matricula = matricula;
        this.modelo = modelo;
        this.temperatura = temperatura;
        this.capacidade = capacidade;
    }

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

    // SETTERS
    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setLotacao(int lotacao) {
        this.lotacao = lotacao;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public void setTemperatura(float temperatura) {
        this.temperatura = temperatura;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public void setRota(String rota) {
        this.rota = rota;
    }

//------------------------------------------

public float percentagem_lotacao() {
        if (capacidade == 0) {
            return 0; // Evitar divisão por zero
        }
        return ((float) lotacao / capacidade) * 100;
    }
}