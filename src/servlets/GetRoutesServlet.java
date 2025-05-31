import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;

@WebServlet("/getRoutes")
public class GetRoutesServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bd_dai", "root", "1234")) {
            String sql = "SELECT * FROM routes";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Route> routes = new ArrayList<>();

            while (resultSet.next()) {
                Route route = new Route(
                    resultSet.getInt("route_id"),
                    resultSet.getString("agency_id"),
                    resultSet.getString("route_short_name"),
                    resultSet.getString("route_long_name"),
                    resultSet.getInt("route_type")
                );
                routes.add(route);
            }
            String json = new Gson().toJson(routes);
            response.getWriter().write(json);
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}