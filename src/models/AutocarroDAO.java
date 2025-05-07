package models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AutocarroDAO {
    private Connection conn;

    public AutocarroDAO() {
        this.conn = DatabaseConnection.connect();
    }

    public Connection getConnection() {
        return this.conn;
    }

    // Método genérico para obter valores a partir de uma query com um único resultado
    private float getFloatValue(String sql, int autocarroId) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, autocarroId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getFloat(1);
            }
        }
        return 0.0f; // Retorna 0.0 caso não encontre ou ocorra erro
    }

    // Método genérico para obter valores inteiros
    private int getIntValue(String sql, int autocarroId) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, autocarroId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0; // Retorna 0 caso não encontre ou ocorra erro
    }

    // Método para adicionar um autocarro
    public void adicionarAutocarro(String matricula, String modelo, int capacidade, String numero, String rota, float temperatura, int lotacao, boolean ativo) {
        String sql = "INSERT INTO autocarros (matricula, modelo, capacidade, numero, rota, temperatura, lotacao, ativo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, matricula);
            pstmt.setString(2, modelo);
            pstmt.setInt(3, capacidade);
            pstmt.setString(4, numero);
            pstmt.setString(5, rota);
            pstmt.setFloat(6, temperatura);
            pstmt.setInt(7, lotacao);
            pstmt.setBoolean(8, ativo);
            pstmt.executeUpdate();
            System.out.println("Autocarro inserido com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao inserir autocarro: " + e.getMessage());
        }
    }

    // Método para listar todos os autocarros
    public List<Autocarro> listarAutocarros() {
        List<Autocarro> lista = new ArrayList<>();
        String sql = "SELECT * FROM autocarros";

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
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

    // Método para atualizar informações de um autocarro
    public void atualizarAutocarro(int id, String modelo, int capacidade, String numero, String rota, float temperatura, int lotacao, boolean ativo) {
        String sql = "UPDATE autocarros SET modelo = ?, capacidade = ?, numero = ?, rota = ?, temperatura = ?, lotacao = ?, ativo = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, modelo);
            pstmt.setInt(2, capacidade);
            pstmt.setString(3, numero);
            pstmt.setString(4, rota);
            pstmt.setFloat(5, temperatura);
            pstmt.setInt(6, lotacao);
            pstmt.setBoolean(7, ativo);
            pstmt.setInt(8, id);
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

    // Método para atualizar a temperatura de um autocarro
    public void atualizarTemperatura(int id, float novaTemperatura) {
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

    // Método para atualizar a lotação de um autocarro
    public void atualizarLotacao(Connection conn1, int id, int novaLotacao) {
        String sql = "UPDATE autocarros SET lotacao = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, novaLotacao);
            pstmt.setInt(2, id);
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Lotação do autocarro atualizada com sucesso!");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar lotação: " + e.getMessage());
        }
    }

    // Método para obter a temperatura de um autocarro
    public float getTemperatura(int autocarroId) {
        String sql = "SELECT temperatura FROM autocarros WHERE id = ?";
        try {
            return getFloatValue(sql, autocarroId);
        } catch (SQLException e) {
            System.err.println("Erro ao obter temperatura: " + e.getMessage());
        }
        return 0.0f;
    }

    // Método para obter a lotação de um autocarro
    public int getLotacao(int autocarroId) {
        String sql = "SELECT lotacao FROM autocarros WHERE id = ?";
        try {
            return getIntValue(sql, autocarroId);
        } catch (SQLException e) {
            System.err.println("Erro ao obter lotação: " + e.getMessage());
        }
        return 0;
    }

// Método para obter a capacidade de um autocarro
    public int getCapacidade(int autocarroId) {
        String sql = "SELECT capacidade FROM autocarros WHERE id = ?";
        try {
            return getIntValue(sql, autocarroId);
        } catch (SQLException e) {
            System.err.println("Erro ao obter capacidade: " + e.getMessage());
        }
        return 0;
    }
}