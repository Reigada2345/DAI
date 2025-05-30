package servlets;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ReadCSVServlet {
    public class ReadCSVServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        String path = "C:\\Users\\ASUS\\Documents\\Universidade\\2o ano\\2o semestre\\DAI\\webapp\\src\\data\\stops.csv";

        List<String> lines = Files.readAllLines(Paths.get(path));

        // Por exemplo, enviar as linhas para a página JSP
        request.setAttribute("csvLines", lines);
        request.getRequestDispatcher("/showcsv.jsp").forward(request, response);
    }
}
}
