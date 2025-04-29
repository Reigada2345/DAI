public class Administrador extends Cliente {

    public Administrador(String nome) {
        super(nome);
    }

    public Paragem criarParagem(String nome, String localizacao, boolean variasRotas, int lotacao) {
        return new Paragem(nome, localizacao, variasRotas, lotacao);
    }

    public void editarParagem(Paragem paragem, String novoNome, String novaLocalizacao, boolean novasRotas, int novaLotacao) {
        paragem.editarParagem(novoNome, novaLocalizacao, novasRotas, novaLotacao);
    }

    public void desativarParagem(Paragem paragem) {
        paragem.desativarParagem();
    }
}