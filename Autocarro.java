public class Autocarro {
    private int id;
    private String matricula;
    private String modelo;
    private float temperatura;
    private int capacidade;
    private int lotacao;

    public Autocarro(int id, String matricula, String modelo, float temperatura, int capacidade, int lotacao) {
       this.id=id;
        this.matricula=matricula;
        this.modelo=modelo;
        this.temperatura = temperatura;
        this.capacidade = capacidade;
        this.lotacao = lotacao;
    }

// SETTERS
    public void setTemperatura(float temperatura) {
        if(temperatura < -20 || temperatura > 50) {
            throw new IllegalArgumentException("Temperatura fora dos valores esperados");
        } 
        this.temperatura = temperatura;
    }

    public void setCapacidade(int capacidade) {
        if(capacidade < 0) {
            throw new IllegalArgumentException("Capacidade não pode ser menor que 0");
        }
        this.capacidade = capacidade;
    }

    public void setLotacao(int lotacao) {
        if (lotacao > capacidade) {
            throw new IllegalArgumentException("Lotação não pode exceder a capacidade.");
        }
        this.lotacao = lotacao;
    }

// GETTERS
    public float getTemperatura() {
        return temperatura;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public int getLotacao() {
        return lotacao;
    }
    public String getMatricula() {
        return matricula;
    }
    public String getModelo() {
        return modelo;
    }
    public int getId() {
        return id;
    }



    

// CALCULAR LOTACAO
    public float percentagem_lotacao() {
        if (capacidade == 0) {
            throw new ArithmeticException("Capacidade não pode ser zero.");
        }
        return (float) lotacao * 100 / capacidade;
    }
}
