
package models;
public class Cliente {
  

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