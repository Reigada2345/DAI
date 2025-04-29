public class Paragem {
    private String nome;
    private String localizacao;
    private boolean variasRotas;
    private int lotacao;
    private boolean ativa = true;

    public Paragem (boolean variasRotas, int lotacao) {
        this.nome = nome;
        this.localizacao = localizacao;
        this.lotacao = lotacao;
        this.variasRotas = variasRotas;
        this.ativa = true;
    }

    public static Paragem criarParagem(boolean variasRotas, int lotacao) {
        return new Paragem(nome, localizacao, variasRotas, lotacao);
    }

//GETTERS------
    public String getNome() {
        return nome;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public boolean getVariasRotas() {
        return variasRotas;
    }

    public int getLotacao() {
        return lotacao;
    }
//-------------
//SETTERS------
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public void setVariasRotas(boolean variasRotas) {
        this.variasRotas = variasRotas;
    }

    public void setLotacao(int lotacao) {
        this.lotacao = lotacao;
    }
//-------------

    public void desativarParagem() {
        this.ativa = false; // Marca a paragem como desativada
    }

    public boolean isAtiva() {
        return ativa; // Retorna o estado da paragem
    }
}
