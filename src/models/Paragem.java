package models;


public class Paragem {
    private String nome;
    private String localizacao;
    private boolean variasRotas;
    private int lotacao;
    private boolean ativa = true; // Paragem is active by default

    public Paragem(String nome, String localizacao, boolean variasRotas, int lotacao) {
        this.nome = nome;
        this.localizacao = localizacao;
        this.variasRotas = variasRotas;
        this.lotacao = lotacao;
    }

    public static Paragem criarParagem(String nome, String localizacao, boolean variasRotas, int lotacao) {
        return new Paragem(nome, localizacao, variasRotas, lotacao);
    }

    // Método para editar a paragem
    public void editarParagem(String novoNome, String novaLocalizacao, boolean novasRotas, int novaLotacao) {
        this.nome = novoNome;
        this.localizacao = novaLocalizacao;
        this.variasRotas = novasRotas;
        this.lotacao = novaLotacao;
    }

    // GETTERS
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

    public boolean isAtiva() {
        return ativa;
    }

    // SETTERS
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

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }

    // Método para desativar a paragem
    public void desativarParagem() {
        this.ativa = false; // Marca a paragem como desativada
    }
}
