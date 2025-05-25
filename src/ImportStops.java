import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Types;

public class ImportStops {
public static void main(String[] args) throws Exception {
        // Liga à base de dados (altera o utilizador e password!)
        Connection conn = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/bd_dai", "root", "root");

        BufferedReader br = new BufferedReader(new FileReader("src/stops.txt"));
        String linha;
        br.readLine(); // Ignora o header

        conn.setAutoCommit(false);
        // Prepara a instrução SQL de INSERT para a nova estrutura da tabela 'stops'
        // Inclui as colunas GTFS e as colunas adicionais que mantiveste
        PreparedStatement ps = conn.prepareStatement(
            "INSERT INTO stops (stop_id, stop_code, stop_name, stop_desc, stop_lat, stop_lon, zone_id, stop_url, location_type, parent_station, stop_timezone, wheelchair_boarding, varias_rotas, lotacao, ativa) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");


            int count = 0;

        while ((linha = br.readLine()) != null) {
            String[] partes = linha.split(",", -1);
            // Extrai os dados das partes, tratando campos opcionais e aspas
            String stop_id = partes.length > 0 ? partes[0].trim() : null;
            String stop_code = partes.length > 1 ? partes[1].trim() : null;
            // Remove as aspas do stop_name, se existirem
            String stop_name = partes.length > 2 ? partes[2].trim().replaceAll("^\"|\"$", "") : null;
            String stop_desc = partes.length > 3 ? partes[3].trim() : null;

            Double stop_lat = null;
            if (partes.length > 4 && !partes[4].trim().isEmpty()) {
                try {
                    stop_lat = Double.parseDouble(partes[4].trim());
                } catch (NumberFormatException e) {
                    System.err.println("Erro ao analisar stop_lat na linha: " + linha + " - " + e.getMessage());
                    // Continua para a próxima linha ou trata o erro conforme necessário
                    continue;
                }
            }

            Double stop_lon = null;
            if (partes.length > 5 && !partes[5].trim().isEmpty()) {
                try {
                    stop_lon = Double.parseDouble(partes[5].trim());
                } catch (NumberFormatException e) {
                    System.err.println("Erro ao analisar stop_lon na linha: " + linha + " - " + e.getMessage());
                    continue;
                }
            }

            String zone_id = partes.length > 6 ? partes[6].trim() : null;
            String stop_url = partes.length > 7 ? partes[7].trim() : null;

            Integer location_type = null;
            if (partes.length > 8 && !partes[8].trim().isEmpty()) {
                try {
                    location_type = Integer.parseInt(partes[8].trim());
                } catch (NumberFormatException e) {
                    System.err.println("Erro ao analisar location_type na linha: " + linha + " - " + e.getMessage());
                    continue;
                }
            }

            String parent_station = partes.length > 9 ? partes[9].trim() : null;
            String stop_timezone = partes.length > 10 ? partes[10].trim() : null;

            Integer wheelchair_boarding = null;
            if (partes.length > 11 && !partes[11].trim().isEmpty()) {
                try {
                    wheelchair_boarding = Integer.parseInt(partes[11].trim());
                } catch (NumberFormatException e) {
                    System.err.println("Erro ao analisar wheelchair_boarding na linha: " + linha + " - " + e.getMessage());
                    continue;
                }
            }

            // Define os parâmetros para a instrução INSERT
            ps.setString(1, stop_id);
            ps.setString(2, stop_code);
            ps.setString(3, stop_name);
            ps.setString(4, stop_desc);

            if (stop_lat != null) ps.setDouble(5, stop_lat); else ps.setNull(5, Types.DECIMAL);
            if (stop_lon != null) ps.setDouble(6, stop_lon); else ps.setNull(6, Types.DECIMAL);

            ps.setString(7, zone_id);
            ps.setString(8, stop_url);

            if (location_type != null) ps.setInt(9, location_type); else ps.setNull(9, Types.INTEGER);

            ps.setString(10, parent_station);
            ps.setString(11, stop_timezone);

            if (wheelchair_boarding != null) ps.setInt(12, wheelchair_boarding); else ps.setNull(12, Types.INTEGER);

            // Define valores para as colunas adicionais que não vêm do stops.txt
            // Podes ajustar estes valores padrão conforme necessário
            ps.setNull(13, Types.BOOLEAN); // varias_rotas (ou ps.setBoolean(13, false);)
            ps.setNull(14, Types.INTEGER); // lotacao (ou ps.setInt(14, 0);)
            ps.setBoolean(15, true); // ativa

            // Adiciona a instrução ao batch
            ps.addBatch();
            count++;

            // Executa o batch periodicamente
            if (count % 1000 == 0) { // Commit a cada 1000 linhas
                ps.executeBatch();
                conn.commit();
                System.out.println("Commit realizado após " + count + " linhas.");
            }
        }

        // Executa o batch final e faz commit
        ps.executeBatch();
        conn.commit();

        br.close();
        ps.close();
        conn.close();
        System.out.println("Importação concluída! Total de linhas processadas: " + count);
    }
}