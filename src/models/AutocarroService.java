package models;


public class AutocarroService {

    public Autocarro criarAutocarro(int id, int lotacao, float temperatura, String numero, int capacidade, String modelo, String rota, String matricula, boolean ativo) {
        return new Autocarro(id, lotacao, temperatura, numero, capacidade, modelo, rota, matricula, ativo);
    }

    public void editarAutocarro(Autocarro autocarro, String novoNumero, int novaCapacidade, String novaRota) {
        autocarro.setNumero(novoNumero);
        autocarro.setCapacidade(novaCapacidade);
        autocarro.setRota(novaRota);
    }

    public void desativarAutocarro(Autocarro autocarro) {
        autocarro.setAtivo(false);
    }
}
