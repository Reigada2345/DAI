package servlets;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Route;

import java.io.IOException;
import java.sql.*;
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
