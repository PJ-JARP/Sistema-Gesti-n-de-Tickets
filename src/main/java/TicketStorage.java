import java.io.*;
import java.util.*;

public class TicketStorage {
    private static final String FILE_NAME = "C:\\Users\\jorge\\OneDrive\\Documentos\\CSV out\\tickets.csv";

    // Asegurarse de que el archivo exista
    private static void ensureFileExists() {
        File file = new File(FILE_NAME);
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs(); // Crear directorios si no existen
                file.createNewFile(); // Crear el archivo
            }
        } catch (IOException e) {
            System.err.println("Error al crear el archivo: " + e.getMessage());
        }
    }

    // Leer todos los tickets del archivo CSV
    public static List<Ticket> readAllTickets() {
        ensureFileExists();
        List<Ticket> tickets = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split("\t");
                if (values.length >= 7) {
                    Ticket ticket = new Ticket(
                        values[0], // ID
                        values[1], // Descripción
                        values[2], // Fecha de creación
                        values[3], // Prioridad
                        values[4], // Estado
                        values[5], // Categoría
                        values[6]  // Asignado a
                    );
                    tickets.add(ticket);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }

        return tickets;
    }

    // Escribir una lista de tickets en el archivo CSV
    public static void writeAllTickets(List<Ticket> tickets) {
        ensureFileExists();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Ticket ticket : tickets) {
                bw.write(ticket.toCSV());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }

    // Guardar un nuevo ticket
    public static void saveTicket(Ticket ticket) {
        List<Ticket> tickets = readAllTickets();
        tickets.add(ticket); // Agregar el nuevo ticket
        writeAllTickets(tickets); // Guardar toda la lista
    }

    // Verificar si un ticket ya existe
    public static boolean ticketExists(String id) {
        return readAllTickets().stream().anyMatch(ticket -> ticket.getId().equals(id));
    }

    // Contar tickets por estado
    public static int countByStatus(String status) {
        return (int) readAllTickets().stream()
            .filter(ticket -> ticket.getEstado().equalsIgnoreCase(status))
            .count();
    }

    // Obtener las últimas 5 actividades
    public static List<String> getRecentActivity() {
        List<Ticket> tickets = readAllTickets();
        int start = Math.max(0, tickets.size() - 5);
        List<String> recentActivities = new ArrayList<>();
        for (Ticket ticket : tickets.subList(start, tickets.size())) {
            recentActivities.add(ticket.toCSV());
        }
        return recentActivities;
    }

    static boolean isTicketAlreadyAssigned(String ticketId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
