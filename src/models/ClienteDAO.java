package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteDAO {

    // REGISTAR NOVO CLIENTE
    public boolean adicionarCliente(Cliente cliente) {
        String sql = "INSERT INTO clientes (nome_proprio, apelido, contacto, utilizador_prioritario, email, password) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNomeProprio());
            stmt.setString(2, cliente.getApelido());
            stmt.setString(3, cliente.getContacto());
            stmt.setBoolean(4, cliente.isUtilizadorPrioritario());
            stmt.setString(5, cliente.getEmail());
            stmt.setString(6, cliente.getPassword());

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Erro ao adicionar cliente: " + e.getMessage());
            return false;
        }
    }

    // VALIDAR LOGIN
    public boolean validarLogin(String email, String password) {
        String sql = "SELECT * FROM clientes WHERE email = ? AND password = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            System.err.println("Erro ao validar login: " + e.getMessage());
            return false;
        }
    }
}
