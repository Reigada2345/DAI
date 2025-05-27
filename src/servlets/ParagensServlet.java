package servlets;

import java.io.*;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import com.google.gson.Gson;

@WebServlet("/api/paragens/*")
public class ParagensServlet extends HttpServlet {
    // Configuração MySQL - ajuste conforme suas credenciais
    private static final String DB_URL = "jdbc:mysql://localhost:3306/bus_management?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "sua_password";
    
    private Gson gson = new Gson();
    
    @Override
    public void init() throws ServletException {
        try {
            // Carrega o driver MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Cria a base de dados se não existir
            createDatabaseIfNotExists();
            
            // Cria a tabela de paragens
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String sql = """
                    CREATE TABLE IF NOT EXISTS paragens (
                        stop_id INT PRIMARY KEY,
                        stop_name VARCHAR(255) NOT NULL,
                        stop_lat DECIMAL(10, 8) NOT NULL,
                        stop_lon DECIMAL(11, 8) NOT NULL,
                        zone_id INT NOT NULL,
                        ativa BOOLEAN DEFAULT TRUE,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                        created_by VARCHAR(100),
                        INDEX idx_zone (zone_id),
                        INDEX idx_ativa (ativa),
                        INDEX idx_name (stop_name)
                    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
                """;
                conn.createStatement().execute(sql);
                System.out.println("✅ Tabela de paragens criada/verificada com sucesso!");
                
                // Cria tabela de auditoria para histórico de alterações
                String auditSql = """
                    CREATE TABLE IF NOT EXISTS paragens_audit (
                        audit_id INT AUTO_INCREMENT PRIMARY KEY,
                        stop_id INT NOT NULL,
                        action_type ENUM('INSERT', 'UPDATE', 'DELETE') NOT NULL,
                        old_data JSON,
                        new_data JSON,
                        user_id VARCHAR(100),
                        action_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        INDEX idx_stop_id (stop_id),
                        INDEX idx_timestamp (action_timestamp)
                    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
                """;
                conn.createStatement().execute(auditSql);
                System.out.println("✅ Tabela de auditoria criada/verificada com sucesso!");
            }
        } catch (Exception e) {
            System.err.println("❌ Erro ao inicializar base de dados: " + e.getMessage());
            throw new ServletException("Erro ao inicializar base de dados", e);
        }
    }
    
    private void createDatabaseIfNotExists() throws SQLException {
        String baseUrl = "jdbc:mysql://localhost:3306/?useSSL=false&serverTimezone=UTC";
        try (Connection conn = DriverManager.getConnection(baseUrl, DB_USER, DB_PASSWORD)) {
            String sql = "CREATE DATABASE IF NOT EXISTS bus_management CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci";
            conn.createStatement().execute(sql);
            System.out.println("✅ Base de dados verificada/criada!");
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        
        String pathInfo = request.getPathInfo();
        
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            
            // Se for uma consulta específica por ID
            if (pathInfo != null && pathInfo.length() > 1) {
                int stopId = Integer.parseInt(pathInfo.substring(1));
                getParagem(conn, stopId, response);
                return;
            }
            
            // Parâmetros de filtro
            String zona = request.getParameter("zona");
            String search = request.getParameter("search");
            String ativas = request.getParameter("ativas");
            
            StringBuilder sql = new StringBuilder("SELECT * FROM paragens WHERE 1=1");
            List<Object> params = new ArrayList<>();
            
            // Filtro por zona
            if (zona != null && !zona.isEmpty() && !zona.equals("todas")) {
                sql.append(" AND zone_id = ?");
                params.add(Integer.parseInt(zona));
            }
            
            // Filtro por pesquisa
            if (search != null && !search.trim().isEmpty()) {
                sql.append(" AND (stop_name LIKE ? OR stop_id = ?)");
                params.add("%" + search.trim() + "%");
                try {
                    params.add(Integer.parseInt(search.trim()));
                } catch (NumberFormatException e) {
                    params.add(-1); // ID impossível se não for número
                }
            }
            
            // Filtro por ativas (por defeito só mostra ativas)
            if (ativas == null || !ativas.equals("todas")) {
                sql.append(" AND ativa = TRUE");
            }
            
            sql.append(" ORDER BY zone_id, stop_name");
            
            PreparedStatement stmt = conn.prepareStatement(sql.toString());
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }
            
            ResultSet rs = stmt.executeQuery();
            List<Map<String, Object>> paragens = new ArrayList<>();
            
            while (rs.next()) {
                Map<String, Object> paragem = new HashMap<>();
                paragem.put("stop_id", rs.getInt("stop_id"));
                paragem.put("stop_name", rs.getString("stop_name"));
                paragem.put("stop_lat", rs.getDouble("stop_lat"));
                paragem.put("stop_lon", rs.getDouble("stop_lon"));
                paragem.put("zone_id", rs.getInt("zone_id"));
                paragem.put("ativa", rs.getBoolean("ativa"));
                paragem.put("created_at", rs.getTimestamp("created_at"));
                paragem.put("updated_at", rs.getTimestamp("updated_at"));
                paragem.put("created_by", rs.getString("created_by"));
                paragens.add(paragem);
            }
            
            Map<String, Object> resultado = new HashMap<>();
            resultado.put("paragens", paragens);
            resultado.put("total", paragens.size());
            resultado.put("filtros", Map.of(
                "zona", zona != null ? zona : "todas",
                "search", search != null ? search : "",
                "ativas", ativas != null ? ativas : "ativas"
            ));
            
            System.out.println("📤 Enviando " + paragens.size() + " paragens");
            response.getWriter().write(gson.toJson(resultado));
            
        } catch (SQLException e) {
            System.err.println("❌ Erro SQL: " + e.getMessage());
            response.setStatus(500);
            response.getWriter().write("{\"error\":\"Erro ao obter paragens: " + e.getMessage() + "\"}");
        } catch (NumberFormatException e) {
            response.setStatus(400);
            response.getWriter().write("{\"error\":\"Parâmetros inválidos\"}");
        }
    }
    
    private void getParagem(Connection conn, int stopId, HttpServletResponse response) throws IOException {
        try {
            String sql = "SELECT * FROM paragens WHERE stop_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, stopId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Map<String, Object> paragem = new HashMap<>();
                paragem.put("stop_id", rs.getInt("stop_id"));
                paragem.put("stop_name", rs.getString("stop_name"));
                paragem.put("stop_lat", rs.getDouble("stop_lat"));
                paragem.put("stop_lon", rs.getDouble("stop_lon"));
                paragem.put("zone_id", rs.getInt("zone_id"));
                paragem.put("ativa", rs.getBoolean("ativa"));
                paragem.put("created_at", rs.getTimestamp("created_at"));
                paragem.put("updated_at", rs.getTimestamp("updated_at"));
                paragem.put("created_by", rs.getString("created_by"));
                
                response.getWriter().write(gson.toJson(paragem));
            } else {
                response.setStatus(404);
                response.getWriter().write("{\"error\":\"Paragem não encontrada\"}");
            }
        } catch (SQLException e) {
            response.setStatus(500);
            response.getWriter().write("{\"error\":\"Erro ao obter paragem: " + e.getMessage() + "\"}");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        
        try {
            StringBuilder sb = new StringBuilder();
            String line;
            try (BufferedReader reader = request.getReader()) {
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            }
            
            Map<String, Object> paragem = gson.fromJson(sb.toString(), Map.class);
            String userId = request.getHeader("User-ID"); // Assumindo que vem no header
            
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                conn.setAutoCommit(false);
                
                // Verifica se a paragem já existe
                String checkSql = "SELECT stop_id FROM paragens WHERE stop_id = ?";
                PreparedStatement checkStmt = conn.prepareStatement(checkSql);
                checkStmt.setInt(1, ((Double) paragem.get("stop_id")).intValue());
                ResultSet rs = checkStmt.executeQuery();
                
                boolean isUpdate = rs.next();
                String action = isUpdate ? "UPDATE" : "INSERT";
                
                String sql;
                if (isUpdate) {
                    sql = "UPDATE paragens SET stop_name = ?, stop_lat = ?, stop_lon = ?, zone_id = ?, updated_at = CURRENT_TIMESTAMP WHERE stop_id = ?";
                } else {
                    sql = "INSERT INTO paragens (stop_name, stop_lat, stop_lon, zone_id, stop_id, created_by) VALUES (?, ?, ?, ?, ?, ?)";
                }
                
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, (String) paragem.get("stop_name"));
                stmt.setDouble(2, (Double) paragem.get("stop_lat"));
                stmt.setDouble(3, (Double) paragem.get("stop_lon"));
                stmt.setInt(4, ((Double) paragem.get("zone_id")).intValue());
                stmt.setInt(5, ((Double) paragem.get("stop_id")).intValue());
                
                if (!isUpdate) {
                    stmt.setString(6, userId);
                }
                
                int affected = stmt.executeUpdate();
                
                if (affected > 0) {
                    // Regista na auditoria
                    registarAuditoria(conn, ((Double) paragem.get("stop_id")).intValue(), action, null, paragem, userId);
                    
                    conn.commit();
                    System.out.println("✅ Paragem " + (isUpdate ? "atualizada" : "criada") + ": " + paragem.get("stop_name"));
                    
                    Map<String, Object> resultado = new HashMap<>();
                    resultado.put("success", true);
                    resultado.put("message", "Paragem " + (isUpdate ? "atualizada" : "criada") + " com sucesso!");
                    resultado.put("action", action.toLowerCase());
                    resultado.put("stop_id", paragem.get("stop_id"));
                    
                    response.getWriter().write(gson.toJson(resultado));
                } else {
                    conn.rollback();
                    response.setStatus(500);
                    response.getWriter().write("{\"error\":\"Nenhuma linha foi afetada\"}");
                }
                
            } catch (SQLException e) {
                System.err.println("❌ Erro SQL: " + e.getMessage());
                response.setStatus(500);
                response.getWriter().write("{\"error\":\"Erro ao guardar paragem: " + e.getMessage() + "\"}");
            }
            
        } catch (Exception e) {
            System.err.println("❌ Erro geral: " + e.getMessage());
            response.setStatus(400);
            response.getWriter().write("{\"error\":\"Dados inválidos: " + e.getMessage() + "\"}");
        }
    }
    
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.length() <= 1) {
            response.setStatus(400);
            response.getWriter().write("{\"error\":\"ID da paragem necessário\"}");
            return;
        }
        
        try {
            int stopId = Integer.parseInt(pathInfo.substring(1));
            String userId = request.getHeader("User-ID");
            
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                conn.setAutoCommit(false);
                
                // Obtém dados antes de "eliminar"
                String selectSql = "SELECT * FROM paragens WHERE stop_id = ? AND ativa = TRUE";
                PreparedStatement selectStmt = conn.prepareStatement(selectSql);
                selectStmt.setInt(1, stopId);
                ResultSet rs = selectStmt.executeQuery();
                
                if (rs.next()) {
                    Map<String, Object> oldData = new HashMap<>();
                    oldData.put("stop_id", rs.getInt("stop_id"));
                    oldData.put("stop_name", rs.getString("stop_name"));
                    oldData.put("stop_lat", rs.getDouble("stop_lat"));
                    oldData.put("stop_lon", rs.getDouble("stop_lon"));
                    oldData.put("zone_id", rs.getInt("zone_id"));
                    oldData.put("ativa", rs.getBoolean("ativa"));
                    
                    // Soft delete - marca como inativa
                    String sql = "UPDATE paragens SET ativa = FALSE, updated_at = CURRENT_TIMESTAMP WHERE stop_id = ?";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setInt(1, stopId);
                    
                    int updated = stmt.executeUpdate();
                    if (updated > 0) {
                        // Regista na auditoria
                        registarAuditoria(conn, stopId, "DELETE", oldData, null, userId);
                        
                        conn.commit();
                        System.out.println("🗑️ Paragem desativada: " + stopId);
                        response.getWriter().write("{\"success\":true,\"message\":\"Paragem desativada com sucesso!\"}");
                    } else {
                        conn.rollback();
                        response.setStatus(500);
                        response.getWriter().write("{\"error\":\"Erro ao desativar paragem\"}");
                    }
                } else {
                    response.setStatus(404);
                    response.getWriter().write("{\"error\":\"Paragem não encontrada ou já inativa\"}");
                }
                
            } catch (SQLException e) {
                System.err.println("❌ Erro SQL: " + e.getMessage());
                response.setStatus(500);
                response.getWriter().write("{\"error\":\"Erro ao eliminar paragem: " + e.getMessage() + "\"}");
            }
            
        } catch (NumberFormatException e) {
            response.setStatus(400);
            response.getWriter().write("{\"error\":\"ID inválido\"}");
        }
    }
    
    private void registarAuditoria(Connection conn, int stopId, String action, 
                                  Map<String, Object> oldData, Map<String, Object> newData, String userId) {
        try {
            String sql = "INSERT INTO paragens_audit (stop_id, action_type, old_data, new_data, user_id) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, stopId);
            stmt.setString(2, action);
            stmt.setString(3, oldData != null ? gson.toJson(oldData) : null);
            stmt.setString(4, newData != null ? gson.toJson(newData) : null);
            stmt.setString(5, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("⚠️ Erro ao registar auditoria: " + e.getMessage());
        }
    }
    
    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, User-ID");
        response.setStatus(200);
    }
}