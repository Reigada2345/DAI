public class Paragem {
    private boolean variasRotas;
    private int lotacao;

    public Paragem (boolean variasRotas, int lotacao) {
        this.lotacao = lotacao;
        this.variasRotas = variasRotas;
    }

//GETTERS------
    public boolean getVariasRotas() {
        return variasRotas;
    }

    public int getLotacao() {
        return lotacao;
    }
//-------------
//SETTERS------
    public void setVariasRotas(boolean variasRotas) {
        this.variasRotas = variasRotas;
    }

    public void setLotacao(int lotacao) {
        this.lotacao = lotacao;
    }
//-------------

}
