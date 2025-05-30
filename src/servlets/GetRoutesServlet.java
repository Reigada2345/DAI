import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

import java.io.*;
import java.sql.*;

@WebServlet("/api/routes") // URL para o fetch do JS
public class GetRoutesServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String dbUrl = "jdbc:mysql://localhost:3306/NOME_DA_TUA_BASE";
        String dbUser = "root";
        String dbPass = "SENHA";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM routes");

            StringBuilder json = new StringBuilder("[");
            while (rs.next()) {
                json.append("{")
                    .append("\"route_id\":\"").append(rs.getString("route_id")).append("\",")
                    .append("\"short_name\":\"").append(rs.getString("route_short_name")).append("\",")
                    .append("\"long_name\":\"").append(rs.getString("route_long_name")).append("\",")
                    .append("\"image\":\"").append(rs.getString("image_path")).append("\"")
                    .append("},");
            }

            if (json.length() > 1) {
                json.setLength(json.length() - 1); // remove vírgula final
            }

            json.append("]");
            out.print(json.toString());

            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            out.print("[]"); // resposta vazia em caso de erro
        }
    }
}

