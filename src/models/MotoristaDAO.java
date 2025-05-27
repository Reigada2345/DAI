package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MotoristaDAO {

    // REGISTAR NOVO MOTORISTA
    public boolean adicionarMotorista(Motorista motorista) {
        String sql = "INSERT INTO motoristas (nome_proprio, apelido, contacto, email, password) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, motorista.getNomeProprio());
            stmt.setString(2, motorista.getApelido());
            stmt.setString(3, motorista.getContacto());
            stmt.setString(4, motorista.getEmail());
            stmt.setString(5, motorista.getPassword());

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Erro ao adicionar motorista: " + e.getMessage());
            return false;
        }
    }

    // VALIDAR LOGIN
    public boolean validarLogin(String email, String password) {
        String sql = "SELECT * FROM motoristas WHERE email = ? AND password = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            return rs.next();

        } catch (SQLException e) {
            System.err.println("Erro ao validar login do motorista: " + e.getMessage());
            return false;
        }
    }

    // BUSCAR MOTORISTA PELO EMAIL
    public Motorista buscarPorEmail(String email) {
        String sql = "SELECT * FROM motoristas WHERE email = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Motorista motorista = new Motorista(
                    rs.getString("nome_proprio"),
                    rs.getString("apelido"),
                    rs.getString("contacto"),
                    rs.getBoolean("utilizador_prioritario"), // Substitua "algum_campo_booleano" pelo nome correto do campo booleano na sua tabela
                    rs.getString("email"),
                    rs.getString("password")
                );
                return motorista;
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar motorista: " + e.getMessage());
        }

        return null;
    }
}
