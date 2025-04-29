public class AutocarroService {

    public Autocarro criarAutocarro(String numero, int capacidade, String rota) {
        return new Autocarro(numero, capacidade, rota);
    }

    public void editarAutocarro(Autocarro autocarro, String novoNumero, int novaCapacidade, String novaRota) {
        autocarro.editarAutocarro(novoNumero, novaCapacidade, novaRota);
    }

    public void desativarAutocarro(Autocarro autocarro) {
        autocarro.desativarAutocarro();
    }
}