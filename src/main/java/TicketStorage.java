import java.io.*;
import java.util.*;

public class TicketStorage {
    // Archivo CSV donde se almacenarán los tickets
    private static final String FILE_NAME = "C:/Users/jorge/OneDrive/Escritorio/CSV out/tickets.csv";

    // Método para verificar si un ticket ya existe
    public static boolean ticketExists(String id) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values[0].equals(id)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Método para guardar un ticket en el archivo CSV
    public static void saveTicket(Ticket ticket) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            bw.write(ticket.toCSV());
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para contar tickets por estado
    public static int countByStatus(String status) {
        int count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(","); // Asume que el estado está en la columna 4
                if (parts.length > 3 && parts[3].trim().equalsIgnoreCase(status)) {
                    count++;
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
        return count;
    }

    // Método para obtener las últimas 5 actividades
    public static List<String> getRecentActivity() {
        List<String> recentActivities = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            List<String> allLines = new ArrayList<>();
            String line;

            // Leer todas las líneas
            while ((line = reader.readLine()) != null) {
                allLines.add(line);
            }

            // Tomar las últimas 5 líneas
            int start = Math.max(0, allLines.size() - 5);
            recentActivities = allLines.subList(start, allLines.size());
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
        return recentActivities;
    }
}
