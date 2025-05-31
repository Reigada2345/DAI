import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/uploadRoutes")
@MultipartConfig
public class UploadRoutesServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException {
        Part filePart = request.getPart("file");
        if (filePart == null || filePart.getSize() == 0) {
            response.getWriter().write("Ficheiro vazio. Por favor, selecione um ficheiro válido.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(filePart.getInputStream()))) {
            String line;
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/seu_banco", "utilizador", "senha");
            String insertSQL = "INSERT INTO routes (route_id, agency_id, route_short_name, route_long_name, route_type) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);

            while ((line = reader.readLine()) != null) {
                if (!line.startsWith("route_id")) {  // Ignorar linha de cabeçalho
                    String[] data = line.split(",");
                    preparedStatement.setInt(1, Integer.parseInt(data[0]));
                    preparedStatement.setString(2, data[1]);
                    preparedStatement.setString(3, data[2]);
                    preparedStatement.setString(4, data[3]);
                    preparedStatement.setInt(5, Integer.parseInt(data[4]));
                    
                    preparedStatement.executeUpdate();
                }
            }
            preparedStatement.close();
            connection.close();
            response.getWriter().write("Rotas importadas com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("Erro ao importar rotas: " + e.getMessage());
        }
    }
}