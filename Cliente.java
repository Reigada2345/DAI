
public class Cliente {
    private String email;
    private String password;

    public Cliente(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public boolean autenticar(String senha) {
        return this.password.equals(senha);
    }
}
