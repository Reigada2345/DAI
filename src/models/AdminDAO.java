package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAO {

    // REGISTAR NOVO ADMIN
    public boolean adicionarAdmin(Admin admin) {
        String sql = "INSERT INTO admins (nome_proprio, apelido, contacto, email, password, utilizador_prioritario) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, admin.getNomeProprio());
            stmt.setString(2, admin.getApelido());
            stmt.setString(3, admin.getContacto());
            stmt.setString(4, admin.getEmail());
            stmt.setString(5, admin.getPassword());
            stmt.setBoolean(6, admin.isUtilizadorPrioritario());

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Erro ao adicionar admin: " + e.getMessage());
            return false;
        }
    }

    // VALIDAR LOGIN
    public boolean validarLogin(String email, String password) {
        String sql = "SELECT * FROM admins WHERE email = ? AND password = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            return rs.next();

        } catch (SQLException e) {
            System.err.println("Erro ao validar login do admin: " + e.getMessage());
            return false;
        }
    }

    // BUSCAR ADMIN PELO EMAIL
    public Admin buscarPorEmail(String email) {
        String sql = "SELECT * FROM admins WHERE email = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Admin admin = new Admin(
                    rs.getString("nome_proprio"),
                    rs.getString("apelido"),
                    rs.getString("contacto"),
                    rs.getBoolean("utilizador_prioritario"),
                    rs.getString("email"),
                    rs.getString("password")
                );
                return admin;
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar admin: " + e.getMessage());
        }

        return null;
    }
}
