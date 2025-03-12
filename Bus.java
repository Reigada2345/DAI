import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Bus {
    private float temperatura;
    private int capacidade;
    private int lotacao;

    public Bus(float temperatura, int capacidade, int lotacao) {
        this.temperatura = temperatura;
        this.capacidade = capacidade;
        this.lotacao = lotacao;
    }

    public float percentagem_lotacao () {
        return (float) lotacao * 100 / capacidade;
    }

    
}