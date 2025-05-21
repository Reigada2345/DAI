package models;

import java.util.List;

public class Administrador extends Cliente {
    private String nome;

public String getNome() { // Método para acessar o valor de nome
        return nome;
    }
    public Administrador(String nome, String email, String password) {
        super(email, password);
        this.nome = nome; // Default password for admin
    }

    public Paragem criarParagem(String nome, String localizacao, boolean variasRotas, int lotacao) {
        return new Paragem(nome, localizacao, variasRotas, lotacao);
    }

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

    public void editarAutocarro(Autocarro autocarro, String novoNumero, int novaCapacidade, String novaRota) {
        autocarro.editarAutocarro(novoNumero, novaCapacidade, novaRota);
    }

    public void atualizarPassageiro(Passageiro passageiro, String novoNome, int novaIdade, String novoBilhete) {
        passageiro.atualizarInformacoes(novoNome, novaIdade, novoBilhete);
    }
}
