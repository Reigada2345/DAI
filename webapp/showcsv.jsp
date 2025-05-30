<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Conteúdo do CSV</title>
</head>
<body>
    <h1>Conteúdo do ficheiro stops.csv</h1>
    <ul>
        <%
            // Pega a lista de linhas passada pelo servlet
            java.util.List<String> csvLines = (java.util.List<String>) request.getAttribute("csvLines");
            if (csvLines != null) {
                for (String line : csvLines) {
        %>
                    <li><%= line %></li>
        <%
                }
            } else {
        %>
            <li>Nenhum dado para mostrar.</li>
        <%
            }
        %>
    </ul>
</body>
</html>
