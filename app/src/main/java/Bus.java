public class Bus {
    private float temperatura;
    private int capacidade;
    private int lotacao;

    public Bus(float temperatura, int capacidade, int lotacao) {
        this.temperatura = temperatura;
        this.capacidade = capacidade;
        this.lotacao = lotacao;
    }

    //GETTERS
    public float getTemperatura() {
        return temperatura;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public int getLotacao() {
        return lotacao;
    }

    //SETTERS
    public void setTemperatura(float temperatura) {
        this.temperatura = temperatura;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public void setLotacao(int lotacao) {
        this.lotacao = lotacao;
    }
    //

    public float percentagem_lotacao () {
        return (float) lotacao * 100 / capacidade;
    }
    
}
