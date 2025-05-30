package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;


@WebServlet("/importRoutes")
@MultipartConfig
public class ImportRoutesServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Part filePart = request.getPart("routesFile");
        InputStream fileContent = filePart.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(fileContent));

        String line;
        boolean skipHeader = true;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/teubanco", "utilizador", "senha");

            PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO routes (route_id, agency_id, short_name, long_name, route_type) " +
                "VALUES (?, ?, ?, ?, ?)");

            while ((line = reader.readLine()) != null) {
                if (skipHeader) {
                    skipHeader = false;
                    continue;
                }

                String[] parts = line.split(",", -1);
                if (parts.length < 5) continue;

                stmt.setString(1, parts[0].trim());
                stmt.setString(2, parts[1].trim());
                stmt.setString(3, parts[2].trim());
                stmt.setString(4, parts[3].trim());
                stmt.setInt(5, Integer.parseInt(parts[4].trim()));
                stmt.executeUpdate();
            }

            stmt.close();
            conn.close();

            response.sendRedirect("adminRoutes.jsp?import=success");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("adminRoutes.jsp?import=error");
        }
    }
}
