package models;


public class Cliente {
    private String nomeProprio;
    private String apelido;
    private String contacto;
    private boolean utilizadorPrioritario;
    private String email;
    private String password;

    public Cliente(String nomeProprio, String apelido, String contacto, boolean utilizadorPrioritario, String email, String password) {
        this.nomeProprio = nomeProprio;
        this.apelido = apelido;
        this.contacto = contacto;
        this.utilizadorPrioritario = utilizadorPrioritario;
        this.email = email;
        this.password = password;
    }

    // Getters e Setters para todos os campos
    public String getNomeProprio() {
        return nomeProprio;
    }

    public void setNomeProprio(String nomeProprio) {
        this.nomeProprio = nomeProprio;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public boolean isUtilizadorPrioritario() {
        return utilizadorPrioritario;
    }

    public void setUtilizadorPrioritario(boolean utilizadorPrioritario) {
        this.utilizadorPrioritario = utilizadorPrioritario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Método de autenticação
    public boolean autenticar(String senha) {
        return this.password.equals(senha);
    }

    // Método toString para facilitar a visualização
    @Override
    public String toString() {
        return "Cliente{" +
                "nomeProprio='" + nomeProprio + '\'' +
                ", apelido='" + apelido + '\'' +
                ", contacto='" + contacto + '\'' +
                ", utilizadorPrioritario=" + utilizadorPrioritario +
                ", email='" + email + '\'' +
                '}';
    }

  
}