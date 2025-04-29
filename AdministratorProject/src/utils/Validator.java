public class Validator {

    public static boolean isValidNome(String nome) {
        return nome != null && !nome.trim().isEmpty();
    }

    public static boolean isValidLocalizacao(String localizacao) {
        return localizacao != null && !localizacao.trim().isEmpty();
    }

    public static boolean isValidLotacao(int lotacao) {
        return lotacao > 0;
    }

    public static boolean isValidNumero(int numero) {
        return numero > 0;
    }

    public static boolean isValidIdade(int idade) {
        return idade >= 0;
    }

    public static boolean isValidBilhete(String bilhete) {
        return bilhete != null && !bilhete.trim().isEmpty();
    }
}