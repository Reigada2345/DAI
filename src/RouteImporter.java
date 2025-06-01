import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RouteImporter {

    public void importRoutes(String filePath, Connection connection) throws SQLException, IOException {
        String sql = "INSERT INTO routes (route_id, agency_id, route_short_name, route_long_name, route_type) VALUES (?, ?, ?, ?, ?)";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath));
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            String line = br.readLine(); // lê o cabeçalho e ignora
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                // Verifique se o número de colunas está correto
                if (data.length == 5) {
                    preparedStatement.setInt(1, Integer.parseInt(data[0]));
                    preparedStatement.setString(2, data[1]);
                    preparedStatement.setString(3, data[2]);
                    preparedStatement.setString(4, data[3]);
                    preparedStatement.setInt(5, Integer.parseInt(data[4]));
                    preparedStatement.executeUpdate();
                } else {
                    System.err.println("Formato de linha incorreto: " + line);
                }
            }
        }
    }
}
