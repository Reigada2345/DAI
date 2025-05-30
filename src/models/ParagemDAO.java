package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ParagemDAO {

    // ADICIONAR NOVA PARAGEM
    public boolean adicionarParagem(Paragem paragem) {
        String sql = "INSERT INTO paragens (nome, localizacao, varias_rotas, lotacao, ativa) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, paragem.getNome());
            stmt.setString(2, paragem.getLocalizacao());
            stmt.setBoolean(3, paragem.getVariasRotas());
            stmt.setInt(4, paragem.getLotacao());
            stmt.setBoolean(5, paragem.isAtiva());

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Erro ao adicionar paragem: " + e.getMessage());
            return false;
        }
    }

    // LISTAR TODAS AS PARAGENS
    public ArrayList<Paragem> listarParagens() {
        ArrayList<Paragem> paragens = new ArrayList<>();
        String sql = "SELECT * FROM paragens";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Paragem paragem = new Paragem(
                    rs.getString("nome"),
                    rs.getString("localizacao"),
                    rs.getBoolean("varias_rotas"),
                    rs.getInt("lotacao")
                );
                paragem.setAtiva(rs.getBoolean("ativa"));
                paragens.add(paragem);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar paragens: " + e.getMessage());
        }

        return paragens;
    }
    
    // EDITAR PARAGEM
    public boolean editarParagem(String nomeOriginal, Paragem novaParagem) {
        String sql = "UPDATE paragens SET nome = ?, localizacao = ?, varias_rotas = ?, lotacao = ? WHERE nome = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, novaParagem.getNome());
            stmt.setString(2, novaParagem.getLocalizacao());
            stmt.setBoolean(3, novaParagem.getVariasRotas());
            stmt.setInt(4, novaParagem.getLotacao());
            stmt.setString(5, nomeOriginal);

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Erro ao editar paragem: " + e.getMessage());
            return false;
        }
    }

    // DESATIVAR PARAGEM (não apaga, apenas altera o estado)
    public boolean desativarParagem(String nomeParagem) {
        String sql = "UPDATE paragens SET ativa = false WHERE nome = ? AND ativa = true";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nomeParagem);
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                return true; // desativação bem-sucedida
            } else {
                System.out.println("Paragem já está desativada ou não existe.");
                return false;
            }

        } catch (SQLException e) {
            System.err.println("Erro ao desativar paragem: " + e.getMessage());
            return false;
        }
    }

    // APAGAR PARAGEM (apenas se estiver desativada)
    public boolean apagarParagem(String nomeParagem) {
        String verificarSql = "SELECT ativa FROM paragens WHERE nome = ?";
        String apagarSql = "DELETE FROM paragens WHERE nome = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement verificarStmt = conn.prepareStatement(verificarSql)) {

            verificarStmt.setString(1, nomeParagem);
            ResultSet rs = verificarStmt.executeQuery();

            if (rs.next()) {
                boolean ativa = rs.getBoolean("ativa");
                if (!ativa) {
                    try (PreparedStatement apagarStmt = conn.prepareStatement(apagarSql)) {
                        apagarStmt.setString(1, nomeParagem);
                        apagarStmt.executeUpdate();
                        return true;
                    }
                } else {
                    System.out.println("Não é possível apagar: a paragem está ativa.");
                    return false;
                }
            } else {
                System.out.println("Paragem não encontrada.");
                return false;
            }

        } catch (SQLException e) {
            System.err.println("Erro ao apagar paragem: " + e.getMessage());
            return false;
        }
    }

    // OBTER PARAGEM PELO NOME
    public Paragem buscarParagemPorNome(String nome) {
        String sql = "SELECT * FROM paragens WHERE nome = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Paragem paragem = new Paragem(
                    rs.getString("nome"),
                    rs.getString("localizacao"),
                    rs.getBoolean("varias_rotas"),
                    rs.getInt("lotacao")
                );
                paragem.setAtiva(rs.getBoolean("ativa"));
                return paragem;
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar paragem: " + e.getMessage());
        }

        return null;
    }
}
