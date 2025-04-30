package models;

public class Passageiro {
    private String nome;
    private int idade;
    private String bilhete;

    public Passageiro(String nome, int idade, String bilhete) {
        this.nome = nome;
        this.idade = idade;
        this.bilhete = bilhete;
    }
//GETTERS ------
    public String getNome() {
        return nome;
    }

    public int getIdade() {
        return idade;
    }

    public String getBilhete() {
        return bilhete;
    }

//SETTERS ------
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public void setBilhete(String bilhete) {
        this.bilhete = bilhete;
    }

    public void atualizarInformacoes(String novoNome, int novaIdade, String novoBilhete) {
        setNome(novoNome);
        setIdade(novaIdade);
        setBilhete(novoBilhete);
    }
}