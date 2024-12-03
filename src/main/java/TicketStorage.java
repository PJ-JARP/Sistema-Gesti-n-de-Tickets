//importación de librerías


import java.io.*;
import java.util.*;

public class TicketStorage {
    private static final String FILE_NAME = "tickets.csv";  // Archivo CSV donde se almacenarán los tickets
    
    // Método para guardar el ticket en el archivo CSV
    public static void saveTicket(Ticket ticket) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(ticket.toCSV());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para leer todos los tickets del archivo CSV
    public static List<Ticket> loadTickets() {
        List<Ticket> tickets = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                tickets.add(new Ticket(data[0], data[1], data[2], data[3]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tickets;
    }

    // Método para verificar si un ticket con el mismo ID ya existe
    public static boolean ticketExists(String id) {
        List<Ticket> tickets = loadTickets();
        for (Ticket ticket : tickets) {
            if (ticket.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }
}
