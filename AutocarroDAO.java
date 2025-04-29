import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import models.Autocarro;

public class AutocarroDAO {
    private Connection conn;

    public AutocarroDAO() {
        this.conn = DatabaseConnection.connect();
    }
    
    public Connection getConnection() {
        return this.conn;
    }

    // Novo método adicionado
    public int getCapacidade(int autocarroId) {
        String sql = "SELECT capacidade FROM autocarros WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, autocarroId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("capacidade");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao obter capacidade: " + e.getMessage());
        }
        return 0;
    }
    // Método para adicionar um autocarro
    public void adicionarAutocarro(String matricula, String modelo, int capacidade) {
        String sql = "INSERT INTO autocarros (matricula, modelo, capacidade) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, matricula);
            pstmt.setString(2, modelo);
            pstmt.setInt(3, capacidade);
            pstmt.executeUpdate();
            System.out.println("Autocarro inserido com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao inserir autocarro: " + e.getMessage());
        }
    }

    // Método para listar todos os autocarros
    public List<Autocarro> listarAutocarros(Connection conn) {
        List<Autocarro> lista = new ArrayList<>();
        String sql = "SELECT * FROM autocarros";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Autocarro a = new Autocarro(
                        rs.getInt("id"),
                        rs.getString("matricula"),
                        rs.getString("modelo"),
                        rs.getFloat("temperatura"),
                        rs.getInt("capacidade"),
                        rs.getInt("lotacao")
                );
                lista.add(a);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar autocarros: " + e.getMessage());
        }
        return lista;
    }

    public void atualizarAutocarro(int id, String modelo, int capacidade) {
        String sql = "UPDATE autocarros SET modelo = ?, capacidade = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, modelo);
            pstmt.setInt(2, capacidade);
            pstmt.setInt(3, id);
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Autocarro atualizado com sucesso!");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar autocarro: " + e.getMessage());
        }
    }

    // Método para apagar um autocarro
    public void apagarAutocarro(int id) {
        String sql = "DELETE FROM autocarros WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Autocarro apagado com sucesso!");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao apagar autocarro: " + e.getMessage());
        }
    }
    public static void atualizarTemperatura(Connection conn, int id, float novaTemperatura) {
        String sql = "UPDATE autocarros SET temperatura = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setFloat(1, novaTemperatura);
            pstmt.setInt(2, id);
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Temperatura do autocarro atualizada com sucesso!");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar temperatura: " + e.getMessage());
        }
    }
    public static void atualizarLotacao(Connection conn, int id, int novaLotacao) {
        String sql = "UPDATE autocarros SET lotacao = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, novaLotacao);
            pstmt.setInt(2, id);
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
       
            }
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar lotação: " + e.getMessage());
        }
    }
}
