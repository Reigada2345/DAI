public class Paragem {
    private String nome;
    private String localizacao;
    private boolean variasRotas;
    private int lotacao;
    private boolean ativa;

    public Paragem(String nome, String localizacao, boolean variasRotas, int lotacao) {
        this.nome = nome;
        this.localizacao = localizacao;
        this.variasRotas = variasRotas;
        this.lotacao = lotacao;
        this.ativa = true; // Paragem is active by default
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public boolean isVariasRotas() {
        return variasRotas;
    }

    public void setVariasRotas(boolean variasRotas) {
        this.variasRotas = variasRotas;
    }

    public int getLotacao() {
        return lotacao;
    }

    public void setLotacao(int lotacao) {
        this.lotacao = lotacao;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public void editarParagem(String novoNome, String novaLocalizacao, boolean novasRotas, int novaLotacao) {
        this.nome = novoNome;
        this.localizacao = novaLocalizacao;
        this.variasRotas = novasRotas;
        this.lotacao = novaLotacao;
    }

    public void desativarParagem() {
        this.ativa = false;
    }
}