package models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import database.DatabaseConnection; 

public class AutocarroDAO {
    private Connection conn;
   

    public AutocarroDAO() {
        this.conn = DatabaseConnection.connect();
    }
    public Connection getConnection() {
        return conn;
    }
    public int getCapacidade(int autocarroId) {
        String query = "SELECT capacidade FROM autocarros WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, autocarroId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("capacidade");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // Retorna 0 se não encontrar o autocarro
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
                        rs.getInt("lotacao"),
                        rs.getFloat("temperatura"),
                        rs.getString("numero"),
                        rs.getInt("capacidade"),
                        rs.getString("modelo"),
                        rs.getString("rota"),
                        rs.getString("matricula"),
                        rs.getBoolean("ativo")
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
    // Retorna a temperatura atual de um autocarro
public float getTemperatura(Connection conn, int autocarroId) {
    String query = "SELECT temperatura FROM autocarros WHERE id = ?";
    try (PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setInt(1, autocarroId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getFloat("temperatura");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return 0.0f; // Retorna 0.0 se não encontrar ou der erro
}

// Retorna a lotação atual de um autocarro
public int getLotacao(Connection conn, int autocarroId) {
    String query = "SELECT lotacao FROM autocarros WHERE id = ?";
    try (PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setInt(1, autocarroId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt("lotacao");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return 0; // Retorna 0 se não encontrar ou der erro
}

}
