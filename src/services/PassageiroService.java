import models.Passageiro;

public class PassageiroService {

    public void atualizarInformacaoPassageiro(Passageiro passageiro, String novoNome, int novaIdade, String novoBilhete) {
        passageiro.setNome(novoNome);
        passageiro.setIdade(novaIdade);
        passageiro.setBilhete(novoBilhete);
    }

    public Passageiro obterInformacaoPassageiro(Passageiro passageiro) {
        return passageiro;
    }
}