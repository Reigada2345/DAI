public class ParagemService {

    private List<Paragem> paragens;

    public ParagemService() {
        this.paragens = new ArrayList<>();
    }

    public void adicionarParagem(Paragem paragem) {
        paragens.add(paragem);
    }

    public void atualizarParagem(Paragem paragem, String novoNome, String novaLocalizacao, boolean novasRotas, int novaLotacao) {
        paragem.editarParagem(novoNome, novaLocalizacao, novasRotas, novaLotacao);
    }

    public void desativarParagem(Paragem paragem) {
        paragem.desativarParagem();
    }

    public List<Paragem> listarParagens() {
        return new ArrayList<>(paragens);
    }

    public Paragem buscarParagemPorNome(String nome) {
        for (Paragem paragem : paragens) {
            if (paragem.getNome().equalsIgnoreCase(nome)) {
                return paragem;
            }
        }
        return null;
    }
}