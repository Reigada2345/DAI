package models;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


public class ServidorLogin {

    public static void main(String[] args) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
            System.out.println("Servidor iniciado na porta 8000");

            server.createContext("/login", new LoginHandler());
            server.createContext("/register", new RegisterHandler());
            server.createContext("/perfil", new PerfilHandler());


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
                    AdminDAO adminDAO = new AdminDAO();

                    boolean validCliente = clienteDAO.validarLogin(email, password);
                    boolean validAdmin = adminDAO.validarLogin(email, password);

                    if (validCliente) {
                        sendResponse(exchange, 200, "success_cliente");
                    } else if (validAdmin) {
                        sendResponse(exchange, 200, "success_admin");
                    } else {
                        sendResponse(exchange, 401, "erro_login");
                    }
                } catch (IOException e) {
                    sendResponse(exchange, 500, "Erro de IO: " + e.getMessage());
                } catch (RuntimeException e) {
                    sendResponse(exchange, 500, "Erro de execução: " + e.getMessage());
                }
            }
        }
    }

    static class PerfilHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, OPTIONS");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
            exchange.getResponseHeaders().add("Content-Type", "application/json; charset=utf-8");


            if ("OPTIONS".equals(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(204, -1);
                return;
            }
        if ("GET".equals(exchange.getRequestMethod())) {

                // Extrair query string
                URI requestURI = exchange.getRequestURI();
                String query = requestURI.getRawQuery();
                

                Map<String, String> params = queryToMap(query);
                String email = params.get("email");


                if (email == null || email.isEmpty()) {
                    sendResponse(exchange, 401, "{\"erro\": \"Email não fornecido\"}");
                    return;
                }

                try {
                    ClienteDAO clienteDAO = new ClienteDAO();
                    Cliente cliente = clienteDAO.buscarPorEmail(email);

                    if (cliente != null) {
                        String json = "{"
                            + "\"tipoUtilizador\":\"Cliente\","
                            + "\"nome\":\"" + escapeJson(cliente.getNomeProprio()) + "\","
                            + "\"apelido\":\"" + escapeJson(cliente.getApelido()) + "\","
                            + "\"contacto\":\"" + escapeJson(cliente.getContacto()) + "\","
                            + "\"email\":\"" + escapeJson(cliente.getEmail()) + "\","
                            + "\"prioritario\":\"" + (cliente.isUtilizadorPrioritario() ? "Sim" : "Não") + "\""
                            + "}";
                        sendResponse(exchange, 200, json);
                        return;
                    }

                    AdminDAO adminDAO = new AdminDAO();
                    Admin admin = adminDAO.buscarPorEmail(email);

                    if (admin != null) {
                    String json = "{"
                        + "\"tipoUtilizador\":\"Admin\","
                        + "\"nome\":\"" + escapeJson(admin.getNomeProprio()) + "\","
                        + "\"apelido\":\"" + escapeJson(admin.getApelido()) + "\","
                        + "\"contacto\":\"" + escapeJson(admin.getContacto()) + "\","
                        + "\"email\":\"" + escapeJson(admin.getEmail()) + "\""
                        + "}";
                    sendResponse(exchange, 200, json);
                    return;
                }


                    sendResponse(exchange, 404, "{\"erro\":\"Utilizador não encontrado\"}");

                } catch (SQLException e) {
                    sendResponse(exchange, 500, "{\"erro\":\"Erro interno ao procurar utilizador: " + escapeJson(e.getMessage()) + "\"}");
                } catch (IOException e) {
                    sendResponse(exchange, 500, "{\"erro\":\"Erro de IO: " + escapeJson(e.getMessage()) + "\"}");
                } catch (RuntimeException e) {
                    sendResponse(exchange, 500, "{\"erro\":\"Erro de execução: " + escapeJson(e.getMessage()) + "\"}");
                }
            } 
        else {
            sendResponse(exchange, 405, "{\"erro\": \"Método não suportado\"}");
            }
    }

    private Map<String, String> queryToMap(String query) {
        Map<String, String> result = new HashMap<>();
        if (query == null) return result;
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            try {
                String key = idx > 0 ? URLDecoder.decode(pair.substring(0, idx), StandardCharsets.UTF_8.name()) : pair;
                String value = idx > 0 && pair.length() > idx + 1 ? URLDecoder.decode(pair.substring(idx + 1), StandardCharsets.UTF_8.name()) : null;
                result.put(key, value);
            } catch (UnsupportedEncodingException | IllegalArgumentException e) {
                // Ignorar erros
            }
        }
        return result;
    }

    private String escapeJson(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    private void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "application/json; charset=utf-8");
        exchange.sendResponseHeaders(statusCode, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }
}

    static class RegisterHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "POST, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
        exchange.getResponseHeaders().add("Content-Type", "application/json; charset=utf-8");



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

        String email = "", password = "", firstName = "", lastName = "", contact = "", tipoUtilizador = "";
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
                case "tipoUtilizador" -> tipoUtilizador = value.toLowerCase();
            }
        }

        if (email.isEmpty() || password.isEmpty() || tipoUtilizador.isEmpty()) {
            sendResponse(exchange, 400, "Campos obrigatórios faltando");
            return;
        }

        try {
           
                    Cliente cliente = new Cliente(firstName, lastName, contact, utilizadorPrioritario, email, password);
                    ClienteDAO dao = new ClienteDAO();
                    boolean sucesso = dao.adicionarCliente(cliente);
                    String jsonResponse = "{\"status\": \"" + (sucesso ? "success" : "erro") + "\"}";
                    exchange.getResponseHeaders().add("Content-Type", "application/json; charset=utf-8");
                    sendResponse(exchange, 200, jsonResponse);


                
                
        } catch (IOException e) {
            sendResponse(exchange, 500, "Erro de IO ao registar: " + e.getMessage());
        } catch (RuntimeException e) {
            sendResponse(exchange, 500, "Erro de execução ao registar: " + e.getMessage());
        }
    }
}
}