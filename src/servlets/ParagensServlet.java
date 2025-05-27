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
    private static final String DB_URL = "jdbc:sqlite:paragens.db";
    private Gson gson = new Gson();
    
    @Override
    public void init() throws ServletException {
        try {
            Class.forName("org.sqlite.JDBC");
            try (Connection conn = DriverManager.getConnection(DB_URL)) {
                String sql = """
                    CREATE TABLE IF NOT EXISTS paragens (
                        stop_id INTEGER PRIMARY KEY,
                        stop_name TEXT NOT NULL,
                        stop_lat REAL NOT NULL,
                        stop_lon REAL NOT NULL,
                        zone_id INTEGER NOT NULL,
                        ativa BOOLEAN DEFAULT 1,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                    )
                """;
                conn.createStatement().execute(sql);
                System.out.println("✅ Base de dados inicializada!");
            }
        } catch (Exception e) {
            throw new ServletException("Erro ao inicializar base de dados", e);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        
        List<Map<String, Object>> paragens = new ArrayList<>();
        
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "SELECT * FROM paragens WHERE ativa = 1 ORDER BY stop_id";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            
            while (rs.next()) {
                Map<String, Object> paragem = new HashMap<>();
                paragem.put("stop_id", rs.getInt("stop_id"));
                paragem.put("stop_name", rs.getString("stop_name"));
                paragem.put("stop_lat", rs.getDouble("stop_lat"));
                paragem.put("stop_lon", rs.getDouble("stop_lon"));
                paragem.put("zone_id", rs.getInt("zone_id"));
                paragem.put("ativa", rs.getBoolean("ativa"));
                paragens.add(paragem);
            }
            
            System.out.println("📤 Enviando " + paragens.size() + " paragens");
            response.getWriter().write(gson.toJson(paragens));
            
        } catch (SQLException e) {
            response.setStatus(500);
            response.getWriter().write("{\"error\":\"Erro ao obter paragens: " + e.getMessage() + "\"}");
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
            
            try (Connection conn = DriverManager.getConnection(DB_URL)) {
                String sql = "INSERT OR REPLACE INTO paragens (stop_id, stop_name, stop_lat, stop_lon, zone_id) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                
                stmt.setInt(1, ((Double) paragem.get("stop_id")).intValue());
                stmt.setString(2, (String) paragem.get("stop_name"));
                stmt.setDouble(3, (Double) paragem.get("stop_lat"));
                stmt.setDouble(4, (Double) paragem.get("stop_lon"));
                stmt.setInt(5, ((Double) paragem.get("zone_id")).intValue());
                
                stmt.executeUpdate();
                System.out.println("✅ Paragem guardada: " + paragem.get("stop_name"));
                response.getWriter().write("{\"success\":true,\"message\":\"Paragem guardada com sucesso!\"}");
                
            } catch (SQLException e) {
                response.setStatus(500);
                response.getWriter().write("{\"error\":\"Erro ao guardar paragem: " + e.getMessage() + "\"}");
            }
            
        } catch (Exception e) {
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
            
            try (Connection conn = DriverManager.getConnection(DB_URL)) {
                String sql = "UPDATE paragens SET ativa = 0 WHERE stop_id = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, stopId);
                
                int updated = stmt.executeUpdate();
                if (updated > 0) {
                    System.out.println("🗑️ Paragem eliminada: " + stopId);
                    response.getWriter().write("{\"success\":true,\"message\":\"Paragem eliminada com sucesso!\"}");
                } else {
                    response.setStatus(404);
                    response.getWriter().write("{\"error\":\"Paragem não encontrada\"}");
                }
                
            } catch (SQLException e) {
                response.setStatus(500);
                response.getWriter().write("{\"error\":\"Erro ao eliminar paragem: " + e.getMessage() + "\"}");
            }
            
        } catch (NumberFormatException e) {
            response.setStatus(400);
            response.getWriter().write("{\"error\":\"ID inválido\"}");
        }
    }
    
    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setStatus(200);
    }
}
