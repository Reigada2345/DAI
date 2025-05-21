package models;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class ServidorLogin {

    public static void main(String[] args) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
            System.out.println("Servidor iniciado na porta 8000");

            server.createContext("/login", new LoginHandler());
            server.createContext("/register", new RegisterHandler());

            server.setExecutor(null);
            server.start();
        } catch (IOException e) {
            System.err.println("Erro ao iniciar o servidor: " + e.getMessage());
        }
    }

    public static void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(statusCode, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }

    static class LoginHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");

            if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(200, -1);
                return;
            }

            if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8));
                StringBuilder body = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    body.append(line);
                }

                String email = "", password = "";
                String[] params = body.toString().split("&");
                for (String param : params) {
                    String[] keyValue = param.split("=", 2);
                    String key = URLDecoder.decode(keyValue[0], StandardCharsets.UTF_8);
                    String value = keyValue.length > 1 ? URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8) : "";
                    if (key.equals("email")) email = value;
                    if (key.equals("password")) password = value;
                }

                if (email.isEmpty() || password.isEmpty()) {
                    sendResponse(exchange, 400, "Email ou senha não podem estar vazios");
                    return;
                }

                try {
                    ClienteDAO clienteDAO = new ClienteDAO();
                    boolean valid = clienteDAO.validarLogin(email, password);
                    String response = valid ? "success" : "erro";
                    sendResponse(exchange, 200, response);
                } catch (IOException e) {
                    sendResponse(exchange, 500, "Erro de IO: " + e.getMessage());
                } catch (RuntimeException e) {
                    sendResponse(exchange, 500, "Erro de execução: " + e.getMessage());
                }
            } else {
                sendResponse(exchange, 405, "Método não suportado");
            }
        }
    }

    static class RegisterHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "POST, OPTIONS");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");

            if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(200, -1);
                return;
            }

            if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(405, -1);
                return;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8));
            StringBuilder body = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                body.append(line);
            }

            String email = "", password = "", firstName = "", lastName = "", contact = "";
            boolean utilizadorPrioritario = false;

            String[] params = body.toString().split("&");
            for (String param : params) {
                String[] keyValue = param.split("=", 2);
                String key = URLDecoder.decode(keyValue[0], StandardCharsets.UTF_8);
                String value = keyValue.length > 1 ? URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8) : "";

                switch (key) {
                    case "email" -> email = value;
                    case "password" -> password = value;
                    case "firstName" -> firstName = value;
                    case "lastName" -> lastName = value;
                    case "contact" -> contact = value;
                    case "utilizadorPrioritario" -> utilizadorPrioritario = value.equalsIgnoreCase("true");
                }
            }

            if (email.isEmpty() || password.isEmpty()) {
                sendResponse(exchange, 400, "Campos obrigatórios faltando");
                return;
            }

            try {
                Cliente cliente = new Cliente(firstName, lastName, contact, utilizadorPrioritario, email, password);
                ClienteDAO dao = new ClienteDAO();
                boolean sucesso = dao.adicionarCliente(cliente);

                String resposta = sucesso ? "success" : "erro";
                sendResponse(exchange, 200, resposta);

            } catch (IOException e) {
                sendResponse(exchange, 500, "Erro de IO: " + e.getMessage());
            } catch (RuntimeException e) {
                sendResponse(exchange, 500, "Erro de execução: " + e.getMessage());
            }
        }
    }
}
