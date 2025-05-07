package models;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.URLDecoder;

public class ServidorLogin {

    public static void main(String[] args) throws IOException {
        
        int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "8080"));
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/login", new LoginHandler());
        server.setExecutor(null); // usa executor por defeito
        server.start();
        System.out.println("Servidor iniciado na porta " + port);
    }

    static class LoginHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException 
        {

            // Adicionando cabeçalhos CORS (permitir chamadas de qualquer origem)
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
             exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");

            // Responder à requisição OPTIONS (pré-flight do CORS)
            if ("OPTIONS".equals(exchange.getRequestMethod())) 
            {
                exchange.sendResponseHeaders(200, -1);
                return;
            }

            // Lidar com requisição POST
             if ("POST".equals(exchange.getRequestMethod())) 
             {
                InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
                BufferedReader br = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                String line;
                    while ((line = br.readLine()) != null) 
                    {
                         sb.append(line);
                    }
                String formData = sb.toString();


                if (formData == null || formData.isEmpty()) 
                {
                    sendErrorResponse(exchange, 400, "Dados inválidos");
                    return;
                }

                // Verificando se os dados estão no formato correto
                String[] params = formData.split("&");
                String email = "";
                String password = "";
                for (String param : params) 
                {
                    String[] keyValue = param.split("=");
                    if (keyValue.length == 2) {
                        if ("email".equals(keyValue[0])) {
                            email = URLDecoder.decode(keyValue[1], "UTF-8");
                        } else if ("password".equals(keyValue[0])) {
                            password = URLDecoder.decode(keyValue[1], "UTF-8");
                        }
                    }
                }

                if (email.isEmpty() || password.isEmpty()) 
                {
                    sendErrorResponse(exchange, 400, "Email ou senha não podem estar vazios");
                    return;
                }

                try {
                    boolean valido = false;
                    ClienteDAO clienteDAO = new ClienteDAO(); // Instanciando o ClienteDAO

                    // Usando o ClienteDAO para validar o login
                    valido = clienteDAO.validarLogin(email, password);

                    // retornar resposta de sucesso 
                    String resposta = valido ? "success" : "erro"; // ou "error" dependendo da validação

                    exchange.sendResponseHeaders(200, resposta.getBytes("UTF-8").length);
                    try (OutputStream os = exchange.getResponseBody()) 
                    {
                        os.write(resposta.getBytes("UTF-8"));
                    }

                } catch (NullPointerException e) {
                    System.out.println("Erro de referência nula!");
                } catch (Exception e) {
                    System.out.println("Erro inesperado: " + e.getMessage());

                    sendErrorResponse(exchange, 400, e.getMessage());
                }
                
                // // Resposta com sucesso ou erro
                //String resposta = valido ? "success" : "error";
                // exchange.sendResponseHeaders(200, resposta.getBytes("UTF-8").length);
                // try (OutputStream os = exchange.getResponseBody()) 
                // {
                //     os.write(resposta.getBytes("UTF-8"));
                    
                // }
            }
            
        }

         private void sendErrorResponse(HttpExchange exchange, int statusCode, String message) throws IOException 
        {
            exchange.sendResponseHeaders(statusCode, message.getBytes("UTF-8").length);
            OutputStream os = exchange.getResponseBody();
            os.write(message.getBytes("UTF-8"));
            os.close();
        }
    
    
        }
}
