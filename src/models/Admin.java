package models;

public class Admin extends Cliente {

    private final AutocarroDAO autocarroDAO;

    public Admin(String nomeProprio, String apelido, String contacto, boolean utilizadorPrioritario, String email, String password) {
        super(nomeProprio, apelido, contacto, utilizadorPrioritario, email, password);
        this.autocarroDAO = new AutocarroDAO();
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

    public void criarAutocarro(String matricula, String modelo, int capacidade, String numero, String rota, float temperatura, int lotacao, boolean ativo) {
        autocarroDAO.adicionarAutocarro(matricula, modelo, capacidade, numero, rota, temperatura, lotacao, ativo);
    }

    public void editarAutocarro(Autocarro autocarro, String novoModelo, int novaCapacidade, String novoNumero, String novaRota, float novaTemperatura, int novaLotacao, boolean ativo) {
        autocarro.setModelo(novoModelo);
        autocarro.setCapacidade(novaCapacidade);
        autocarro.setNumero(novoNumero);
        autocarro.setRota(novaRota);
        autocarro.setTemperatura(novaTemperatura);
        autocarro.setLotacao(novaLotacao);
        autocarro.setAtivo(ativo);

        autocarroDAO.atualizarAutocarro(
                autocarro.getId(),
                novoModelo,
                novaCapacidade,
                novoNumero,
                novaRota,
                novaTemperatura,
                novaLotacao,
                ativo
        );
    }

    public void atualizarPassageiro(Passageiro passageiro, String novoNome, int novaIdade, String novoBilhete) {
        passageiro.atualizarInformacoes(novoNome, novaIdade, novoBilhete);
    }
}
