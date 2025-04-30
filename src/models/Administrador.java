package models;

public class Administrador extends Cliente {
    private String nome;

public String getNome() { // Método para acessar o valor de nome
        return nome;
    }
    public Administrador(String nome, String nomeProprio, String apelido, String contacto, 
                   boolean utilizadorPrioritario, String email, String password) {
    super(nomeProprio, apelido, contacto, utilizadorPrioritario, email, password);
    this.nome = nome;
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

    public Autocarro criarAutocarro(String numero, int capacidade, String rota) {
        return new Autocarro(numero, capacidade, rota);
    }

    public void editarAutocarro(Autocarro autocarro, String novoNumero, int novaCapacidade, String novaRota) {
        autocarro.editarAutocarro(novoNumero, novaCapacidade, novaRota);
    }

    public void atualizarPassageiro(Passageiro passageiro, String novoNome, int novaIdade, String novoBilhete) {
        passageiro.atualizarInformacoes(novoNome, novaIdade, novoBilhete);
    }
}