package models;

import java.util.List;

public class Administrador extends Cliente {
    private String nome;
    private AutocarroDAO autocarroDAO;

    public Administrador(String nome, String email, String password, String endereco, boolean ativo, String telefone) {
        super(email, password, endereco, ativo, telefone, nome);
        this.nome = nome;
        this.autocarroDAO = new AutocarroDAO(); // Inicializa a conexão com o DAO
    }

    // Método para criar uma paragem
    public Paragem criarParagem(String nome, String localizacao, boolean variasRotas, int lotacao) {
        return new Paragem(nome, localizacao, variasRotas, lotacao);
    }

    // Método para editar uma paragem existente
    public void editarParagem(Paragem paragem, String novoNome, String novaLocalizacao, boolean novasRotas, int novaLotacao) {
        paragem.editarParagem(novoNome, novaLocalizacao, novasRotas, novaLotacao);
    }

    // Método para desativar uma paragem
    public void desativarParagem(Paragem paragem) {
        paragem.desativarParagem();
    }

    // Método para criar um autocarro (persistente no banco de dados)
    public void criarAutocarro(String matricula, String modelo, int capacidade, String numero, String rota, float temperatura, int lotacao, boolean ativo) {
        autocarroDAO.adicionarAutocarro(matricula, modelo, capacidade, numero, rota, temperatura, lotacao, ativo);
    }

    // Método para editar um autocarro (atualiza no banco de dados)
    public void editarAutocarro(int id, String modelo, int capacidade, String numero, String rota, float temperatura, int lotacao, boolean ativo) {
        autocarroDAO.atualizarAutocarro(id, modelo, capacidade, numero, rota, temperatura, lotacao, ativo);
    }

    // Método para apagar um autocarro (do banco de dados)
    public void apagarAutocarro(int id) {
        autocarroDAO.apagarAutocarro(id);
    }

    // Método para listar todos os autocarros
    public List<Autocarro> listarAutocarros() {
        return autocarroDAO.listarAutocarros();
    }

    // Método para atualizar temperatura de um autocarro
    public void atualizarTemperatura(int id, float novaTemperatura) {
        autocarroDAO.atualizarTemperatura(id, novaTemperatura);
    }

    // Método para atualizar lotação de um autocarro
    public void atualizarLotacao(int id, int novaLotacao) {
        autocarroDAO.atualizarLotacao(autocarroDAO.getConnection(), id, novaLotacao);
    }

    // Método para atualizar informações de um passageiro
    public void atualizarPassageiro(Passageiro passageiro, String novoNome, int novaIdade, String novoBilhete) {
        passageiro.atualizarInformacoes(novoNome, novaIdade, novoBilhete);
    }
}
