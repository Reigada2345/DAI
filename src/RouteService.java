import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class RouteService {
public List<Route> getRoutes(Connection connection) throws SQLException {
        List<Route> routes = new ArrayList<>();
        String sql = "SELECT * FROM routes";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery()) {
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
        }
        return routes;
    }
}
